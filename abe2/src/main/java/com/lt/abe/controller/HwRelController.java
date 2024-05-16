package com.lt.abe.controller;

import com.github.pagehelper.PageInfo;
import com.lt.abe.commen.Result;
import com.lt.abe.entity.HwRel;
import com.lt.abe.entity.Params;
import com.lt.abe.entity.User;
import com.lt.abe.service.HwRelService;
import com.lt.abe.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author 物联网2班刘婷
 * @Description HwRelController
 * @Date 2024/3/2
 */

@RestController
@CrossOrigin
@RequestMapping("/hwRel")

public class HwRelController {
    @Resource
    private HwRelService hwRelService;

    @GetMapping("/search")
    public Result findBySearch(Params params){

        PageInfo<HwRel> info = hwRelService.findBySearch(params);
        return Result.success(info);
    }

    /**
     * 查找是否之前已经上传过作业详情。
     * @param hwRel
     * @return
     */
    @PostMapping
    public Result add(@RequestBody HwRel hwRel){
        if (hwRel.getCreate_time() == null){
            hwRelService.add(hwRel);
        }
        else {
            hwRelService.update(hwRel);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable int id){
        hwRelService.deleteHw(id);
        return Result.success();
    }

}
