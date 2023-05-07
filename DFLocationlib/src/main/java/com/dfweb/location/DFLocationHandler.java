package com.dfweb.location;

import androidx.fragment.app.FragmentActivity;

import com.dffl.dfbaselibrary.bean.GPSResponseBean;
import com.dffl.dfbaselibrary.handlers.JSBridgeHandler;
import com.dffl.dfbaselibrary.plugin.DFJsBridgeCallback;

public class DFLocationHandler extends JSBridgeHandler {
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
