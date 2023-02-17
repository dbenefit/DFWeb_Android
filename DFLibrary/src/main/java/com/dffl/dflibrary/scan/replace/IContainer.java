package com.dffl.dflibrary.scan.replace;

import androidx.lifecycle.LifecycleOwner;

import com.dffl.dflibrary.scan.callback.ResultCallBack;


public interface IContainer extends LifecycleOwner {
    void startType(int type);
    void forResult(ResultCallBack call);
}