package com.lt.abe.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lt.abe.dao.HwRelDao;
import com.lt.abe.dao.HwSubDao;
import com.lt.abe.entity.HwRel;
import com.lt.abe.entity.HwSub;
import com.lt.abe.entity.Params;
import com.lt.abe.entity.User;
import com.lt.abe.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 物联网2班刘婷
 * @Description HWUploadService
 * @Date 2024/3/5
 */
@Service
public class HwSubService {
    @Resource
    private HwSubDao hwSubDao;

    public HwSub findHwAndStudent(String hwName, String schId) {
        HwSub hwSub1= hwSubDao.findHwAndStudent(hwName, schId);
        return hwSub1;
    }

    public void update(HwSub hwSub) {
        hwSubDao.update(hwSub);
    }

    public PageInfo<User> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<User> list= hwSubDao.findBySearch(params);
        return PageInfo.of(list);
    }


    public void add(HwSub hwSub) {
        //不允许名称相同新增
        if (hwSub.getHw_file() == null||"".equals(hwSub.getHw_file())) {
            throw new CustomException("请选择上传文件");
        }

        hwSubDao.add(hwSub);}
}
