package com.sbzl.framework.multi.datasource.dao.cluster;

import com.sbzl.framework.multi.datasource.dao.BaseDao;
import com.sbzl.framework.multi.datasource.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
* @Title: StudentDao
* @Description: 
* @Version:1.0.0  
* @author pancm
* @date 2018年4月9日
 */
@Mapper
public interface StudentDao extends BaseDao<Student> {

}
