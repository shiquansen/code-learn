package com.sbzl.framework.jdkoperation.stream;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 邱道长
 * 2020/2/26
 */
public class StreamFlatMap {


    public static void main(String[] args) {
        List<List<String>> lists = Arrays.asList(
                Arrays.asList("深圳", "广州", "佛山"),
                Arrays.asList("株洲", "岳阳", "湘潭")

        );

        Stream<List<String>> stream = lists.stream();

        List<String> list = stream.flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(list);

    }
}
