package com.alibaba.dm.vo;

public class CommonResponse<T> {
    private String errorCode;
    private String success;
    private String msg;
    private T data;

    public CommonResponse(String msg ,String errCode) {
        this.msg=msg;
        this.errorCode=errCode;
    }
    public CommonResponse(String msg,T data) {
        this.success="0000";
        this.errorCode="0000";
        this.msg=msg;
        this.data=data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
