package com.lt.abe.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lt.abe.dao.HwRelDao;
import com.lt.abe.entity.HwRel;
import com.lt.abe.entity.Params;
import com.lt.abe.entity.User;
import com.lt.abe.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 物联网2班刘婷
 * @Description HwRelService
 * @Date 2024/3/2
 */

@Service
public class HwRelService {

    @Resource
    private HwRelDao hwRelDao;

    public PageInfo<HwRel> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<HwRel> list= hwRelDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void update(HwRel hwRel) {
        hwRel.setCreate_time();
        hwRelDao.update(hwRel);
    }

    public void add(HwRel hwRel) {
        //不允许名称相同新增
        if (hwRel.getRel_name() == null||"".equals(hwRel.getRel_name())) {
            throw new CustomException("请填入作业名称");
        }
        HwRel hwRel1= hwRelDao.findByHwName(hwRel.getRel_name());
        //System.out.println("添加，查询是否存在相同的名称：hwRel1"+hwRel1);

        if (hwRel1!=null) {
            throw new CustomException("该作业已存在");
        }
        hwRel.setCreate_time();
        hwRelDao.add(hwRel);
    }

    public void deleteHw(int id) {
        hwRelDao.deleteHw(id);
    }


}
