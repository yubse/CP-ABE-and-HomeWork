package com.lt.abe.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.lt.abe.commen.Result;
import com.lt.abe.cpabe.CPABE;
import com.lt.abe.cpabe.GenerateTree;
import com.lt.abe.entity.HwRel;
import com.lt.abe.entity.User;
import com.lt.abe.service.HwRelService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 *  老师上传作业详情文件
 *  访问策略如下    →班级
 *  （1.2）→（2，2）→专业
 *        →教师身份（不必是特定的教师）
 */
@RestController
@RequestMapping("/files")
//@CrossOrigin(origins = "http://localhost:8080")

public class FileController {

    // 文件上传存储路径
    private static final String filePath = System.getProperty("user.dir") + "/file/";
    private String pkFileName = "SetUpFile/pk.properties";
    private String mskFileName = "SetUpFile/msk.properties";

    private HwRelService hwRelService;
    private GenerateTree hwTree;
    private String ctFileName = "ct.properties";
    private String college="";
    private String classs="";
    private String teacherID="";
    private String UserSchId="";

    /**
     * 获得作业的基本信息，发布作业的老师，面向专业和班级
     * @param hwRel
     * @return
     */
    @PostMapping("/getHwRel")
    public Result getHwRel(@RequestBody HwRel hwRel){

        college=hwRel.getRel_college();
        classs=hwRel.getRel_class();
        teacherID=hwRel.getTeacher_id();
        return Result.success();
    }

    /**
     * 获得用户信息，以获得私钥，检查是否满足属性要求。暂时只用到学号
     * @param user
     * @return
     */
    @PostMapping("/getUser")
    public Result getUser(@RequestBody User user){
        UserSchId=user.getSch_id();

        return Result.success();
    }

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        /**
         * 进行文件加密
         * 老师发布加密后的作业详情给学生
         * 设置用户属性，只有班级的学生和指定的老师能下载
         */

        if (file.isEmpty()) {
            // 文件为空，处理空文件的情况
            return Result.error("提交的文件为空，请重新选择！");
        }

        try {
            byte[] fileBytes = file.getBytes();
//            String data = new String(fileBytes, StandardCharsets.UTF_8);

            String accessTreeString = hwTree.RelHwTree(college, classs,teacherID);

            String flag = System.currentTimeMillis() + "";

            String fileName = file.getOriginalFilename();

            CPABE.kemEncrypt(fileBytes, accessTreeString, pkFileName, filePath + flag + "-" + fileName + "-" + ctFileName);

            System.out.println("加密成功");
                return Result.success(flag);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return Result.error("文件加密出错");}
    }



     /**
     * 获取文件
     */
    @GetMapping("/{flag}")
    public Result avatarPath(@PathVariable String flag, HttpServletResponse response) throws GeneralSecurityException, IOException {
        //获得文件全名
        if (!FileUtil.isDirectory(filePath)) {
            FileUtil.mkdir(filePath);
        }

        List<String> fileNames = FileUtil.listFileNames(filePath);
        String avatar = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");

        String skPath = System.getProperty("user.dir") + "/Student_SK/"+UserSchId+"_sk.properties";
        byte[] decryptedData = CPABE.kemDecrypt(filePath + avatar, skPath);
        String fileName = extractFileName(avatar);

        try {
            if (decryptedData!=null) {
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,  StandardCharsets.UTF_8.toString()));
                response.setContentType("application/octet-stream");
                OutputStream os = response.getOutputStream();;

                os = response.getOutputStream();
                os.write(decryptedData);
                os.flush();
                os.close();
//                System.out.println("文件下载成功");
            }
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
        }
        return Result.error("抱歉！您的属性不符合访问要求！");
    }

    // 提取文件名的方法
    private String extractFileName(String fullName) {
        int startIndex = fullName.indexOf("-");
        int endIndex = fullName.lastIndexOf("-");
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return fullName.substring(startIndex + 1, endIndex).trim();
        }
        return fullName;
    }

}