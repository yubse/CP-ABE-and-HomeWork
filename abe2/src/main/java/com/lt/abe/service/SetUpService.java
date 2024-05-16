package com.lt.abe.service;

import com.lt.abe.dao.HwRelDao;
import com.lt.abe.dao.SetUpDao;
import com.lt.abe.entity.SetUp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author 物联网2班刘婷
 * @Description SetUpService
 * @Date 2024/3/4
 */
@Service
public class SetUpService {

    @Resource
    private SetUpDao setUpDao;

    public void update(SetUp setUp) {
        setUpDao.update(setUp);
    }
}
