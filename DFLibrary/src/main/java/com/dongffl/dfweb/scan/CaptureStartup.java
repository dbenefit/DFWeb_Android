package com.dongffl.dfweb.scan;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.dongffl.dfweb.scan.replace.ContainerFactory;
import com.dongffl.dfweb.scan.replace.IContainer;

import java.lang.ref.WeakReference;

public class CaptureStartup {

    private WeakReference<IContainer> mContainer;

    private int mType = -1;

    public CaptureStartup from(FragmentActivity activity) {
        this.mContainer = new WeakReference(ContainerFactory.create(activity));
        return this;
    }

    public CaptureStartup from(Fragment fragment) {
        this.mContainer = new WeakReference(ContainerFactory.create(fragment));
        return this;
    }

    public CaptureStartup setType(int type) {
        this.mContainer.get().startType(type);
        return this;
    }

    public IContainer create() {
        return mContainer.get();
    }
}
