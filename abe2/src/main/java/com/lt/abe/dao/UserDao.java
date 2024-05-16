package com.lt.abe.dao;

import com.lt.abe.entity.Params;
import com.lt.abe.entity.User;
import org.apache.ibatis.annotations.Insert;
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
public interface UserDao extends Mapper<User> {
    //1、基于注释
//    @Select("select * from user")
    List<User> getUser();

    List<User> findBySearch(@Param("params") Params params);
    void add(User user);

    void update(User user);

    void deleteUser(String sch_id);

    @Select("select * from user where sch_id = #{sch_id} limit 1")
    User findBySchId(@Param("sch_id") String schId);
    @Select("select * from user where sch_id = #{sch_id} and password=#{password} limit 1")
    User findBySchIdAndPassword(@Param("sch_id")String schId,@Param("password") String password);

    @Select("select * from user where id = #{id} limit 1")
    User findById(Integer id);
}
