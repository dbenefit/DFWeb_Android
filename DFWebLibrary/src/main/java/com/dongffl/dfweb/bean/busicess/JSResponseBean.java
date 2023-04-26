package com.dongffl.dfweb.bean.busicess;

public class JSResponseBean<T> {
    int code=0;
    String callbackTag  = null; //调用js的方法标记
    T data  = null;//给js的参数对象
    String msg  = null;//message

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCallbackTag() {
        return callbackTag;
    }

    public void setCallbackTag(String callbackTag) {
        this.callbackTag = callbackTag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
