package com.dffl.dfbaselibrary.handlers;

import android.text.TextUtils;

import com.dffl.dfbaselibrary.config.JSHandlerPath;


public class DFHandlerFactory {
    public static DFJSBridgeHandler createHandler(String name) {

        if (TextUtils.equals(name, JSHandlerPath.GET_GPS_LOC)) {
            return DFHandlerContainer.getSingleton().getDFHandler(DFHandlerStyle.LOCATION);
        }
        if (TextUtils.equals(name, JSHandlerPath.SCAN_CODE)) {
            return DFHandlerContainer.getSingleton().getDFHandler(DFHandlerStyle.SCAN);
        }
        if (TextUtils.equals(name, JSHandlerPath.OPEN_NEW_WEB)) {
            return DFHandlerContainer.getSingleton().getDFHandler(DFHandlerStyle.OPEN_WEBVIEW);
        }
        if (TextUtils.equals(name, JSHandlerPath.TAKE_PHOTO_ONLY)) {
            return DFHandlerContainer.getSingleton().getDFHandler(DFHandlerStyle.TAKE_PHONE);
        }
        if (TextUtils.equals(name, JSHandlerPath.CHOOSE_PIC)) {
            return DFHandlerContainer.getSingleton().getDFHandler(DFHandlerStyle.CHOOSE_PICTURE);
        }
        if (TextUtils.equals(name, JSHandlerPath.FINISH)) {
            return DFHandlerContainer.getSingleton().getDFHandler(DFHandlerStyle.FINISH_WEBVIEW);
        }
        if (TextUtils.equals(name, JSHandlerPath.SHARE)) {
            return DFHandlerContainer.getSingleton().getDFHandler(DFHandlerStyle.SHARE);
        }
        if (TextUtils.equals(name, JSHandlerPath.GO_BACK)) {
            return DFHandlerContainer.getSingleton().getDFHandler(DFHandlerStyle.BACK_WEBVIEW);
        }
        if (TextUtils.equals(name, JSHandlerPath.SET_PAGE_TITLE)) {
            return DFHandlerContainer.getSingleton().getDFHandler(DFHandlerStyle.SETTITLE);
        }
        return null;
    }
}
