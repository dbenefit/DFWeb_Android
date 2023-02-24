package com.dongffl.dfweb.webivew.handlers;

import androidx.fragment.app.FragmentActivity;

import com.dongffl.dfweb.DFManager;
import com.dongffl.dfweb.location.GPSResponseBean;
import com.dongffl.dfweb.location.LocationCallback;
import com.dongffl.dfweb.webivew.bean.JSResponseBuilder;
import com.dongffl.dfweb.webivew.bean.JSResponseCode;

public class LocationHandler implements JSBridgeHandler {
    @Override
    public void doWhat(FragmentActivity activity, JSHandlerCallback callback, String param, String callTag) {
        JSResponseBuilder jsResponseBuilder = new JSResponseBuilder().setCallbackTag(callTag);
        DFManager.getSingleton().startLocation(activity, new LocationCallback() {
            @Override
            public void onSuccessLocationListener(GPSResponseBean gpsResponseBean) {
                if (gpsResponseBean.getErrorCode() != -1) {
                    jsResponseBuilder
                            .setCode(JSResponseCode.SUCCESS.getCode())
                            .setResponse(gpsResponseBean)
                            .setMessage("success");
                    callback.endWord(jsResponseBuilder.buildResponse());
                } else {
                    jsResponseBuilder
                            .setCode(JSResponseCode.FAILED.getCode())
                            .setMessage("failed");
                    callback.endWord(jsResponseBuilder.buildResponse());
                }
            }
        });
    }
}
