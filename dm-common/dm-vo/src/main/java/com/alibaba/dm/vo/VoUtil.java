package com.alibaba.dm.vo;

public class VoUtil<T> {

    public static<T> CommonResponse<T> returnSuccess(String msg,T data){
        return new CommonResponse<T>(msg,data);
    }

    public static<T> CommonResponse<T> returnFailure(String errCode,String msg){
        return new CommonResponse<T>(errCode,msg);
    }

}
