package com.sbzl.framework.juc.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class RunableOption {
    ThreadLocal<Map> threadLocal = new ThreadLocal<>();

    public Long getCount(Long maxNumber){
        List<Long> longList = new ArrayList<>();
//        longList.subList()

        return 0L;
    }



    private class Count implements Callable {
        @Override
        public Object call() throws Exception {
            return null;
        }
    }

    public static void main(String[] args) {
        List<Long> longList = new ArrayList<>();
        for(long i = 0; i < 10000000L; i++){
            longList.add(i);
        }


    }
}
