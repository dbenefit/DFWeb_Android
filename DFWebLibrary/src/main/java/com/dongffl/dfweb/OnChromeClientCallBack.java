package com.dongffl.dfweb;

import android.net.Uri;
import android.webkit.ValueCallback;

public interface OnChromeClientCallBack {
    public void onShowFileChooser(FileType fileType, ValueCallback<Uri[]> filePathCallback);
    public void onSetTitle(String title);
}

