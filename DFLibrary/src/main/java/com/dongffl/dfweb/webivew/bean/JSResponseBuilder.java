package com.dongffl.dfweb.webivew.bean;


import com.dongffl.dfweb.location.GPSResponseBean;
import com.dongffl.dfweb.webivew.bean.busicess.JSResponseBean;
import com.dongffl.dfweb.webivew.bean.busicess.ScanResultBean;

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

        } catch (Exception e) {

        }
        return "";
    }

//    public String buildNormalFailed(String callTag) {
//        response.setCallbackTag(callTag);
//        response.setMsg("failed");
//        response.setCode(JSResponseCode.FAILED.getCode());
//        return JsonHelper.toJSON(response).toString();
//    }
//
//    public String buildNormalSuccess(String callTag) {
//        response.setCallbackTag(callTag);
//        response.setMsg("su\n" +
//                "02/28 19:10:37: Launching 'app' on OnePlus KB2000.ccess");
//        response.setCode(JSResponseCode.SUCCESS.getCode());
//        return JsonHelper.toJSON(response).toString();
//    }
//
//    public String buildParamError(String callTag) {
//        response.setCallbackTag(callTag);
//        response.setMsg("failed");
//        response.setCode(JSResponseCode.ERROR_FORMAT.getCode());
//        return JsonHelper.toJSON(response).toString();
//    }
}
