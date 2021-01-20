package com.sbzl.framework.jdkoperation.stream;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 邱道长
 * 2020/2/26
 */
public class StreamCollect {


    public static void main(String[] args) {
        Integer ids[] = {1,3,5};
        UserRepository userRepository = new UserRepository();

        List<User> list = Stream.of(ids)
                .map(userRepository::findById)
                .collect(Collectors.toList());

        System.out.println(list);
        Set<User> set = Stream.of(ids)
                .map(userRepository::findById)
                .collect(Collectors.toSet());
        System.out.println(set);

    }
}
