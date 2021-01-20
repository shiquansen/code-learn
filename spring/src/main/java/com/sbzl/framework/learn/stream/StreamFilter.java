package com.sbzl.framework.learn.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 邱道长
 * 2020/2/26
 */
public class StreamFilter {

    public static void main(String[] args) {
        Integer[] ids = {1,3,5,6,7,8};
//        List<Integer> l = Stream.of(ids).filter(it -> it % 2 == 0).collect(Collectors.toList());
        List<Integer> l = Stream.of(ids).filter(it -> it % 2 == 1).filter(it -> it >= 3)
                .collect(Collectors.toList());
        System.out.println(l);
    }
}
