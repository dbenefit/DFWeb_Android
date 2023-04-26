package com.dongffl.dfweb.location;

import androidx.fragment.app.FragmentActivity;

import com.dongffl.dfweb.bean.GPSResponseBean;
import com.dongffl.dfweb.bean.JSResponseBuilder;
import com.dongffl.dfweb.bean.JSResponseCode;
import com.dongffl.dfweb.handlers.JSBridgeHandler;
import com.dongffl.dfweb.handlers.JSHandlerCallback;

public class DFLocationHandler implements JSBridgeHandler {
    @Override
    public void handle(FragmentActivity activity, JSHandlerCallback callback, String param, String callTag) {
        JSResponseBuilder jsResponseBuilder = new JSResponseBuilder().setCallbackTag(callTag);
        LocationUtil.getInstance().startLocationCheck(activity, new LocationCallback() {
            @Override
            public void onSuccessLocationListener(GPSResponseBean gpsResponseBean) {
                if (gpsResponseBean.getErrorCode() != -1) {
                    jsResponseBuilder
                            .setCode(JSResponseCode.SUCCESS.getCode())
                            .setResponse(gpsResponseBean)
                            .setMessage("success");
                    callback.callJsBridgeResult(jsResponseBuilder.buildResponse());
                } else {
                    jsResponseBuilder
                            .setCode(JSResponseCode.FAILED.getCode())
                            .setMessage("failed");
                    callback.callJsBridgeResult(jsResponseBuilder.buildResponse());
                }
            }
        });
    }
}
