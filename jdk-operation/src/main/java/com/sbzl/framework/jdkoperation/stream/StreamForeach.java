package com.sbzl.framework.jdkoperation.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 邱道长
 * 2020/2/26
 */
public class StreamForeach {

    public static void main(String[] args) {
        List<String> l = Arrays.asList("james","wade","bosh");
        Stream<String> stream = l.stream();

//        stream.forEach(it -> System.out.println(it));
        stream.forEach(System.out::println);// terminal操作，终止了。
//        stream.forEach(System.out::println);




    }
}
