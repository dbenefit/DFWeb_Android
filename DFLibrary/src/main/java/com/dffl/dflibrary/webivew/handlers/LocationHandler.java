package com.dffl.dflibrary.webivew.handlers;

import androidx.fragment.app.FragmentActivity;

import com.dffl.dflibrary.DFManager;
import com.dffl.dflibrary.location.GPSResponseBean;
import com.dffl.dflibrary.location.LocationCallback;
import com.dffl.dflibrary.webivew.bean.JSResponseBuilder;
import com.dffl.dflibrary.webivew.bean.JSResponseCode;
import com.dffl.dflibrary.webivew.plugin.PluginContainer;
import com.dffl.dflibrary.webivew.plugin.SelfImplPluginCallback;

public class LocationHandler implements JSBridgeHandler {
    @Override
    public void doWhat(FragmentActivity activity, JSHandlerCallback callback, String callTag) {
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
