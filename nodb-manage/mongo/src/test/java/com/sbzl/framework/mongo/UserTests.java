package com.sbzl.framework.mongo;

import com.sbzl.framework.mongo.dao.User;
import com.sbzl.framework.mongo.dao.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.util.*;

@SpringBootTest
public class UserTests {

    @Autowired
    UserRepository userRepository;

    @Test
    public void save(){
        for(int i = 0; i < 5000; i++){
            User user = new User();
            user.setId(i); // 这里先临时写死一个 ID 编号，后面演示自增 ID 的时候，在修改这块
            user.setUsername(UUID.randomUUID().toString());
            user.setPassword(UUID.randomUUID().toString().substring(1,5));
            user.setCreateTime(new Date());
            // 创建 Profile 对象
            User.Profile profile = new User.Profile();
            profile.setNickname(UUID.randomUUID().toString().substring(20));
            profile.setGender(i);
            user.setProfile(profile);
            // 存储到 DB
            userRepository.save(user);
        }
    }

    // 这里要注意，如果使用 save 方法来更新的话，必须是全量字段，否则其它字段会被覆盖。
    // 所以，这里仅仅是作为一个示例。
    @Test // 更新一条记录
    public void testUpdate() {
        // 查询用户
        Optional<User> userResult = userRepository.findById(1);
        Assert.isTrue(userResult.isPresent(), "用户一定要存在");
        // 更新
        User updateUser = userResult.get();
        updateUser.setUsername("yutou");
        userRepository.save(updateUser);
    }

    @Test // 根据 ID 编号，删除一条记录
    public void testDelete() {
        userRepository.deleteById(1);
    }

    @Test // 根据 ID 编号，查询一条记录
    public void testSelectById() {
        Optional<User> userDO = userRepository.findById(2);
        System.out.println(userDO);
    }

    @Test // 根据名字获得一条记录
    public void testFindByName() {
        User user = userRepository.findByUsername("0585a280-74ba-46b3-a4ac-086e4ddf5abd");
        System.out.println(user);
    }

    @Test // 使用 username 模糊查询，分页返回结果
    public void testFindByNameLike() {
        // 创建排序条件
        List<Sort.Order> orders = new ArrayList<>();
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        orders.add(order);
        Sort sort = Sort.by(orders); // ID 倒序
        // 创建分页条件。
        Pageable pageable = PageRequest.of(0, 10, sort);
        // 执行分页操作
        Page<User> page = userRepository.findByUsernameLike("05", pageable);
        // 打印
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        page.get().forEach(System.out::println);
    }

    @Test
    public void testFindByUsername01() {
        User user = userRepository.findByUsername01("0585a280-74ba-46b3-a4ac-086e4ddf5abd");
        System.out.println(user);
    }

    @Test
    public void testFindByUsernameLike01() {
        User user = userRepository.findByUsernameLike01("05");
        System.out.println(user);
    }


}
