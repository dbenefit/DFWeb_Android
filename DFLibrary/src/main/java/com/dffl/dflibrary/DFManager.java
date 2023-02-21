package com.dffl.dflibrary;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dffl.dflibrary.location.LocationActivity;
import com.dffl.dflibrary.location.LocationCallback;
import com.dffl.dflibrary.scan.CaptureStartup;
import com.dffl.dflibrary.scan.callback.ResultCallBack;
import com.dffl.dflibrary.webivew.DFWebviewActivity;
import com.dffl.dflibrary.webivew.plugin.SelfImplPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DFManager {
    private Map<String, String> cookieMap = new HashMap<>();
    private ArrayList<LocationCallback> locationCallbacks = new ArrayList<>();
    private String userAgentString = "";

    public ArrayList<LocationCallback> getLocationCallbacks() {
        return locationCallbacks;
    }

    public void setLocationCallbacks(ArrayList<LocationCallback> locationCallbacks) {
        this.locationCallbacks = locationCallbacks;
    }

    LocationCallback locationCallback;

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

    public void init(Application application) {
        Utils.getSingleton().init(application);
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
        locationCallbacks.remove(locationCallback);
    }

    public void startLocation(Context context, LocationCallback locationCallback) {
        if (locationCallback != null) {
            if (!locationCallbacks.contains(locationCallback)) {
                locationCallbacks.add(locationCallback);
            }
        } else {
            Toast.makeText(context, "请设置接口回调！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (context == null) {
            Toast.makeText(Utils.getApp(), "请设置上下文！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (context instanceof AppCompatActivity) {
            context.startActivity(new Intent(context, LocationActivity.class));
        } else {
            Intent intent = new Intent(context, LocationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public void startScan(AppCompatActivity appCompatActivity, ResultCallBack resultCallBack) {
        new CaptureStartup().from(appCompatActivity).setType(0).create().forResult(resultCallBack);
    }
}
