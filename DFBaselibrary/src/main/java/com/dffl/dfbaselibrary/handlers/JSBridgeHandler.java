package com.dffl.dfbaselibrary.handlers;

import androidx.fragment.app.FragmentActivity;

import com.dffl.dfbaselibrary.bean.JSResponseBuilder;
import com.dffl.dfbaselibrary.bean.JSResponseCode;
import com.dffl.dfbaselibrary.plugin.DFJsBridgeCallback;

public abstract class JSBridgeHandler {

    public abstract void handle(FragmentActivity activity, String param, DFJsBridgeCallback dfJsBridgeCallback);

    public void handleJsResponse(FragmentActivity activity, JSHandlerCallback callback, String param, String callTag) {
        handle(activity, param, new DFJsBridgeCallback() {
            @Override
            public void success(Object result) {
                callback.callJsBridgeResult(buildSuccess(result, callTag));
            }
            @Override
            public void failed() {
                buildFailed(callTag);
            }
            @Override
            public void cancel() {
                buildCanceled(callTag);
            }
        });

    }

    public String buildFailed(String callTag) {
        JSResponseBuilder jsResponseBuilder = new JSResponseBuilder().setCallbackTag(callTag);
        jsResponseBuilder.setCallbackTag(callTag).setMessage("failed").setCode(JSResponseCode.FAILED.getCode());
        return jsResponseBuilder.buildResponse();
    }

    public String buildCanceled(String callTag) {
        JSResponseBuilder jsResponseBuilder = new JSResponseBuilder().setCallbackTag(callTag);
        jsResponseBuilder.setCallbackTag(callTag).setMessage("canceled").setCode(JSResponseCode.CANCEL.getCode());
        return jsResponseBuilder.buildResponse();
    }

    public String buildSuccess(Object result, String callTag) {
        JSResponseBuilder jsResponseBuilder = new JSResponseBuilder().setCallbackTag(callTag);
        jsResponseBuilder
                .setCode(JSResponseCode.SUCCESS.getCode())
                .setResponse(result)
                .setResponse(result)
                .setMessage("success");
        return jsResponseBuilder.buildResponse();
    }

}
