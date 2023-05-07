package com.dffl.dfscanlib.handler;


import androidx.fragment.app.FragmentActivity;

import com.dffl.dfbaselibrary.bean.busicess.ScanResultBean;
import com.dffl.dfbaselibrary.handlers.JSBridgeHandler;
import com.dffl.dfbaselibrary.handlers.JSHandlerCallback;
import com.dffl.dfbaselibrary.plugin.DFJsBridgeCallback;
import com.dffl.dfscanlib.CaptureStartup;
import com.dffl.dfscanlib.callback.ResultCallBack;

public class ScanHandler extends JSBridgeHandler {

    @Override
    public void handle(FragmentActivity activity, String param, DFJsBridgeCallback dfJsBridgeCallback) {
        new CaptureStartup().from(activity).setType(0).create().forResult(new ResultCallBack() {
            @Override
            public void onResult(boolean success, boolean cancel, String result) {
                if (success) {
                    dfJsBridgeCallback.success(new ScanResultBean(result));
                } else if (cancel) {
                    dfJsBridgeCallback.cancel();
                } else {
                    dfJsBridgeCallback.failed();

                }
            }
        });
    }
}