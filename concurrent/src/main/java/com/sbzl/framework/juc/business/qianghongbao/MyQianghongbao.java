package com.sbzl.framework.juc.business.qianghongbao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class MyQianghongbao {



    public static void main(String[] args) throws InterruptedException {
        MyQianghongbao myQianghongbao = new MyQianghongbao();
        //初始化红包
        LinkedBlockingQueue<Money2Person> luckyMoneys = new LinkedBlockingQueue<>();
        BigDecimal money = new BigDecimal(100);
        myQianghongbao.rangeMoney(luckyMoneys, money, 50);
        luckyMoneys.stream().forEach(item -> System.out.println(" 当前的金额" + item.getMoney().floatValue()));
        BigDecimal decimal = luckyMoneys.stream().map(Money2Person::getMoney).reduce(BigDecimal::add).get();
        System.out.println("============Queue=========="+decimal.floatValue());
        //模拟抢红包
        //输出对应结果
    }


     void rangeMoney(LinkedBlockingQueue<Money2Person> decimals, BigDecimal theRest, int n) throws InterruptedException {
        if(n > 0){
            if(n == 1){
                Money2Person luckyMoney = new Money2Person(theRest, null);
                System.out.println("===========放入Queue========" + theRest + " 次数" + n);
                theRest = theRest.subtract(theRest);
                decimals.put(luckyMoney);
            }else{
                float maxF = theRest.floatValue();
                BigDecimal db = new BigDecimal(Math.random() * maxF);
                Money2Person luckyMoney = new Money2Person(db, null);
                decimals.put(luckyMoney);
                theRest = theRest.subtract(db);
                System.out.println("===========放入Queue========" + db+ " 次数" + n);
            }
            n--;
            rangeMoney(decimals, theRest, n);
        }
    }
}


class Money2Person{
    private BigDecimal money;
    private String name;

    public Money2Person(BigDecimal money, String name) {
        this.money = money;
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
