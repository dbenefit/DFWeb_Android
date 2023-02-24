package com.dongffl.dfweb;

import android.net.Uri;
import android.webkit.ValueCallback;

public interface OnFileChooseCallBack {
    public void onShowFileChooser( ValueCallback<Uri[]> filePathCallback);
    public void onVideoChooser(ValueCallback<Uri[]> filePathCallback);
    public void onImageChooser(ValueCallback<Uri[]> filePathCallback);

}
