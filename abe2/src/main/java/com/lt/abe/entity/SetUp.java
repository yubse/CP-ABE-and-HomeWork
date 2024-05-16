package com.lt.abe.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author 物联网2班刘婷
 * @Description SetUp
 * @Date 2024/3/4
 */
@Table(name = "setup")
public class SetUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String securityLevel;
    private String PK;
    private String MSK;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getPK() {
        return PK;
    }

    public void setPK(String PK) {
        this.PK = PK;
    }

    public String getMSK() {
        return MSK;
    }

    public void setMSK(String MSK) {
        this.MSK = MSK;
    }
}
