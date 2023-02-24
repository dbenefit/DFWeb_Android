package com.dongffl.dfweb.scan.replace;

import androidx.lifecycle.LifecycleOwner;

import com.dongffl.dfweb.scan.callback.ResultCallBack;


public interface IContainer extends LifecycleOwner {
    void startType(int type);
    void forResult(ResultCallBack call);
}