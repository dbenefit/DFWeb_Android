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
        if (TextUtils.equals(name, JSHandlerPath.OPEN_NEW_WEB)) {
            return new OpenNewWebHandler();
        }
        if (TextUtils.equals(name, JSHandlerPath.TAKE_PHOTO_ONLY)) {
            return new TakePhotoHandler();
        }
        if (TextUtils.equals(name, JSHandlerPath.CHOOSE_PIC)) {
            return new ChoosePicHandler();
        }
        if (TextUtils.equals(name, JSHandlerPath.FINISH)) {
            return new FinishWebHandler();
        }
        if (TextUtils.equals(name, JSHandlerPath.SHARE)) {
            return new ShareWebHandler();
        }
        if (TextUtils.equals(name, JSHandlerPath.GO_BACK)) {
            return new BackWebHandler();
        } if (TextUtils.equals(name, JSHandlerPath.SET_PAGE_TITLE)) {
            return new SetTitleHandler();
        }
        return null;
    }
}
