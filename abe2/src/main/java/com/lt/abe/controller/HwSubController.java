package com.lt.abe.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.lt.abe.commen.Result;
import com.lt.abe.cpabe.CPABE;
import com.lt.abe.cpabe.GenerateTree;
import com.lt.abe.entity.HwRel;
import com.lt.abe.entity.HwSub;
import com.lt.abe.entity.Params;
import com.lt.abe.entity.User;
import com.lt.abe.exception.CustomException;
import com.lt.abe.service.HwSubService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 *  文件上传接口
 */
@RestController
@RequestMapping("/HwFiles")
//@CrossOrigin(origins = "http://localhost:8080")

public class HwSubController {
    @Resource
    private HwSubService hwSubService;
    // 文件上传存储路径
    private static final String filePath = System.getProperty("user.dir") + "/HwFiles/";
    private String pkFileName = "SetUpFile/pk.properties";
    private String mskFileName = "SetUpFile/msk.properties";
    private String a_Path = "SetUpFile/a.properties";
    private GenerateTree hwTree;
    private String StudentId = "";
    private String StudentCollege = "";
    private String StudentClass = "";
    private String Rel_College = "";
    private String Rel_Class = "";
    private String ctFileName = "ct.properties";
    private String TeacherRole = "教师";
    /**
     * 保存到数据库
     */
    @PostMapping("/save")
    public Result SaveHW(@RequestBody HwSub hwSub){

        hwSub.setUpgrade_time();
        HwSub hwSub1=hwSubService.findHwAndStudent(hwSub.getHw_name(),hwSub.getSch_id());
        if (hwSub1!=null){
            //说明已经提交过作业
            hwSubService.update(hwSub);
        }else {
            hwSubService.add(hwSub);
        }

        return Result.success();
    }

    /**
     * 获得用户信息，以获得私钥，检查是否满足属性要求。暂时只用到学号
     * @param user
     * @return
     */
    @PostMapping("/getUser")
    public Result getUser(@RequestBody User user){
        StudentId=user.getSch_id();
        StudentClass=user.getClasss();
        StudentCollege=user.getCollege();
        return Result.success();
    }

    /**
     * 获得作业信息，如果不符合上传条件就提示不让上传
     * @param hwRel
     * @return
     */
    @PostMapping("/getHwMsg")
    public Result getHwMsg(@RequestBody HwRel hwRel){
        Rel_College=hwRel.getRel_college();
        Rel_Class=hwRel.getRel_class();

        return Result.success();
    }

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {

        /**
         * 进行文件加密,只有唯一的学生和老师的角色
         */

        if (file.isEmpty()) {
            // 文件为空，处理空文件的情况
            return Result.error("提交的文件为空，请重新选择！");
        }

//        if (StudentClass!=Rel_Class&&StudentCollege!=Rel_College){
//            throw new CustomException("您的身份信息不符合上传条件！");
//        }
//
        try {
            byte[] fileBytes = file.getBytes();
//            String data = new String(fileBytes, StandardCharsets.UTF_8);

            String accessTreeString = hwTree.SubHwTree(StudentId,TeacherRole);

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
        if (!FileUtil.isDirectory(filePath)) {
            FileUtil.mkdir(filePath);
        }
        List<String> fileNames = FileUtil.listFileNames(filePath);
        String avatar = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");

        String skPath = System.getProperty("user.dir") + "/Student_SK/"+StudentId+"_sk.properties";
        byte[] decryptedData = CPABE.kemDecrypt(filePath + avatar, skPath);
        String fileName = extractFileName(avatar);

        try {
            if (decryptedData!=null) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,  StandardCharsets.UTF_8.toString()));
                response.setContentType("application/octet-stream");
                OutputStream os = response.getOutputStream();;

                os = response.getOutputStream();
                os.write(decryptedData);
                os.flush();
                os.close();
                System.out.println("文件下载成功");
            }
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
        }
        return Result.error("抱歉！您的属性不符合访问要求！");

    }
    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<User> info = hwSubService.findBySearch(params);
        return Result.success(info);
    }

    private String extractFileName(String fullName) {
        int startIndex = fullName.indexOf("-");
        int endIndex = fullName.lastIndexOf("-");
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return fullName.substring(startIndex + 1, endIndex).trim();
        }
        return fullName;
    }
}