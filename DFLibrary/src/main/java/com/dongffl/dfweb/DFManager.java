package com.dongffl.dfweb;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.dongffl.dfweb.location.LocationCallback;
import com.dongffl.dfweb.scan.CaptureStartup;
import com.dongffl.dfweb.scan.callback.ResultCallBack;
import com.dongffl.dfweb.webivew.DFWebviewActivity;

import java.util.HashMap;
import java.util.Map;

public class DFManager {
    private Map<String, String> cookieMap = new HashMap<>();
    private String userAgentString = "";

    public String TAG = "DFSDK --";


    private DFManager() {
    }

    public static DFManager getSingleton() {
        return Inner.instance;
    }

    private static class Inner {
        private static final DFManager instance = new DFManager();
    }

    public void setCookie(Map<String, String> cookie) {
        this.cookieMap = cookie;
    }

    public Map<String, String> getCookieMap() {
        return cookieMap;
    }

    public String getUserAgentString() {
        return userAgentString;
    }

    public void setUserAgent(String userAgent) {
        this.userAgentString = userAgent;
    }


    public void startDFWebPage(Context context, String url, HashMap<String, String> params) {
        Intent intent = new Intent(context, DFWebviewActivity.class);
        intent.putExtra("url", url);
        if (params != null) {
            intent.putExtra("params", params);
        }
        context.startActivity(intent);
    }

    public void removeLocationCallback(LocationCallback locationCallback) {
        LocationUtil.getInstance().removeLocationCallback(locationCallback);
    }
    public void startLocation(Context context, LocationCallback locationCallback) {
       LocationUtil.getInstance().startLocationCheck(context,locationCallback);
    }

    public void startScan(FragmentActivity fragmentActivity, ResultCallBack resultCallBack) {
        new CaptureStartup().from(fragmentActivity).setType(0).create().forResult(resultCallBack);
    }
}
