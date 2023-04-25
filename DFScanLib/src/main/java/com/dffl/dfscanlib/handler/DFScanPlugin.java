package com.dffl.dfscanlib.handler;

import androidx.fragment.app.FragmentActivity;

import com.dffl.dfbaselibrary.plugin.DFJSBridgePlugin;
import com.dffl.dfbaselibrary.plugin.DFJsBridgePluginCallback;
import com.dffl.dfscanlib.CaptureStartup;
import com.dffl.dfscanlib.callback.ResultCallBack;

public class DFScanPlugin extends DFJSBridgePlugin {
    @Override
    public void implJsBridge(FragmentActivity activity, DFJsBridgePluginCallback call) {
        new CaptureStartup().from(activity).setType(0).create().forResult(new ResultCallBack() {
            @Override
            public void onResult(boolean success, boolean cancel, String result) {
                if (success) {
                    call.success(result);
                } else if (cancel) {
                    call.cancel();
                } else {
                    call.failed();
                }
            }
        });
    }
}
