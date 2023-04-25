package com.dffl.dfbaselibrary.handlers;


import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.dffl.dfbaselibrary.bean.JSResponseBuilder;
import com.dffl.dfbaselibrary.bean.JSResponseCode;
import com.dffl.dfbaselibrary.bean.busicess.ScanResultBean;
import com.dffl.dfbaselibrary.plugin.DFPluginContainer;
import com.dffl.dfbaselibrary.plugin.DFJsBridgePluginCallback;

public class ScanHandler implements JSBridgeHandler {

    @Override
    public void handle(FragmentActivity activity, JSHandlerCallback callback, String param, String callTag) {
        if (DFPluginContainer.getSingleton().getScanPlugin() != null) {
            JSResponseBuilder jsResponseBuilder = new JSResponseBuilder().setCallbackTag(callTag);
            DFPluginContainer.getSingleton().getScanPlugin().implJsBridge(activity
                    , new DFJsBridgePluginCallback() {
                        @Override
                        public void success(String result) {
                            jsResponseBuilder
                                    .setCode(JSResponseCode.SUCCESS.getCode())
                                    .setResponse(new ScanResultBean(result))
                                    .setMessage("success");
                            callback.callJsBridgeResult(jsResponseBuilder.buildResponse());
                        }

                        @Override
                        public void failed() {
                            jsResponseBuilder
                                    .setCode(JSResponseCode.FAILED.getCode())
                                    .setResponse(new ScanResultBean("failed"))
                                    .setMessage("failed");
                            callback.callJsBridgeResult(jsResponseBuilder.buildResponse());
                        }

                        @Override
                        public void cancel() {
                            jsResponseBuilder
                                    .setCode(JSResponseCode.FAILED.getCode())
                                    .setResponse(new ScanResultBean("cancel"))
                                    .setMessage("cancel");
                            callback.callJsBridgeResult(jsResponseBuilder.buildResponse());
                        }
                    });
        }else {
            Toast.makeText(activity, "未注册扫码插件", Toast.LENGTH_SHORT).show();
        }
    }
}