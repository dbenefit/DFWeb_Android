package com.dongffl.dfweb;

import android.app.Application;


public class Utils {
     private static Application app;
    private Utils(){
    }
    public static Utils getSingleton(){
        return Utils.Inner.instance;
    }
    private static class Inner {
        private static final Utils instance = new Utils();
    }
    public void init(Application application){
        app=application;
    }

    public static Application getApp() {
        return app;
    }

}
