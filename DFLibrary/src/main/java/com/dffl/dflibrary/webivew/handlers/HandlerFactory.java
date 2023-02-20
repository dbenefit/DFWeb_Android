package com.dffl.dflibrary.webivew.handlers;

import android.text.TextUtils;

import com.dffl.dflibrary.webivew.config.JSHandlerPath;

public class HandlerFactory {
    public static JSBridgeHandler createHandler(String name) {

        if (TextUtils.equals(name, JSHandlerPath.GET_GPS_LOC)) {
            return new LocationHandler();
        }
        if (TextUtils.equals(name, JSHandlerPath.SCAN_CODE)) {
            return new ScanHandler();
        }
        return null;
    }
}
