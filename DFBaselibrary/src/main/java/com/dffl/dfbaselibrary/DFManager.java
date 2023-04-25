package com.dffl.dfbaselibrary;
public class DFManager {
    private String userAgentString = "";

    private DFManager() {
    }


    public static DFManager getSingleton() {
        return Inner.instance;
    }


    private static class Inner {
        private static final DFManager instance = new DFManager();
    }


}
