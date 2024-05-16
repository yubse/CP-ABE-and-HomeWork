package com.lt.abe.dao;

import com.lt.abe.entity.HwSub;
import com.lt.abe.entity.Params;
import com.lt.abe.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author 物联网2班刘婷
 * @Description HWUploadDao
 * @Date 2024/3/5
 */
@Repository
public interface HwSubDao extends Mapper<HwSub> {
    @Select("select * from hwsub where sch_id = #{sch_id} and hw_name=#{hw_name} limit 1")
    HwSub findHwAndStudent(@Param("hw_name") String hwName, @Param("sch_id") String schId) ;

    void update(HwSub hwSub);

    List<User> findBySearch(@Param("params") Params params);

    void add(HwSub hwSub);
}
