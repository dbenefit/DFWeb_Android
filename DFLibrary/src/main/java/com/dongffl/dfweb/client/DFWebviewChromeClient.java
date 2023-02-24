package com.dongffl.dfweb.client;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.dongffl.dfweb.OnFileChooseCallBack;

public class DFWebviewChromeClient extends WebChromeClient {


    OnFileChooseCallBack onFileChooseCallBack;

    public void setOnFileChooseCallBack(OnFileChooseCallBack onFileChooseCallBack) {
        this.onFileChooseCallBack = onFileChooseCallBack;
    }

    public DFWebviewChromeClient() {

    }

//    @Override
//    public void onReceivedTitle(WebView view, String title) {
//        super.onReceivedTitle(view, title);
//        if (weakReference.get() == null) {
//            return;
//        }
//        weakReference.get().setTitle(title);
//    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {


        if (filePathCallback == null) {
            return false;
        }
        if (fileChooserParams == null || fileChooserParams.getAcceptTypes() == null || fileChooserParams.getAcceptTypes().length == 0) {
            return false;
        }
        if (onFileChooseCallBack != null) {
            if (fileChooserParams.getAcceptTypes()[0].contains("video")) {
                onFileChooseCallBack.onVideoChooser(filePathCallback);
            } else if (fileChooserParams.getAcceptTypes()[0].contains("image")) {
                onFileChooseCallBack.onImageChooser(filePathCallback);
            } else {
                onFileChooseCallBack.onShowFileChooser(filePathCallback);
            }
        }
        return true;
    }
}
