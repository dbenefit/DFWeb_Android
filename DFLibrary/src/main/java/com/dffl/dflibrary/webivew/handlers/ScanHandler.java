package com.dffl.dflibrary.webivew.handlers;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.dffl.dflibrary.DFManager;
import com.dffl.dflibrary.scan.callback.ResultCallBack;
import com.dffl.dflibrary.webivew.bean.JSResponseBuilder;
import com.dffl.dflibrary.webivew.bean.JSResponseCode;
import com.dffl.dflibrary.webivew.bean.busicess.ScanBeanResult;
import com.dffl.dflibrary.webivew.plugin.PluginContainer;
import com.dffl.dflibrary.webivew.plugin.SelfImplPluginCallback;

public class ScanHandler implements JSBridgeHandler {
    @Override
    public void doWhat(FragmentActivity activity, JSHandlerCallback callback, String callTag) {
        JSResponseBuilder jsResponseBuilder = new JSResponseBuilder().setCallbackTag(callTag);
        DFManager.getSingleton().startScan((AppCompatActivity) activity, new ResultCallBack() {
            @Override
            public void onResult(boolean success, boolean cancel, String result) {
                if (result != null) {
                    jsResponseBuilder
                            .setCode(JSResponseCode.SUCCESS.getCode())
                            .setResponse(new ScanBeanResult(result))
                            .setMessage("success");
                    callback.endWord(jsResponseBuilder.buildResponse());
                }else{
                    jsResponseBuilder
                            .setCode(JSResponseCode.FAILED.getCode())
                            .setResponse(new ScanBeanResult(result))
                            .setMessage("failed");
                    callback.endWord(jsResponseBuilder.buildResponse());
                }
            }
        });
    }
}
