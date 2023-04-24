package com.dffl.dfscanlib.handler;

import androidx.fragment.app.FragmentActivity;

import com.dffl.dfbaselibrary.bean.JSResponseBuilder;
import com.dffl.dfbaselibrary.bean.JSResponseCode;
import com.dffl.dfbaselibrary.bean.busicess.ScanResultBean;
import com.dffl.dfbaselibrary.handlers.JSHandlerCallback;
import com.dffl.dfbaselibrary.handlers.ScanHandler;
import com.dffl.dfscanlib.CaptureStartup;
import com.dffl.dfscanlib.callback.ResultCallBack;

public class DfScanHandler implements ScanHandler {
    @Override
    public void doWhat(FragmentActivity activity, JSHandlerCallback callback, String param, String callTag) {
        JSResponseBuilder jsResponseBuilder = new JSResponseBuilder().setCallbackTag(callTag);
        new CaptureStartup().from(activity).setType(0).create().forResult(new ResultCallBack() {
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
