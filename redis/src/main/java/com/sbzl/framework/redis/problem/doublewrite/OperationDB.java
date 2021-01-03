package com.sbzl.framework.redis.problem.doublewrite;

import com.sbzl.framework.redis.base.domain.City;

public interface OperationDB {

    /**
     * 入库的方法
     * @return
     */
    int operationToDB(KeyDO keyDO);

    int deleteCache(KeyDO keyDO);

    class KeyDO{

    }
}
