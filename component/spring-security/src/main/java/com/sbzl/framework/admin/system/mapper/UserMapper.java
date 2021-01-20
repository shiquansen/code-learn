package com.sbzl.framework.admin.system.mapper;

import com.sbzl.framework.admin.system.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int insert(User user);

    User selectByName(String userName);

    int update(User user);
}
