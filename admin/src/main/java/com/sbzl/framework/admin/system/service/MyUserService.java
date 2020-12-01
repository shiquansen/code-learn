package com.sbzl.framework.admin.system.service;

import com.sbzl.framework.admin.system.adapter.UserAdapter;
import com.sbzl.framework.admin.system.mapper.UserMapper;
import com.sbzl.framework.admin.system.model.User;
import com.sbzl.framework.admin.util.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;


@Repository
public class MyUserService implements UserDetailsService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MyPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.selectByName(s);
        if(user == null)
            throw new UsernameNotFoundException("用户未注册");
        return new UserAdapter(user);
    }

    public int insert(String userName, String password) throws UsernameNotFoundException {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        return userMapper.insert(user);
    }

//    public UserDetails login(String userName, String password) throws UsernameNotFoundException {
//        User user = new User();
//        user.setUserName(userName);
//        user.setPassword(passwordEncoder.encode(password));
//        return userMapper.login(user);
//    }

}
