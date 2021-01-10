package com.sbzl.framework.redis.problem.cache.penetrate;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.sbzl.framework.redis.base.domain.City;
import com.sbzl.framework.redis.base.mapper.CityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存穿透
 */
public class Penetrate<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Penetrate.class);

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 第一种是缓存层缓存空值
     *
     * 　　　　将数据库中的空值也缓存到缓存层中，这样查询该空值就不会再访问DB，而是直接在缓存层访问就行。
     * 　　　　但是这样有个弊端就是缓存太多空值占用了更多的空间，可以通过给缓存层空值设立一个较短的过期时间来解决，例如60s
     */
    public Object bloomFilter(long id){
        // 从缓存中获取城市信息
        String key = "city_" + id;
        ValueOperations<String, City> operations = redisTemplate.opsForValue();

        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            City city = operations.get(key);
            return city;
        }

        // 从 DB 中获取城市信息
        City city = cityMapper.findById(id);

        if(city != null){
            // 插入缓存
            operations.set(key, city, 10, TimeUnit.HOURS);
            LOGGER.info("CityServiceImpl.findCityById() : 城市插入缓存 >> " + city.toString());
        }if(city == null){
            // 插入缓存
            operations.set(key, null, 1, TimeUnit.SECONDS);
            LOGGER.info("CityServiceImpl.findCityById() : 城市插入缓存 >> null");
        }
        return city;
    }




    /**
     * 　　第二种是布隆过滤器
     *
     * 　　　　将数据库中所有的查询条件，放入布隆过滤器中，
     * 　　　　当一个查询请求过来时，先经过布隆过滤器进行查，如果判断请求查询值存在，则继续查；如果判断请求查询不存在，直接丢弃。
     */

    /**
     * static <T> BloomFilter<T> create(Funnel<? super T> funnel, long expectedInsertions, double fpp, BloomFilter.Strategy strategy)
     * funnel是插入数据的Funnel，expectedInsertions是期望插入的元素总个数n，fpp即期望假阳性率p，strategy即哈希策略。
     *
     * https://www.cnblogs.com/luxianyu-s/p/12686466.html   布隆过滤器介绍
     * https://blog.csdn.net/tianyaleixiaowu/article/details/74739827   布隆过滤器使用
     */


    private static int size = 1000000;
    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, 0.0001);


    public static void bloomFilter(){
        /**
         * bits即上文讲到的长度为m的布隆位数组，采用LockFreeBitArray类型做了封装。   （布隆数组）
         * numHashFunctions即哈希函数的个数k。
         * funnel是Funnel接口实现类的实例，它用于将任意类型T的输入数据转化为Java基本类型的数据（byte、int、char等等）。这里是会转化为byte。
         * strategy是布隆过滤器的哈希策略，即数据如何映射到位数组，其具体方法在BloomFilterStrategies枚举中。
         */
        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }

        for (int i = 0; i < size; i++) {
            if (!bloomFilter.mightContain(i)) {
                System.out.println("有坏人逃脱了");
            }
        }

        List<Integer> list = new ArrayList<Integer>(1000);
        for (int i = size + 10000; i < size + 20000; i++) {
            if (bloomFilter.mightContain(i)) {
                list.add(i);
            }
        }
        System.out.println("有误伤的数量：" + list.size());
    }

    public static void main(String[] args) {
        bloomFilter();
    }



}
