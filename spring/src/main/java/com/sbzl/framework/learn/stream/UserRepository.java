package com.sbzl.framework.learn.stream;

import java.util.ArrayList;
import java.util.List;

/**
 * 邱道长
 * 2020/2/26
 */
public class UserRepository {

    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1,"james"));
        users.add(new User(2,"wade"));
        users.add(new User(3,"bosh"));
    }


    public User findById(int id) {
        for(User u : users) {
            if(u.getId() == id) {
                return u;
            }
        }
        return null;
    }


    public List<User> findAll() {
        return users;
    }
}
