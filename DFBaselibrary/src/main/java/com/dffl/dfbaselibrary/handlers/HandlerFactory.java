package com.dffl.dfbaselibrary.handlers;

import android.text.TextUtils;

import com.dffl.dfbaselibrary.DFManager;
import com.dffl.dfbaselibrary.config.JSHandlerPath;

public class HandlerFactory {
    public static JSBridgeHandler createHandler(String name) {

        if (TextUtils.equals(name, JSHandlerPath.GET_GPS_LOC)) {
            if (DFManager.getSingleton().getLocationHandler() != null) {
                return DFManager.getSingleton().getLocationHandler();
            }
        }
        if (TextUtils.equals(name, JSHandlerPath.SCAN_CODE)) {
            if (DFManager.getSingleton().getScanHandler() != null) {
                return DFManager.getSingleton().getScanHandler();
            }
        }
        return null;
    }
}
