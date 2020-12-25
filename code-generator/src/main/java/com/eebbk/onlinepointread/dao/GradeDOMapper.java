package com.eebbk.onlinepointread.dao;

import com.eebbk.onlinepointread.pojo.GradeDO;

public interface GradeDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GradeDO record);

    int insertSelective(GradeDO record);

    GradeDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GradeDO record);

    int updateByPrimaryKey(GradeDO record);
}