package com.dongffl.dfweb.handlers;


import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.dffl.dfbaselibrary.plugin.DFJsBridgePluginCallback;
import com.dffl.dfbaselibrary.plugin.DFPluginContainer;
import com.dffl.dfbaselibrary.plugin.DFPluginStyle;
import com.dongffl.dfweb.bean.JSResponseBuilder;
import com.dongffl.dfweb.bean.JSResponseCode;
import com.dongffl.dfweb.bean.busicess.ScanResultBean;

public class OpenNewWebHandler implements JSBridgeHandler {

    @Override
    public void handle(FragmentActivity activity, JSHandlerCallback callback, String param, String callTag) {
        if (DFPluginContainer.getSingleton().getDFPlugin(DFPluginStyle.OPEN_WEBVIEW) != null) {
            DFPluginContainer.getSingleton().getDFPlugin(DFPluginStyle.OPEN_WEBVIEW).implJsBridge(activity
                    , new DFJsBridgePluginCallback() {
                        @Override
                        public void success(Object result) {

                        }

                        @Override
                        public void failed() {

                        }

                        @Override
                        public void cancel() {

                        }
                    });
        }else {
            Toast.makeText(activity, "未注册新开webview插件", Toast.LENGTH_SHORT).show();
        }
    }
}