package com.dffl.dfbaselibrary.config;

import java.util.ArrayList;

public class HandlerPathCollect {
    public static ArrayList<String> collectAllJSMethodName(){
        ArrayList<String> path = new ArrayList<>();
        path.add(JSHandlerPath.FINISH);
        path.add(JSHandlerPath.DEVICE_INFO);
        path.add(JSHandlerPath.TAKE_PHOTO_ONLY);
        path.add(JSHandlerPath.OPEN_NEW_WEB);
        path.add(JSHandlerPath.GET_GPS_LOC);
        path.add(JSHandlerPath.SET_PAGE_TITLE);
        path.add(JSHandlerPath.CHOOSE_PIC);
        path.add(JSHandlerPath.SCAN_CODE);

        return path;
    }
}
