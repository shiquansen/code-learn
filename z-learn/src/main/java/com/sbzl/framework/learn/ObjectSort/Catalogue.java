package com.sbzl.framework.learn.ObjectSort;

public class Catalogue {

   private Integer parentOrder;
   private String name;

    @Override
    public String toString() {
        return "Catalogue{" +
                "parentOrder=" + parentOrder +
                ", name='" + name + '\'' +
                '}';
    }

    public Catalogue() {
    }

    public Catalogue(Integer parentOrder, String name) {
        this.parentOrder = parentOrder;
        this.name = name;
    }

    public Integer getParentOrder() {
        return parentOrder;
    }

    public void setParentOrder(Integer parentOrder) {
        this.parentOrder = parentOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
