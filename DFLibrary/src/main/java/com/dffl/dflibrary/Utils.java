package com.dffl.dflibrary;

import android.app.Application;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;


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
