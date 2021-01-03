package com.sbzl.framework.redis.problem.doublewrite;

public class DoubleWriteOperation{


    private OperationDB operationDB;

    /**
     * 先更新数据库，再删除缓存 穿透
     * @return
     */
    public int cacheAndStorage(){
        OperationDB.KeyDO keyDO = new OperationDB.KeyDO();
        int i = operationDB.operationToDB(keyDO);
        if(i != 0){
            throw new RuntimeException("更新失败！！！");
        }
        return operationDB.deleteCache(keyDO);
    }

}
