package com.dffl.dflibrary;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public interface OnFileChooseCallBack {
    public void onShowFileChooser( ValueCallback<Uri[]> filePathCallback);
    public void onVideoChooser(ValueCallback<Uri[]> filePathCallback);
    public void onImageChooser(ValueCallback<Uri[]> filePathCallback);

}
