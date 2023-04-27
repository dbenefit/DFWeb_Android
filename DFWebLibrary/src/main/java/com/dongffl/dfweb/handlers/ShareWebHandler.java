package com.dongffl.dfweb.handlers;


import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.dffl.dfbaselibrary.plugin.DFJsBridgePluginCallback;
import com.dffl.dfbaselibrary.plugin.DFPluginContainer;
import com.dffl.dfbaselibrary.plugin.DFPluginStyle;
import com.dongffl.dfweb.bean.JSResponseBuilder;
import com.dongffl.dfweb.bean.JSResponseCode;
import com.dongffl.dfweb.bean.busicess.ScanResultBean;

public class ShareWebHandler implements JSBridgeHandler {

    @Override
    public void handle(FragmentActivity activity, JSHandlerCallback callback, String param, String callTag) {
        if (DFPluginContainer.getSingleton().getDFPlugin(DFPluginStyle.SHARE) != null) {
            JSResponseBuilder jsResponseBuilder = new JSResponseBuilder().setCallbackTag(callTag);
            DFPluginContainer.getSingleton().getDFPlugin(DFPluginStyle.SHARE).implJsBridge(activity
                    , new DFJsBridgePluginCallback() {
                        @Override
                        public void success(Object result) {
                            jsResponseBuilder
                                    .setCode(JSResponseCode.SUCCESS.getCode())
                                    .setMessage("success");
                            callback.callJsBridgeResult(jsResponseBuilder.buildResponse());
                        }

                        @Override
                        public void failed() {
                            jsResponseBuilder
                                    .setCode(JSResponseCode.FAILED.getCode())
                                    .setMessage("failed");
                            callback.callJsBridgeResult(jsResponseBuilder.buildResponse());
                        }

                        @Override
                        public void cancel() {
                            jsResponseBuilder
                                    .setCode(JSResponseCode.CANCEL.getCode())
                                    .setMessage("cancel");
                            callback.callJsBridgeResult(jsResponseBuilder.buildResponse());
                        }
                    });
        } else {
            Toast.makeText(activity, "未注册分享插件", Toast.LENGTH_SHORT).show();

        }
    }
}