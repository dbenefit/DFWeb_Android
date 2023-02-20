package com.dffl.dflibrary.webivew.jsbridge;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.fragment.app.FragmentActivity;

import com.dffl.dflibrary.webivew.config.HandlerPathCollect;
import com.dffl.dflibrary.webivew.config.JSConfigs;
import com.dffl.dflibrary.webivew.handlers.HandlerFactory;
import com.dffl.dflibrary.webivew.handlers.JSBridgeHandler;
import com.dffl.dflibrary.webivew.handlers.JSHandlerCallback;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public class JSBridgeInterface {
    FragmentActivity ctx;
    WebView webView;
    Boolean isActivity;
    ArrayList<String> mMethodNames = new ArrayList<>();
    HashMap<String, JSBridgeHandler> mHandler = new HashMap<>();
    WeakReference<WebView> mWebView = new WeakReference<>(webView);
    WeakReference<FragmentActivity> mContext ;

    public JSBridgeInterface(FragmentActivity ctx, WebView webView, Boolean isActivity) {
        this.ctx = ctx;
        mContext = new WeakReference<>(this.ctx);
        this.webView = webView;
        this.isActivity = isActivity;
        mMethodNames = HandlerPathCollect.collectAllJSMethodName();
    }
    @JavascriptInterface
    public void dispatchMethod(String methodName, String data, String callBack) {
        if (TextUtils.isEmpty(methodName)) {
            return;
        }
        String callTag = "";
        String params="";
        if (!TextUtils.isEmpty(data)) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                callTag = jsonObject.optString(JSConfigs.CALLBACK_TAG);
                params = jsonObject.optString(JSConfigs.PARAMS);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String pathName = "/handler/" + methodName;
        if (mMethodNames.contains(pathName)) {
            JSBridgeHandler handler = getHandlerByName(pathName);
            if (handler!=null&&mContext.get()!=null){
                String finalCallTag = callTag;
                mContext.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handler.doWhat(ctx, new JSHandlerCallback() {
                            @Override
                            public void endWord(String result) {
                                handCallback(callBack, result);
                            }
                        }, finalCallTag);
                    }
                });

            }
        }
    }
    private void handCallback( String callBack , String response  ) {
        if (callBack != null && mContext.get() != null && mWebView.get() != null && !TextUtils.isEmpty(
                response
        )
        ) {
            //如果有回调方法就执行回调
            String javascript = new StringBuilder("javascript:")
                    .append(callBack)
                    .append("('")
                    .append(response)
                    .append("')").toString();
            if (!TextUtils.isEmpty(callBack)) {
                if (mContext.get()!=null){
                    mContext.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWebView.get().loadUrl(javascript);
                        }
                    });
                }


            }
        }
    }
    private JSBridgeHandler getHandlerByName(  String name)  {
        if (mHandler.containsKey(name)) {
            return mHandler.get(name);
        }
        JSBridgeHandler handler = HandlerFactory.createHandler(name);
        if (handler != null) {
            mHandler.put(name, handler) ;
        }
        return handler;
    }
    @JavascriptInterface
    public void dispatchMethod(String methodName , String data ) {
        dispatchMethod(methodName, data, null);
    }

    // 返回按钮回调
    @JavascriptInterface
    public void onH5BackPressJs() {

    }

    // 分发无参数方法
    @JavascriptInterface
    public void dispatchMethod(String methodName ) {
        dispatchMethod(methodName, null);
    }
}
