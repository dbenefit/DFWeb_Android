package com.dffl.dfbaselibrary.handlers;

import android.text.TextUtils;

import com.dffl.dfbaselibrary.DFManager;
import com.dffl.dfbaselibrary.config.JSHandlerPath;
import com.dffl.dfbaselibrary.location.DFLocationHandler;

public class HandlerFactory {
    public static JSBridgeHandler createHandler(String name) {

        if (TextUtils.equals(name, JSHandlerPath.GET_GPS_LOC)) {
             return new  DFLocationHandler();
        }
        if (TextUtils.equals(name, JSHandlerPath.SCAN_CODE)) {
             return new ScanHandler();
        }
        return null;
    }
}
