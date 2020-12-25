package com.eebbk.onlinepointread.dao;

import com.eebbk.onlinepointread.pojo.PublisherDO;

public interface PublisherDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PublisherDO record);

    int insertSelective(PublisherDO record);

    PublisherDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PublisherDO record);

    int updateByPrimaryKey(PublisherDO record);
}