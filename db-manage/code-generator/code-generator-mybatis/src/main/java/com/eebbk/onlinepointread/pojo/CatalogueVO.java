package com.eebbk.onlinepointread.pojo;

import java.io.Serializable;
import java.util.Date;

public class CatalogueVO implements Serializable {
    private Integer id;

    private String subtitle;

    private String nameEn;

    private String nameZh;

    private String fullEnName;

    private String fullZnName;

    private String contentType;

    private Integer contentOrder;

    private Integer innerOrder;

    private String pageNum;

    private Integer pageFrom;

    private Integer pageTo;

    private Integer contentLevel;

    private Integer parentId;

    private Integer bookId;

    private Date createTime;

    private Boolean ifFather;

    private String attribute;

    private Integer catalogueType;

    private String modelNameZh;

    private String modelNameEn;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh == null ? null : nameZh.trim();
    }

    public String getFullEnName() {
        return fullEnName;
    }

    public void setFullEnName(String fullEnName) {
        this.fullEnName = fullEnName == null ? null : fullEnName.trim();
    }

    public String getFullZnName() {
        return fullZnName;
    }

    public void setFullZnName(String fullZnName) {
        this.fullZnName = fullZnName == null ? null : fullZnName.trim();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public Integer getContentOrder() {
        return contentOrder;
    }

    public void setContentOrder(Integer contentOrder) {
        this.contentOrder = contentOrder;
    }

    public Integer getInnerOrder() {
        return innerOrder;
    }

    public void setInnerOrder(Integer innerOrder) {
        this.innerOrder = innerOrder;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum == null ? null : pageNum.trim();
    }

    public Integer getPageFrom() {
        return pageFrom;
    }

    public void setPageFrom(Integer pageFrom) {
        this.pageFrom = pageFrom;
    }

    public Integer getPageTo() {
        return pageTo;
    }

    public void setPageTo(Integer pageTo) {
        this.pageTo = pageTo;
    }

    public Integer getContentLevel() {
        return contentLevel;
    }

    public void setContentLevel(Integer contentLevel) {
        this.contentLevel = contentLevel;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getIfFather() {
        return ifFather;
    }

    public void setIfFather(Boolean ifFather) {
        this.ifFather = ifFather;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute == null ? null : attribute.trim();
    }

    public Integer getCatalogueType() {
        return catalogueType;
    }

    public void setCatalogueType(Integer catalogueType) {
        this.catalogueType = catalogueType;
    }

    public String getModelNameZh() {
        return modelNameZh;
    }

    public void setModelNameZh(String modelNameZh) {
        this.modelNameZh = modelNameZh == null ? null : modelNameZh.trim();
    }

    public String getModelNameEn() {
        return modelNameEn;
    }

    public void setModelNameEn(String modelNameEn) {
        this.modelNameEn = modelNameEn == null ? null : modelNameEn.trim();
    }
}