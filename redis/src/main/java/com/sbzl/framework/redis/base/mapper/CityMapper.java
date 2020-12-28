package com.sbzl.framework.redis.base.mapper;

import com.sbzl.framework.redis.base.domain.City;

import java.util.List;

/**
 * 城市 DAO 接口类
 *
 */
public interface CityMapper {

    /**
     * 获取城市信息列表
     *
     * @return
     */
    List<City> findAllCity();

    /**
     * 根据城市 ID，获取城市信息
     *
     * @param id
     * @return
     */
    City findById(Long id);

    Long saveCity(City city);

    Long updateCity(City city);

    Long deleteCity(Long id);
}