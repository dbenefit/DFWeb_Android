package com.dongffl.dfweb.webivew.jsbridge;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.fragment.app.FragmentActivity;


import com.dongffl.dfweb.config.HandlerPathCollect;
import com.dffl.dfbaselibrary.JSConfigs;
import com.dongffl.dfweb.handlers.HandlerFactory;
import com.dongffl.dfweb.handlers.JSBridgeHandler;
import com.dongffl.dfweb.handlers.JSHandlerCallback;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public class JSBridgeInterface {

    ArrayList<String> mMethodNames = new ArrayList<>();
    HashMap<String, JSBridgeHandler> mHandler = new HashMap<>();
    WeakReference<WebView> mWebView ;
    WeakReference<FragmentActivity> mContext ;

    public JSBridgeInterface(FragmentActivity ctx, WebView webView) {
        mContext = new WeakReference<>(ctx);
        mWebView = new WeakReference<>(webView);
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
                String finalParams = params;
                mContext.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handler.handle(mContext.get(), new JSHandlerCallback() {
                            @Override
                            public void callJsBridgeResult(String result) {
                                handCallback(callBack, result);
                            }
                        }, finalParams,finalCallTag);
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
