//package com.dongffl.dfweb.client;
//
//import android.net.Uri;
//import android.webkit.ValueCallback;
//import android.webkit.WebChromeClient;
//import android.webkit.WebView;
//
//import com.dongffl.dfweb.FileType;
//import com.dongffl.dfweb.OnChromeClientCallBack;
//
//public class DFWebviewChromeClient extends WebChromeClient {
//
//
//    OnChromeClientCallBack onChromeClientCallBack;
//
//    public void setOnFileChooseCallBack(OnChromeClientCallBack onChromeClientCallBack) {
//        this.onChromeClientCallBack = onChromeClientCallBack;
//    }
//
//    public DFWebviewChromeClient() {
//
//    }
//
//    @Override
//    public void onReceivedTitle(WebView view, String title) {
//        super.onReceivedTitle(view, title);
//        if (onChromeClientCallBack != null) {
//            onChromeClientCallBack.onSetTitle(title);
//        }
//    }
//
//    @Override
//    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//        if (filePathCallback == null) {
//            return false;
//        }
//        if (fileChooserParams == null || fileChooserParams.getAcceptTypes() == null || fileChooserParams.getAcceptTypes().length == 0) {
//            return false;
//        }
//        if (onChromeClientCallBack != null) {
//            if (fileChooserParams.getAcceptTypes()[0].contains("video")) {
//                onChromeClientCallBack.onShowFileChooser(FileType.VIDEO, filePathCallback);
//            } else if (fileChooserParams.getAcceptTypes()[0].contains("image")) {
//                onChromeClientCallBack.onShowFileChooser(FileType.IMAGE, filePathCallback);
//            } else if (fileChooserParams.getAcceptTypes()[0].contains("camera")) {
//                onChromeClientCallBack.onShowFileChooser(FileType.CAMERA, filePathCallback);
//            } else {
//                onChromeClientCallBack.onShowFileChooser(FileType.FILE, filePathCallback);
//            }
//        }
//        return true;
//    }
//}
