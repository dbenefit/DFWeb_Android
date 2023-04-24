package com.dffl.dfscanlib.replace;

import androidx.lifecycle.LifecycleOwner;

import com.dffl.dfscanlib.callback.ResultCallBack;


public interface IContainer extends LifecycleOwner {
    void startType(int type);
    void forResult(ResultCallBack call);
}