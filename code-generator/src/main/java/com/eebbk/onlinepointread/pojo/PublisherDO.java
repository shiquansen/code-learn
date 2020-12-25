package com.eebbk.onlinepointread.pojo;

import java.io.Serializable;
import java.util.Date;

public class PublisherDO implements Serializable {
    private Integer id;

    private String name;

    private String editionName;

    private Integer publisherNo;

    private Date updateTime;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEditionName() {
        return editionName;
    }

    public void setEditionName(String editionName) {
        this.editionName = editionName == null ? null : editionName.trim();
    }

    public Integer getPublisherNo() {
        return publisherNo;
    }

    public void setPublisherNo(Integer publisherNo) {
        this.publisherNo = publisherNo;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}