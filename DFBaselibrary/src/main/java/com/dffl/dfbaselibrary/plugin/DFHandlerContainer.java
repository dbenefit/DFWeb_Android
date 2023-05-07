package com.dffl.dfbaselibrary.plugin;


import com.dffl.dfbaselibrary.handlers.JSBridgeHandler;

import java.util.HashMap;

public class DFHandlerContainer {
    private DFHandlerContainer() {
    }

    public static DFHandlerContainer getSingleton() {
        return Inner.instance;
    }

    private static class Inner {
        private static final DFHandlerContainer instance = new DFHandlerContainer();
    }

    private HashMap<Enum, JSBridgeHandler> mDFPluginMap = new HashMap<>();

    public void setDFHandler(DFHandlerStyle style, JSBridgeHandler dfHandler) {
        if (dfHandler == null) {
            return;
        }
        mDFPluginMap.put(style, dfHandler);
    }

    public JSBridgeHandler getDFHandler(DFHandlerStyle style) {
        if (mDFPluginMap.containsKey(style)) {
            return mDFPluginMap.get(style);
        }
        return null;
    }


}
