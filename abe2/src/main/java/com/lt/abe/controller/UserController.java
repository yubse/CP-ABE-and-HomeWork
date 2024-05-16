package com.lt.abe.controller;

import com.github.pagehelper.PageInfo;
import com.lt.abe.commen.Result;
import com.lt.abe.cpabe.CPABE;
import com.lt.abe.entity.User;
import com.lt.abe.entity.Params;
import com.lt.abe.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Author 物联网2班刘婷
 * @Description UserController
 * @Date 2024/2/29
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

     private String pkFileName = "SetUpFile/pk.properties";
    private String mskFileName = "SetUpFile/msk.properties";
    private String a_Path = "SetUpFile/a.properties";


    private static final String filePath = System.getProperty("user.dir") + "/Student_SK/";



    @GetMapping
    public Result getUser(){
        List<User> list= userService.getUser();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params){
        System.out.println("control层"+params);
        PageInfo<User> info = userService.findBySearch(params);

        return Result.success(info);
    }

    @PostMapping
    public Result add(@RequestBody User user) throws NoSuchAlgorithmException {

        String[] userAttList={user.getSch_id(), user.getCollege(),user.getClasss(),user.getRole()};
        String skFileName=filePath+user.getSch_id()+ "_sk.properties";

        CPABE.keygen(userAttList,  pkFileName, mskFileName, skFileName);
        user.setSkey(skFileName);
        if (user.getPassword() == null){
            userService.add(user);
        }
        else {
            userService.update(user);
        }
        return Result.success();
    }

    @DeleteMapping("/{sch_id}")
    public Result deleteUser(@PathVariable String sch_id){
        userService.deleteUser(sch_id);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        User LoginUser = userService.login(user);
        return Result.success(LoginUser);
    }
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        userService.add(user);
        return Result.success();
    }

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        
            return Result.success();
        }

}
