package com.dongffl.dfweb.handlers;


import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.dongffl.dfweb.bean.JSResponseBuilder;
import com.dongffl.dfweb.bean.JSResponseCode;
import com.dongffl.dfweb.bean.busicess.ScanResultBean;
import com.dffl.dfbaselibrary.plugin.DFJsBridgePluginCallback;
import com.dffl.dfbaselibrary.plugin.DFPluginContainer;

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