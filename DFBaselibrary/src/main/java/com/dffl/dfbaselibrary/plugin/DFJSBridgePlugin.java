package com.dffl.dfbaselibrary.plugin;

import androidx.fragment.app.FragmentActivity;

public abstract class DFJSBridgePlugin {
    public abstract void implJsBridge(FragmentActivity activity, DFJsBridgePluginCallback call  );
}
