package com.dongffl.dfweb.webivew.bean;


import com.dongffl.dfweb.JsonHelper;
import com.dongffl.dfweb.webivew.bean.busicess.JSResponseBean;

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

    public String buildResponse()
    {
        return  JsonHelper.toJSON(response).toString();

//        return new Gson().toJson(response);
    }

    public String buildNormalFailed(String callTag) {
        response.setCallbackTag(callTag);
        response.setMsg("failed");
        response.setCode(JSResponseCode.FAILED.getCode());
        return JsonHelper.toJSON(response).toString();
    }

    public String buildNormalSuccess(String callTag) {
        response.setCallbackTag(callTag);
        response.setMsg("success");
        response.setCode(JSResponseCode.SUCCESS.getCode());
        return JsonHelper.toJSON(response).toString();
    }

    public String buildParamError(String callTag) {
        response.setCallbackTag(callTag);
        response.setMsg("failed");
        response.setCode(JSResponseCode.ERROR_FORMAT.getCode());
        return JsonHelper.toJSON(response).toString();
    }
}
