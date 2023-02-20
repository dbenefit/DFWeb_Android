package com.dffl.dflibrary.webivew.handlers;

import androidx.fragment.app.FragmentActivity;

public interface JSBridgeHandler {
    public void doWhat(FragmentActivity activity,JSHandlerCallback callback,String callTag);
}
