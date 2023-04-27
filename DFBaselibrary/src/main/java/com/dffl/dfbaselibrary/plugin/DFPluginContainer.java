package com.dffl.dfbaselibrary.plugin;


import com.dffl.dfbaselibrary.JSConfigs;

import java.util.HashMap;

public class DFPluginContainer {
    private DFPluginContainer() {
    }

    public static DFPluginContainer getSingleton() {
        return Inner.instance;
    }

    private static class Inner {
        private static final DFPluginContainer instance = new DFPluginContainer();
    }

    private HashMap<Enum, DFJSBridgePlugin> mDFPluginMap = new HashMap<>();

    public void setDFPlugin(DFPluginStyle style, DFJSBridgePlugin dfPlugin)  {
        if (dfPlugin==null){
             return;
        }
        mDFPluginMap.put(style, dfPlugin);
    }

    public DFJSBridgePlugin getDFPlugin(DFPluginStyle style) {
        if (mDFPluginMap.containsKey(style)) {
            return mDFPluginMap.get(style);
        }
        return null;
    }


}
