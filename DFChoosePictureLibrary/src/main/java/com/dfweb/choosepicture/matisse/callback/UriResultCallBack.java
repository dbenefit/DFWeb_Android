package com.dfweb.choosepicture.matisse.callback;

import android.net.Uri;

import java.util.List;

public interface UriResultCallBack extends ResultCallBack {
    public void onUriResult(List<Uri> data);
}