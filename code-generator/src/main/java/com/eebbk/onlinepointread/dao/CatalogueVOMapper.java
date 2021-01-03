package com.eebbk.onlinepointread.dao;

import com.eebbk.onlinepointread.pojo.CatalogueVO;

public interface CatalogueVOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CatalogueVO record);

    int insertSelective(CatalogueVO record);

    CatalogueVO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CatalogueVO record);

    int updateByPrimaryKey(CatalogueVO record);
}