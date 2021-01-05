package com.eebbk.onlinepointread.mapper;

import com.eebbk.onlinepointread.domain.BookshelfDO;

public interface BookshelfDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BookshelfDO record);

    int insertSelective(BookshelfDO record);

    BookshelfDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BookshelfDO record);

    int updateByPrimaryKey(BookshelfDO record);
}