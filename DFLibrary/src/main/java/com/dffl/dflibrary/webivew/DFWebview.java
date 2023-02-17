package com.dffl.dflibrary.webivew;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DFWebview extends WebView {

    public DFWebview(@NonNull Context context) {
        super(context);
    }

    public DFWebview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DFWebview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DFWebview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DFWebview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    public Context getFixedContext(Context context) {
        if (Build.VERSION.SDK_INT == 21 || Build.VERSION.SDK_INT == 22) {
            return context.createConfigurationContext(
                    new Configuration()
            );
        } else {
            return context;
        }
    }
}
