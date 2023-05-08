package com.dffl.dfbaselibrary.handlers;


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

    private HashMap<Enum, DFJSBridgeHandler> mDFPluginMap = new HashMap<>();

    public void setDFHandler(DFHandlerStyle style, DFJSBridgeHandler dfHandler) {
        if (dfHandler == null) {
            return;
        }
        mDFPluginMap.put(style, dfHandler);
    }

    public DFJSBridgeHandler getDFHandler(DFHandlerStyle style) {
        if (mDFPluginMap.containsKey(style)) {
            return mDFPluginMap.get(style);
        }
        return null;
    }


}
