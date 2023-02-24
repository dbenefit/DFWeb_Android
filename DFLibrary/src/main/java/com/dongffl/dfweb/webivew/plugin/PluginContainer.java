package com.dongffl.dfweb.webivew.plugin;

import com.dongffl.dfweb.webivew.config.JSConfigs;

import java.util.HashMap;

public class PluginContainer {
    private PluginContainer() {
    }

    public static PluginContainer getSingleton() {
        return PluginContainer.Inner.instance;
    }

    private static class Inner {
        private static final PluginContainer instance = new PluginContainer();
    }

    private HashMap<String, SelfImplPlugin> mSelfPluginMap = new HashMap<>();

    public SelfImplPlugin getLocationPlugin() {
        if (mSelfPluginMap.containsKey(JSConfigs.SELF_PLUGIN_LOCATION)) {
            return mSelfPluginMap.get(JSConfigs.SELF_PLUGIN_LOCATION);
        }
        return null;
    }
    public SelfImplPlugin getScanPlugin() {
        if (mSelfPluginMap.containsKey(JSConfigs.SELF_PLUGIN_SCANNER)) {
            return mSelfPluginMap.get(JSConfigs.SELF_PLUGIN_SCANNER);
        }
        return null;
    }
}
