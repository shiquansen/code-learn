package com.sbzl.framework.jdkoperation.stream;

import java.util.stream.Stream;

/**
 * 邱道长
 * 2020/2/26
 */
public class StreamMap {


    public static void main(String[] args) {
        Integer ids[] = {1,3,5};
        UserRepository userRepository = new UserRepository();
        Stream<Integer> stream = Stream.of(ids);

        Stream<User> userStream = stream.map(userRepository::findById);

        userStream.forEach(System.out::println);


    }
}
