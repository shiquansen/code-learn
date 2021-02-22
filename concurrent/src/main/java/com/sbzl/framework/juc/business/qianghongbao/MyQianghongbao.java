package com.sbzl.framework.juc.business.qianghongbao;

import java.math.BigDecimal;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class MyQianghongbao {



    public static void main(String[] args) throws InterruptedException {
        MyQianghongbao myQianghongbao = new MyQianghongbao();
        //初始化红包
        LinkedBlockingQueue<Money2Person> luckyMoneys = new LinkedBlockingQueue<>();
        CopyOnWriteArrayList<Money2Person> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        BigDecimal money = new BigDecimal(100);
        myQianghongbao.rangeMoney(luckyMoneys, money, 10);
        BigDecimal decimal = luckyMoneys.stream().map(Money2Person::getMoney).reduce(BigDecimal::add).get();
        System.out.println("============Queue=========="+decimal.floatValue());
        //模拟抢红包
        ExecutorService service = Executors.newFixedThreadPool(50);
        for(int i = 0; i < 50; i++){
            service.execute(() -> {
                if(luckyMoneys.size() > 0){
                    Money2Person a = luckyMoneys.poll();
                    a.setName(Thread.currentThread().getName());
                    copyOnWriteArrayList.add(a);
                    System.out.println(Thread.currentThread().getName() + "强到了红红包" + a.getMoney());
                }
            });
        }
        //输出对应结果
        BigDecimal total = copyOnWriteArrayList.stream().map(Money2Person::getMoney).reduce(BigDecimal::add).get();
        System.out.println("总金额为" + total);

    }


/*
    //优化红包金额，在0.01和剩余平均数两倍之间
    public static double getRandomMoney(RedPackage _redPackage) {
        // remainSize 剩余的红包数量
        // remainMoney 剩余的钱
        if (_redPackage.remainSize == 1) {
            _redPackage.remainSize--;
            return (double) Math.round(_redPackage.remainMoney * 100) / 100;
        }
        Random r     = new Random();
        double min   = 0.01; //
        double max   = _redPackage.remainMoney / _redPackage.remainSize * 2;
        double money = r.nextDouble() * max;
        money = money &lt;= min ? 0.01: money;
        money = Math.floor(money * 100) / 100;
        _redPackage.remainSize--;
        _redPackage.remainMoney -= money;
        return money;
    }*/


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
