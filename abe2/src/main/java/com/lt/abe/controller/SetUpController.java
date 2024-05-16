package com.lt.abe.controller;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import com.lt.abe.commen.Result;
import com.lt.abe.cpabe.CPABE;
import com.lt.abe.entity.SetUp;
import com.lt.abe.service.SetUpService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author 物联网2班刘婷
 * @Description SetUpController
 * @Date 2024/3/4
 */
@RestController
@CrossOrigin
@RequestMapping("/security")
public class SetUpController {
    // 文件上传存储路径
    private static final String filePath = System.getProperty("user.dir") + "/SetUpFile/";
    @Resource
    private SetUpService setUpService;
    private static String pairingParametersFileName;
    /**
     * 文件上传
     */
    @PostMapping
    public Result update(@RequestBody SetUp setUp) throws IOException {
        String pairingParametersFileName =  "security_params/security_level_" + setUp.getSecurityLevel() + ".properties";
//        System.out.println("管理员将安全系数更新为："+setUp.getSecurityLevel());

        String pkFileName = filePath + "pk.properties";
//        System.out.println("成功获得系统公钥路径！");
        String mskFileName = filePath + "msk.properties";
//        System.out.println("成功获得系统主秘钥路径！");

        String newPairingParametersFileName = filePath + "a.properties";
//        System.out.println("成功获得a.properties路径！");

        // 复制并重命名 pairingParametersFileName 文件到 newPairingParametersFileName
        File sourceFile = new File(pairingParametersFileName);
        File destinationFile = new File(newPairingParametersFileName);
        Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        CPABE.setup(pairingParametersFileName, pkFileName, mskFileName);
        setUp.setPK(pkFileName);
//        System.out.println("pkFileName:"+pkFileName);
        setUp.setMSK(mskFileName);
        setUp.setId(1);
        setUpService.update(setUp);
        return Result.success();
    }
    public String getPairing() {
        // 在其他方法中可以访问和使用 pairingParametersFileName
        return pairingParametersFileName;
    }
}
