package com.dffl.dfbaselibrary.bean;



import com.dffl.dfbaselibrary.bean.busicess.JSResponseBean;
import com.dffl.dfbaselibrary.bean.busicess.ScanResultBean;

import org.json.JSONObject;

public class JSResponseBuilder {

    private JSResponseBean response;

    public JSResponseBuilder() {
        response = new JSResponseBean();
    }

    public JSResponseBuilder setCode(Integer code) {
        response.setCode(code);
        return this;
    }

    public JSResponseBuilder setCallbackTag(String tag) {
        response.setCallbackTag(tag);
        return this;
    }

    public JSResponseBuilder setResponse(Object res) {
        response.setData(res);
        return this;
    }

    public JSResponseBuilder setMessage(String message) {
        response.setMsg(message);
        return this;
    }

    public String buildResponse() {
        try {
            if (response.getData() instanceof GPSResponseBean) {
                GPSResponseBean gpsResponseBean = (GPSResponseBean) response.getData();
                JSONObject gpsResponseJsonObject = new JSONObject();
                gpsResponseJsonObject.put("latitude", gpsResponseBean.getLatitude());
                gpsResponseJsonObject.put("longitude", gpsResponseBean.getLongitude());
                gpsResponseJsonObject.put("address", gpsResponseBean.getAddress());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("callbackTag", response.getCallbackTag());
                jsonObject.put("msg", response.getMsg());
                jsonObject.put("data", gpsResponseJsonObject);
                jsonObject.put("code", response.getCode());
                return jsonObject.toString();
            }
            if (response.getData() instanceof ScanResultBean) {
                ScanResultBean scanResultBean = (ScanResultBean) response.getData();
                JSONObject gpsResponseJsonObject = new JSONObject();
                gpsResponseJsonObject.put("resultUrl", scanResultBean.getResultUrl());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("callbackTag", response.getCallbackTag());
                jsonObject.put("msg", response.getMsg());
                jsonObject.put("data", gpsResponseJsonObject);
                jsonObject.put("code", response.getCode());
                return jsonObject.toString();
            }
            if (response.getData() instanceof TakePhotoResponse) {
                TakePhotoResponse scanResultBean = (TakePhotoResponse) response.getData();
                 JSONObject jsonObject = new JSONObject();
                jsonObject.put("callbackTag", response.getCallbackTag());
                jsonObject.put("msg", response.getMsg());
                jsonObject.put("data", scanResultBean.url);
                jsonObject.put("code", response.getCode());
                return jsonObject.toString();
            }
            if (response.getData() instanceof ChoosePicResponse) {
                ChoosePicResponse choosePicResponse = (ChoosePicResponse) response.getData();
                 JSONObject jsonObject = new JSONObject();
                jsonObject.put("callbackTag", response.getCallbackTag());
                jsonObject.put("msg", response.getMsg());
                jsonObject.put("data", choosePicResponse.urls);
                jsonObject.put("code", response.getCode());
                return jsonObject.toString();
            }

        } catch (Exception e) {

        }
        return "";
    }


}
