package com.dfweb.choosepicture.matisse.replace;

import androidx.lifecycle.LifecycleOwner;

import com.dfweb.choosepicture.matisse.callback.ResultCallBack;

public interface IContainer extends LifecycleOwner{
    public void forResult(ResultCallBack call);
}