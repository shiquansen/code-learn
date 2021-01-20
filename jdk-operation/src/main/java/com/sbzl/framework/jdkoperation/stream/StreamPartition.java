package com.sbzl.framework.jdkoperation.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 邱道长
 * 2020/2/27
 */
public class StreamPartition {
    public static void main(String[] args) {
        List<Player> players = Arrays.asList(
                new Player("湖人","james"),
                new Player("湖人","ad"),
                new Player("热火","jb"),
                new Player("热火","ig"),
                new Player("湖人","ab"),
                new Player("雷霆","cp3")
        );
        Map<Boolean, List<Player>> map = players.stream().collect(Collectors.partitioningBy(it -> it.getTeam().equals("湖人")));
        // 是不是湖人
        System.out.println(map);


    }
}
