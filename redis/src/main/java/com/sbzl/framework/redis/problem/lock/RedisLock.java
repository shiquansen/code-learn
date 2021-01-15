package com.sbzl.framework.redis.problem.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁的简单实现
 */
public class RedisLock {

    private static final String LOCK_PREFIX = "lock:";

    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    /**
     * @param key
     * @param lockedMillis  锁多长时间
     * @return 超时时间点
     */
    public long tryLock(final String key, final long lockedMillis){
        String k = key(key);
        long expireAtTime = System.currentTimeMillis() + lockedMillis;
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(k, expireAtTime);
        if(null != locked && locked){
            redisTemplate.expire(k, 1, TimeUnit.SECONDS);
            return expireAtTime;
        }
        return -1;
    }

    public long lock(final String k, final long lockedMillis, long waitMills){
        long expireAt = -1;
        long sleep = 1 << 4;
        while (expireAt < 0 && waitMills > 0 && sleep > 0){
            expireAt = tryLock(k, lockedMillis);
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                logger.warn("Lock interrupted! key {}", k);
            }

            waitMills -= sleep;
            sleep = sleep << 1;

            Long expireAtTime = redisTemplate.opsForValue().get(key(k));
            if(null == expireAtTime){
                //无条件再试一次
                expireAt = tryLock(k, lockedMillis);
            }else if(expireAtTime <= System.currentTimeMillis()){
                //前一把锁已经超时
                long newExpireAt = System.currentTimeMillis() + lockedMillis;
                Long old = redisTemplate.opsForValue().getAndSet(key(k), newExpireAt);
                if(null != old && old .equals(expireAtTime)){
                    return newExpireAt;
                }
            }
        }
        return expireAt;
    }

    /**
     * 释放锁
     * @param k
     * @param expireAtTime
     */
    public void unlock(String k, long expireAtTime){
        k = key(k);
        Long lockTime = redisTemplate.opsForValue().get(k);
        if(null != lockTime && expireAtTime == lockTime){
            redisTemplate.delete(k);
        }
    }

    private String key(String key) {
        return LOCK_PREFIX + key;
    }


    public static void main(String[] args) {
        RedisLock redisLock = new RedisLock();
        String k = "";
        Long lockMillis = 100000L;
        Long waitMillis = 100000L;
        Long expireAtTime = 100000L;
        try{
            redisLock.lock(k, lockMillis, waitMillis);
        }finally {
            redisLock.unlock(k, expireAtTime);
        }

    }
}
