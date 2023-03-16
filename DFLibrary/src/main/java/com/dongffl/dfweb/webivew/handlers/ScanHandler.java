package com.dongffl.dfweb.webivew.handlers;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.dongffl.dfweb.DFManager;
import com.dongffl.dfweb.scan.callback.ResultCallBack;
import com.dongffl.dfweb.webivew.bean.JSResponseBuilder;
import com.dongffl.dfweb.webivew.bean.JSResponseCode;
import com.dongffl.dfweb.webivew.bean.busicess.ScanResultBean;

public class ScanHandler implements JSBridgeHandler {
    @Override
    public void doWhat(FragmentActivity activity, JSHandlerCallback callback, String param, String callTag) {
        JSResponseBuilder jsResponseBuilder = new JSResponseBuilder().setCallbackTag(callTag);
        DFManager.getSingleton().openScan((FragmentActivity) activity, new ResultCallBack() {
            @Override
            public void onResult(boolean success, boolean cancel, String result) {
                if (result != null) {
                    jsResponseBuilder
                            .setCode(JSResponseCode.SUCCESS.getCode())
                            .setResponse(new ScanResultBean(result))
                            .setMessage("success");
                    callback.endWord(jsResponseBuilder.buildResponse());
                }else{
                    jsResponseBuilder
                            .setCode(JSResponseCode.FAILED.getCode())
                            .setResponse(new ScanResultBean("failed"))
                            .setMessage("failed");
                    callback.endWord(jsResponseBuilder.buildResponse());
                }
            }
        });
    }
}
