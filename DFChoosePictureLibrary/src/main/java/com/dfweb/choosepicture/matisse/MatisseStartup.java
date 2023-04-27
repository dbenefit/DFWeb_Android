package com.dfweb.choosepicture.matisse;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.dfweb.choosepicture.matisse.replace.ContainerFactory;
import com.dfweb.choosepicture.matisse.replace.IContainer;
import com.zhihu.matisse.MimeType;
import java.lang.ref.WeakReference;
import java.util.Set;

public class MatisseStartup {
    private    WeakReference<IContainer> mContainer;
    public MatisseStartup  from( FragmentActivity activity) {
        this.mContainer = new WeakReference(ContainerFactory.getSingleton().create(activity));
        return this;
    }

    public MatisseStartup  from( Fragment  fragment) {
        this.mContainer = new WeakReference(ContainerFactory.getSingleton().create(fragment));
        return this;
    }

    public SelectionCreator choose( Set<MimeType> mimeTypes)  {
        return choose(this, mimeTypes, true);
    }

    private SelectionCreator choose(
          MatisseStartup startup,
         Set<MimeType> mimeTypes,
         Boolean mediaTypeExclusive
    ) {
        return new SelectionCreator(startup, mimeTypes, mediaTypeExclusive);
    }

    public IContainer getStartup()    {
        return mContainer.get();
    }

}