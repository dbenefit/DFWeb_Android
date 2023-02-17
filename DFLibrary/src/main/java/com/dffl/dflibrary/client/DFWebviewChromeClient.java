package com.dffl.dflibrary.client;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.dffl.dflibrary.webivew.DFWebviewActivity;

public class DFWebviewChromeClient  extends WebChromeClient {
    DFWebviewActivity activity;

    public DFWebviewChromeClient(DFWebviewActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        activity.setTitle(title);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
       if (fileChooserParams.getAcceptTypes()==null||fileChooserParams.getAcceptTypes()[0].isEmpty()){
           return false;
       }
        activity.onShowFileChooser(webView,filePathCallback,fileChooserParams);
        return true;
    }
}
