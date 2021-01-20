package com.sbzl.framework.jdkoperation.stream;

import java.util.stream.Stream;

/**
 * 邱道长
 * 2020/2/26
 */
public class CreationStreamOf {

    public static void main(String[] args) {
        // style 1 接收一个参数 ，传入数组
        String[] players = {"james","wade","bosh"};
        Stream<String> players1 = Stream.of(players);
        // style 2 接收多个参数
        Stream.of("james", "wade", "bosh");

    }
}
