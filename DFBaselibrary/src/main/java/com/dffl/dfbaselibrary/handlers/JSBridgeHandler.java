package com.dffl.dfbaselibrary.handlers;

import androidx.fragment.app.FragmentActivity;

public interface JSBridgeHandler {
    public void handle(FragmentActivity activity, JSHandlerCallback callback, String param, String callTag);
}
