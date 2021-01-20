package com.eebbk.onlinepointread.domain;

import java.io.Serializable;
import java.util.Date;

public class BookshelfDO implements Serializable {
    private Long id;

    private String userId;

    private Long businessBookId;

    private Integer isCancel;

    private Date creatTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Long getBusinessBookId() {
        return businessBookId;
    }

    public void setBusinessBookId(Long businessBookId) {
        this.businessBookId = businessBookId;
    }

    public Integer getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Integer isCancel) {
        this.isCancel = isCancel;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}