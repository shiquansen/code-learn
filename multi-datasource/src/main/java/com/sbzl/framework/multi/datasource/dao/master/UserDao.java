package com.sbzl.framework.multi.datasource.dao.master;


import com.sbzl.framework.multi.datasource.dao.BaseDao;
import com.sbzl.framework.multi.datasource.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
* Title: UserDao
* Description:
* 用户数据接口 
* Version:1.0.0  
* @author pancm
* @date 2018年1月9日
 */
@Mapper
public interface UserDao extends BaseDao<User> {
    
}
