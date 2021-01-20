package com.sbzl.framework.learn.ObjectSort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Catalogue> catalogueVOList = new ArrayList<>();

        catalogueVOList.add(new Catalogue(1, "sss1"));
        catalogueVOList.add(new Catalogue(10, "sss10"));
        catalogueVOList.add(new Catalogue(8, "sss8"));
        catalogueVOList.add(new Catalogue(2, "sss2"));
        catalogueVOList.add(new Catalogue(3, "sss3"));
        catalogueVOList.add(new Catalogue(4, "sss4"));
        catalogueVOList.add(new Catalogue(9, "sss9"));
        catalogueVOList.add(new Catalogue(6, "sss6"));
        catalogueVOList.sort(Comparator.comparingInt(Catalogue::getParentOrder));
        System.out.println(catalogueVOList);
    }
}
