package com.dongffl.dfweb.handlers;


import androidx.fragment.app.FragmentActivity;

import com.dffl.dfbaselibrary.plugin.DFJsBridgePluginCallback;
import com.dffl.dfbaselibrary.plugin.DFPluginContainer;
import com.dffl.dfbaselibrary.plugin.DFPluginStyle;
import com.dongffl.dfweb.bean.ChoosePicResponse;
import com.dongffl.dfweb.bean.JSResponseBuilder;
import com.dongffl.dfweb.bean.JSResponseCode;
import com.dongffl.dfweb.bean.busicess.ScanResultBean;

public class ChoosePicHandler implements JSBridgeHandler {

    @Override
    public void handle(FragmentActivity activity, JSHandlerCallback callback, String param, String callTag) {
        JSResponseBuilder jsResponseBuilder = new JSResponseBuilder().setCallbackTag(callTag);
        if (DFPluginContainer.getSingleton().getDFPlugin(DFPluginStyle.CHOOSE_PICTURE) != null) {
            DFPluginContainer.getSingleton().getDFPlugin(DFPluginStyle.CHOOSE_PICTURE).implJsBridge(activity
                    , new DFJsBridgePluginCallback() {
                        @Override
                        public void success(Object result) {
                            if (result instanceof ChoosePicResponse) {
                                jsResponseBuilder
                                        .setCode(JSResponseCode.SUCCESS.getCode())
                                        .setResponse((ChoosePicResponse) result)
                                        .setMessage("success");
                                callback.callJsBridgeResult(jsResponseBuilder.buildResponse());
                            } else {
                                jsResponseBuilder
                                        .setCode(JSResponseCode.FAILED.getCode())
                                        .setResponse(new ScanResultBean("failed"))
                                        .setMessage("failed");
                                callback.callJsBridgeResult(jsResponseBuilder.buildResponse());
                            }
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
                                    .setCode(JSResponseCode.CANCEL.getCode())
                                    .setResponse(new ScanResultBean("cancel"))
                                    .setMessage("cancel");
                            callback.callJsBridgeResult(jsResponseBuilder.buildResponse());
                        }
                    });
        }
    }
}