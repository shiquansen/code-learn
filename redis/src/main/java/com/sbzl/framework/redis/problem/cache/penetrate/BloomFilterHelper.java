package com.sbzl.framework.redis.problem.cache.penetrate;

import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


//https://www.cnblogs.com/woshixiangshang/p/11412474.html
public class BloomFilterHelper<T>  {

    private int numHashFunctions;

    private int bitSize;

    private Funnel<T> funnel;

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            redisTemplate.opsForValue().setBit(key, i, true);
        }
    }

    /**
     * 根据给定的布隆过滤器判断值是否存在
     */
    public <T> boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            if (!redisTemplate.opsForValue().getBit(key, i)) {
                return false;
            }
        }

        return true;
    }

    /** * 计算bit数组的长度 */
    private int optimalNumOfBits(long n, double p) {
        if (p == 0) { p = Double.MIN_VALUE; } return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }
    /**
     * 计算hash方法执行次数
     *
     */
    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }



}
