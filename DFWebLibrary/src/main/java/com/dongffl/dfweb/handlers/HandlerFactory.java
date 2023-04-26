package com.dongffl.dfweb.handlers;

import android.text.TextUtils;

import com.dongffl.dfweb.config.JSHandlerPath;
import com.dongffl.dfweb.location.DFLocationHandler;

public class HandlerFactory {
    public static JSBridgeHandler createHandler(String name) {

        if (TextUtils.equals(name, JSHandlerPath.GET_GPS_LOC)) {
             return new DFLocationHandler();
        }
        if (TextUtils.equals(name, JSHandlerPath.SCAN_CODE)) {
             return new ScanHandler();
        }
        return null;
    }
}
