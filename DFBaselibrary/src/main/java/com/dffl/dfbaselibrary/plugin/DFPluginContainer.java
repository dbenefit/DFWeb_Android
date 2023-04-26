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

    private HashMap<String, DFJSBridgePlugin> mSelfPluginMap = new HashMap<>();

    public DFJSBridgePlugin getLocationPlugin() {
        if (mSelfPluginMap.containsKey(JSConfigs.SELF_PLUGIN_LOCATION)) {
            return mSelfPluginMap.get(JSConfigs.SELF_PLUGIN_LOCATION);
        }
        return null;
    }
    public void setScanPlugin(DFJSBridgePlugin scanPlugin){
        mSelfPluginMap.remove(JSConfigs.SELF_PLUGIN_SCANNER);
        mSelfPluginMap.put(JSConfigs.SELF_PLUGIN_SCANNER,scanPlugin);
    }
    public void setLocationPlugin(DFJSBridgePlugin scanPlugin){
        mSelfPluginMap.remove(JSConfigs.SELF_PLUGIN_LOCATION);
        mSelfPluginMap.put(JSConfigs.SELF_PLUGIN_LOCATION,scanPlugin);
    }
    public DFJSBridgePlugin getScanPlugin() {
        if (mSelfPluginMap.containsKey(JSConfigs.SELF_PLUGIN_SCANNER)) {
            return mSelfPluginMap.get(JSConfigs.SELF_PLUGIN_SCANNER);
        }
        return null;
    }
}
