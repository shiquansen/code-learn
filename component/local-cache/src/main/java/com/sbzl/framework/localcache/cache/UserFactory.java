package com.sbzl.framework.localcache.cache;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class UserFactory implements InitializingBean {
    public static final Map<Integer, User> userFactory = new HashMap();

    int addUser(User user){
        userFactory.putIfAbsent(user.getId(), user);
        return 1;
    }

    int deleteUser(Integer id){
        userFactory.remove(id);
        return 1;
    }

    int updateUser(User user){
        userFactory.put(user.getId(), user);
        return 1;
    }

    User selectUser(Integer id){
        return userFactory.get(id);
    }

    List<User> selectUserList(){
        return new ArrayList<>(userFactory.values());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        userFactory.put(1, new User(1, "1号用户"));
        userFactory.put(2, new User(2, "2号用户"));
        userFactory.put(3, new User(3, "3号用户"));
        userFactory.put(4, new User(4, "4号用户"));
        userFactory.put(5, new User(5, "5号用户"));
        userFactory.put(6, new User(6, "6号用户"));
        userFactory.put(7, new User(7, "admin"));
    }
}
