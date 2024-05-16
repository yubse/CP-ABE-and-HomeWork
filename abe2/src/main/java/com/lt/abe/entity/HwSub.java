package com.lt.abe.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author 物联网2班刘婷
 * @Description HwSub
 * @Date 2024/3/5
 */
@Table(name = "hwsub")
public class HwSub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String hw_name;
    private String sch_id;
    private String hw_file;
    private String up_college;
    private String up_class;
    private String upgrade_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSch_id() {
        return sch_id;
    }

    public void setSch_id(String stu_id) {
        this.sch_id = stu_id;
    }

    public String getHw_file() {
        return hw_file;
    }

    public void setHw_file(String hw_file) {
        this.hw_file = hw_file;
    }

    public String getUp_college() {
        return up_college;
    }

    public void setUp_college(String up_college) {
        this.up_college = up_college;
    }

    public String getUp_class() {
        return up_class;
    }

    public void setUp_class(String up_class) {
        this.up_class = up_class;
    }

    public String getHw_name() {
        return hw_name;
    }

    public void setHw_name(String hw_name) {
        this.hw_name = hw_name;
    }

    public String getUpgrade_time() {
        return upgrade_time;
    }

    public void setUpgrade_time() {

        LocalDateTime currentTime = LocalDateTime.now();

        // 格式化日期时间为字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentTime.format(formatter);

        // 将格式化后的日期时间设置给 create_time 字段
        this.upgrade_time = formattedDateTime;
    }

}
