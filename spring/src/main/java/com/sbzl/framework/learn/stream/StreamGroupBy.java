package com.sbzl.framework.learn.stream;

import com.sbzl.framework.learn.stream.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 邱道长
 * 2020/2/27
 */
public class StreamGroupBy {
    public static void main(String[] args) {
        List<Player> players = Arrays.asList(
                new Player("湖人","james"),
                new Player("湖人","ad"),
                new Player("热火","jb"),
                new Player("热火","ig"),
                new Player("湖人","ab"),
                new Player("雷霆","cp3")
        );
        Map<String, List<Player>> collect = players.stream().
                collect(Collectors.groupingBy(it -> it.getTeam()));
        System.out.println(collect);



    }
}
