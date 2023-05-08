package com.dffl.dfbaselibrary.bean;


/**
 * 应用模块:
 * <p>
 * 类描述: js定位回调
 */
public class GPSResponseBean {

    private double latitude;//维度
    private double longitude;//经度
    private String province;//省
    private String city;//城市
    private String cityCode;//城市代码
    private String address;//详细地址
    private String country;//国家
    private String poiName;//位置名称
    private String street;//街道
    private int errorCode;//错误码
    private String errorInfo;//错误信息


    public GPSResponseBean() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }


    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
