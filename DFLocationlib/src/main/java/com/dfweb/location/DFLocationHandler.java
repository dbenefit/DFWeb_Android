package com.dfweb.location;

import androidx.fragment.app.FragmentActivity;

import com.dffl.dfbaselibrary.bean.GPSResponseBean;
import com.dffl.dfbaselibrary.handlers.DFJSBridgeHandler;
import com.dffl.dfbaselibrary.handlers.DFJsBridgeCallback;

public class DFLocationHandler extends DFJSBridgeHandler {
    @Override
    public void handle(FragmentActivity activity, String param, DFJsBridgeCallback dfJsBridgeCallback) {
        LocationUtil.getInstance().startLocationCheck(activity, new LocationCallback() {
            @Override
            public void onSuccessLocationListener(GPSResponseBean gpsResponseBean) {
                if (gpsResponseBean.getErrorCode()!=-1){
                    dfJsBridgeCallback.success(gpsResponseBean);
                }else {
                    dfJsBridgeCallback.failed();
                }
            }
        });
    }
}
