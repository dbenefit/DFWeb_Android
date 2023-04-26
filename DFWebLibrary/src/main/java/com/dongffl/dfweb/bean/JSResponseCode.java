package com.dongffl.dfweb.bean;

public enum JSResponseCode {
    SUCCESS(0),
    FAILED(101),
    ERROR_FORMAT(201),
    CANCEL(301);
    private  int code=0;
    private JSResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
