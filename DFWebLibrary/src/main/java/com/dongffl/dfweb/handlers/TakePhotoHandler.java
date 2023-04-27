package com.dongffl.dfweb.handlers;


import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.dffl.dfbaselibrary.plugin.DFJsBridgePluginCallback;
import com.dffl.dfbaselibrary.plugin.DFPluginContainer;
import com.dffl.dfbaselibrary.plugin.DFPluginStyle;
import com.dongffl.dfweb.bean.JSResponseBuilder;
import com.dongffl.dfweb.bean.JSResponseCode;
import com.dongffl.dfweb.bean.TakePhotoResponse;
import com.dongffl.dfweb.bean.busicess.ScanResultBean;

public class TakePhotoHandler implements JSBridgeHandler {

    @Override
    public void handle(FragmentActivity activity, JSHandlerCallback callback, String param, String callTag) {
        if (DFPluginContainer.getSingleton().getDFPlugin(DFPluginStyle.TAKE_PHONE) != null) {
            JSResponseBuilder jsResponseBuilder = new JSResponseBuilder().setCallbackTag(callTag);
            DFPluginContainer.getSingleton().getDFPlugin(DFPluginStyle.TAKE_PHONE).implJsBridge(activity
                    , new DFJsBridgePluginCallback() {
                        @Override
                        public void success(Object result) {
                            if (result instanceof String)
                            jsResponseBuilder
                                    .setCode(JSResponseCode.SUCCESS.getCode())
                                    .setResponse(new TakePhotoResponse((String)result))
                                    .setMessage("success");
                            callback.callJsBridgeResult(jsResponseBuilder.buildResponse());
                        }

                        @Override
                        public void failed() {
                            jsResponseBuilder
                                    .setCode(JSResponseCode.FAILED.getCode())
                                    .setResponse(new TakePhotoResponse("failed"))
                                    .setMessage("failed");
                            callback.callJsBridgeResult(jsResponseBuilder.buildResponse());
                        }

                        @Override
                        public void cancel() {
                            jsResponseBuilder
                                    .setCode(JSResponseCode.CANCEL.getCode())
                                    .setResponse(new TakePhotoResponse("cancel"))
                                    .setMessage("cancel");
                            callback.callJsBridgeResult(jsResponseBuilder.buildResponse());
                        }
                    });
        }else {
            Toast.makeText(activity, "未注册拍照插件", Toast.LENGTH_SHORT).show();
        }
    }
}