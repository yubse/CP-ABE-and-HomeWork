package com.lt.abe.entity;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author 物联网2班刘婷
 * @Description User
 * @Date 2024/2/29
 */

@Table(name = "hwrel")
public class HwRel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String rel_name;
    private String rel_college;
    private String rel_class;
    private String rel_file;
    private String teacher_id;
    private String create_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRel_name() {
        return rel_name;
    }

    public void setRel_name(String rel_name) {
        this.rel_name = rel_name;
    }

    public String getRel_college() {
        return rel_college;
    }

    public void setRel_college(String rel_college) {
        this.rel_college = rel_college;
    }

    public String getRel_class() {
        return rel_class;
    }

    public void setRel_class(String rel_class) {
        this.rel_class = rel_class;
    }

    public String getRel_file() {
        return rel_file;
    }

    public void setRel_file(String rel_file) {
        this.rel_file = rel_file;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time() {
        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();

        // 格式化日期时间为字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentTime.format(formatter);

        // 将格式化后的日期时间设置给 create_time 字段
        this.create_time = formattedDateTime;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }
}
