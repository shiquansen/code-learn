package com.sbzl.framework.learn.globalException;

public class ResultVO<T> {
    public static final String SUCCESS = "200";
    public static final String FAIL = "500";

    private String stateCode;
    private String stateInfo;
    private T data;

    public static ResultVO ok(String msg) {
        return new ResultVO(SUCCESS, msg, null);
    }

    public static ResultVO ok(String msg, Object obj) {
        return new ResultVO(SUCCESS, msg, obj);
    }



    public static ResultVO error(String msg) {
        return new ResultVO(FAIL, msg, null);
    }

    public static ResultVO error(String msg, Object obj) {
        return new ResultVO(FAIL, msg, obj);
    }

    public ResultVO() {
    }

    public ResultVO(String stateCode, String stateInfo, T data) {
        this.stateCode = stateCode;
        this.stateInfo = stateInfo;
        this.data = data;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVO{" +
                "stateCode='" + stateCode + '\'' +
                ", stateInfo='" + stateInfo + '\'' +
                ", data=" + data +
                '}';
    }
}
