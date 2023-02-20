package com.dffl.dflibrary.client;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.dffl.dflibrary.webivew.DFWebviewActivity;

import java.lang.ref.WeakReference;

public class DFWebviewChromeClient extends WebChromeClient {
    DFWebviewActivity activity;
    WeakReference<DFWebviewActivity> weakReference;

    public DFWebviewChromeClient(DFWebviewActivity activity) {
        this.activity = activity;
        weakReference = new WeakReference<>(this.activity);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (weakReference.get() == null) {
            return;
        }
        weakReference.get().setTitle(title);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        if (weakReference.get() == null) {
            return false;
        }
        if (filePathCallback == null) {
            return false;
        }
        if (fileChooserParams == null || fileChooserParams.getAcceptTypes() == null || fileChooserParams.getAcceptTypes().length == 0) {
            return false;
        }
        weakReference.get().onShowFileChooser(webView, filePathCallback, fileChooserParams);
        return true;
    }
}
