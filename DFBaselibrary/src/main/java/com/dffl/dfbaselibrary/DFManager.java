package com.dffl.dfbaselibrary;

import com.dffl.dfbaselibrary.handlers.JSBridgeHandler;
import com.dffl.dfbaselibrary.handlers.ScanHandler;
import com.dffl.dfbaselibrary.location.DFLocationHandler;


public class DFManager {
    private String userAgentString = "";

    private DFManager() {
        registerLocationHandler();
    }

    ScanHandler scanHandler;
    JSBridgeHandler locationHandler;

    public static DFManager getSingleton() {
        return Inner.instance;
    }

    public ScanHandler getScanHandler() {
        return scanHandler;
    }

    public JSBridgeHandler getLocationHandler() {
        return locationHandler;
    }

    private static class Inner {
        private static final DFManager instance = new DFManager();
    }

    public String getUserAgentString() {
        return userAgentString;
    }

    public void setUserAgentFlag(String userAgent) {
        this.userAgentString = userAgent;
    }


    public void registerScanHandler(ScanHandler scanHandler) {
        if (scanHandler != null) {
            this.scanHandler = scanHandler;
        }
    }

    public void registerLocationHandler() {
        if (locationHandler != null) {
            this.locationHandler = new DFLocationHandler();
        }
    }

}
