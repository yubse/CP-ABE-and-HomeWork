package com.lt.abe.entity;

/**
 * @Author 物联网2班刘婷
 * @Description Params
 * @Date 2024/3/1
 * 一些从前端传过来的变量，主要是用于分页、分页查询
 */

public class Params {
    private String name;
    private String hw_name;
    private String sch_id;
    private String rel_college;
    private String rel_class;
    private Integer pageNum;
    private Integer pageSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSch_id() {
        return sch_id;
    }

    public void setSch_id(String sch_id) {
        this.sch_id = sch_id;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getHw_name() {
        return hw_name;
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

    public void setHw_name(String hw_name) {
        this.hw_name = hw_name;
    }
}
