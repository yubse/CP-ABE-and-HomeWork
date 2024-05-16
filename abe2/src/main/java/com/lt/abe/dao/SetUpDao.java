package com.lt.abe.dao;

import com.lt.abe.entity.SetUp;
import com.lt.abe.entity.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author 物联网2班刘婷
 * @Description SetUpDao
 * @Date 2024/3/4
 */
@Repository
public interface SetUpDao extends Mapper<SetUp> {

    void update(SetUp setUp);
}
