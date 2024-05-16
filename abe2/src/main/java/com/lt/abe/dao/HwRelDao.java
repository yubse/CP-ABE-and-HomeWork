package com.lt.abe.dao;

import com.lt.abe.entity.Params;
import com.lt.abe.entity.HwRel;
import com.lt.abe.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author 物联网2班刘婷
 * @Description UserDao
 * @Date 2024/2/29
 */
@Repository
public interface HwRelDao extends Mapper<HwRel> {
    void findHwNameAndTeacher(HwRel hwRel);

    List<HwRel> findBySearch(@Param("params") Params params);

    @Select("select * from hwrel where rel_name = #{rel_name} limit 1")
    HwRel findByHwName(@Param("rel_name") String relName);

    void add(HwRel hwRel);

    void update(HwRel hwRel);

    void deleteHw(int id);
}
