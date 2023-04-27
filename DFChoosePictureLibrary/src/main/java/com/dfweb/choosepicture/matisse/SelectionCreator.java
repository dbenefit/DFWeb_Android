package com.dfweb.choosepicture.matisse;

import androidx.annotation.StyleRes;

import com.dfweb.choosepicture.matisse.callback.PathResultCallBack;
import com.dfweb.choosepicture.matisse.callback.ResultCallBack;
import com.dfweb.choosepicture.matisse.callback.UriResultCallBack;
import com.dfweb.choosepicture.matisse.replace.IContainer;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.ImageEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.entity.SelectionSpec;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.util.*;

public class SelectionCreator {
    MatisseStartup startup;
    Set<MimeType > mimeTypes;
    Boolean  mediaTypeExclusive;
    public SelectionCreator( MatisseStartup startup, Set mimeTypes, boolean mediaTypeExclusive) {
        super();
        this.startup = startup;
        this.mimeTypes = mimeTypes;
        this.mediaTypeExclusive = mediaTypeExclusive;
    }
    private   SelectionSpec mSelectionSpec = SelectionSpec.getCleanInstance();


    public final SelectionCreator showSingleMediaType(boolean showSingleMediaType) {
        this.mSelectionSpec.showSingleMediaType = showSingleMediaType;
        return this;
    }

    /**
     * Theme for media selecting Activity.
     *
     *
     * There are two built-in themes:
     * 1. com.zhihu.matisse.R.style.Matisse_Zhihu;
     * 2. com.zhihu.matisse.R.style.Matisse_Dracula
     * you can define a custom theme derived from the above ones or other themes.
     *
     * @param themeId theme resource id. Default value is com.zhihu.matisse.R.style.Matisse_Zhihu.
     * @return [com.zhihu.matisse.SelectionCreator] for fluent API.
     */
    public final SelectionCreator theme(@StyleRes int themeId) {
        this.mSelectionSpec.themeId = themeId;
        return this;
    }

    /**
     * Show a auto-increased number or a check mark when user select media.
     *
     * @param countable true for a auto-increased number from 1, false for a check mark. Default
     * value is false.
     * @return [com.zhihu.matisse.SelectionCreator] for fluent API.
     */
    public final SelectionCreator countable(boolean countable) {
        this.mSelectionSpec.countable = countable;
        return this;
    }


    /**
     * Maximum selectable count.
     *
     * @param maxSelectable Maximum selectable count. Default value is 1.
     * @return [com.zhihu.matisse.SelectionCreator] for fluent API.
     */
    public final SelectionCreator maxSelectable(int maxSelectable) {
        boolean var2 = maxSelectable >= 1;
        String var4;
        if (!var2) {
            var4 = "maxSelectable must be greater than or equal to one";
            throw new IllegalArgumentException(var4.toString());
        } else {
            var2 = this.mSelectionSpec.maxImageSelectable <= 0 && this.mSelectionSpec.maxVideoSelectable <= 0;
            if (!var2) {
                var4 = "already set maxImageSelectable and maxVideoSelectable";
                throw new IllegalStateException(var4.toString());
            } else {
                this.mSelectionSpec.maxSelectable = maxSelectable;
                return this;
            }
        }
    }

    /**
     * Only useful when [SelectionSpec.mediaTypeExclusive] set true and you want to set different maximum
     * selectable files for image and video media types.
     *
     * @param maxImageSelectable Maximum selectable count for image.
     * @param maxVideoSelectable Maximum selectable count for video.
     * @return [com.zhihu.matisse.SelectionCreator] for fluent API.
     */
    public final SelectionCreator maxSelectablePerMediaType(int maxImageSelectable, int maxVideoSelectable) {
        boolean var3 = maxImageSelectable >= 1 && maxVideoSelectable >= 1;
        if (!var3) {
            String var5 = "max selectable must be greater than or equal to one";
            throw new IllegalArgumentException(var5.toString());
        } else {
            this.mSelectionSpec.maxSelectable = -1;
            this.mSelectionSpec.maxImageSelectable = maxImageSelectable;
            this.mSelectionSpec.maxVideoSelectable = maxVideoSelectable;
            return this;
        }
    }

    /**
     * Add filter to filter each selecting item.
     *
     * @param filter [Filter]
     * @return [com.zhihu.matisse.SelectionCreator] for fluent API.
     */
    public final SelectionCreator addFilter( Filter filter) {
        if (this.mSelectionSpec.filters == null) {
            this.mSelectionSpec.filters = (List)(new ArrayList());
        }

        this.mSelectionSpec.filters.add(filter);
        return this;
    }

    /**
     * Determines whether the photo capturing is enabled or not on the media grid view.
     *
     *
     * If this value is set true, photo capturing entry will appear only on All Media's page.
     *
     * @param enable Whether to enable capturing or not. Default value is false;
     * @return [com.zhihu.matisse.SelectionCreator] for fluent API.
     */
    public final SelectionCreator capture(boolean enable) {
        this.mSelectionSpec.capture = enable;
        return this;
    }

    /**
     * Show a original photo check options.Let users decide whether use original photo after select
     *
     * @param enable Whether to enable original photo or not
     * @return [com.zhihu.matisse.SelectionCreator] for fluent API.
     */
    public final SelectionCreator originalEnable(boolean enable) {
        this.mSelectionSpec.originalable = enable;
        return this;
    }

    /**
     * Determines Whether to hide top and bottom toolbar in PreView mode ,when user tap the picture
     *
     * @param enable
     * @return [com.zhihu.matisse.SelectionCreator] for fluent API.
     */
    public final SelectionCreator autoHideToolbarOnSingleTap(boolean enable) {
        this.mSelectionSpec.autoHideToobar = enable;
        return this;
    }

    /**
     * Maximum original size,the unit is MB. Only useful when {link@originalEnable} set true
     *
     * @param size Maximum original size. Default value is Integer.MAX_VALUE
     * @return [com.zhihu.matisse.SelectionCreator] for fluent API.
     */
    public final SelectionCreator maxOriginalSize(int size) {
        this.mSelectionSpec.originalMaxSize = size;
        return this;
    }

    /**
     * Capture strategy provided for the location to save photos including internal and external
     * storage and also a authority for [androidx.core.content.FileProvider].
     *
     * @param captureStrategy [CaptureStrategy], needed only when capturing is enabled.
     * @return [com.zhihu.matisse.SelectionCreator] for fluent API.
     */
    public final SelectionCreator captureStrategy( CaptureStrategy captureStrategy) {
        this.mSelectionSpec.captureStrategy = captureStrategy;
        return this;
    }

    public final SelectionCreator restrictOrientation(int orientation) {
        this.mSelectionSpec.orientation = orientation;
        return this;
    }

    /**
     * Set a fixed span count for the media grid. Same for different screen orientations.
     *
     *
     * This will be ignored when [.gridExpectedSize] is set.
     *
     * @param spanCount Requested span count.
     * @return [com.zhihu.matisse.SelectionCreator] for fluent API.
     */
    public final SelectionCreator spanCount(int spanCount) {
        if (!(spanCount >= 1)) {
            String var4 = "spanCount cannot be less than 1";
            throw new IllegalArgumentException(var4.toString());
        } else {
            this.mSelectionSpec.spanCount = spanCount;
            return this;
        }
    }

    /**
     * Set expected size for media grid to adapt to different screen sizes. This won't necessarily
     * be applied cause the media grid should fill the view container. The measured media grid's
     * size will be as close to this value as possible.
     *
     * @param size Expected media grid size in pixel.
     * @return [com.zhihu.matisse.SelectionCreator] for fluent API.
     */
    public final SelectionCreator gridExpectedSize(int size) {
        this.mSelectionSpec.gridExpectedSize = size;
        return this;
    }

    /**
     * Photo thumbnail's scale compared to the View's size. It should be a float value in (0.0,
     * 1.0].
     *
     * @param scale Thumbnail's scale in (0.0, 1.0]. Default value is 0.5.
     * @return [com.zhihu.matisse.SelectionCreator] for fluent API.
     */
    public final SelectionCreator thumbnailScale(float scale) {
        boolean var2 = !(scale <= 0.0F) && !(scale > 1.0F);
        if (!var2) {
            String var4 = "Thumbnail scale must be between (0.0, 1.0]";
            throw new IllegalArgumentException(var4.toString());
        } else {
            this.mSelectionSpec.thumbnailScale = scale;
            return this;
        }
    }

    /**
     * Provide an image engine.
     *
     *
     * There are two built-in image engines:
     * 1. [com.zhihu.matisse.engine.impl.GlideEngine]
     * 2. [com.zhihu.matisse.engine.impl.PicassoEngine]
     * And you can implement your own image engine.
     *
     * @param imageEngine [ImageEngine]
     * @return [com.zhihu.matisse.SelectionCreator] for fluent API.
     */
    public final SelectionCreator imageEngine( ImageEngine imageEngine) {
        this.mSelectionSpec.imageEngine = imageEngine;
        return this;
    }

    public final SelectionCreator setOnSelectedListener( OnSelectedListener listener) {
        this.mSelectionSpec.onSelectedListener = listener;
        return this;
    }

    /**
     * Set listener for callback immediately when user check or uncheck original.
     *
     * @param listener [OnSelectedListener]
     * @return [com.zhihu.matisse.SelectionCreator] for fluent API.
     */
    public final SelectionCreator setOnCheckedListener( OnCheckedListener listener) {
        this.mSelectionSpec.onCheckedListener = listener;
        return this;
    }

    public final SelectionCreator showPreview(boolean showPreview) {
        this.mSelectionSpec.showPreview = showPreview;
        return this;
    }

    public final void forPathResult( PathResultCallBack call) {
        IContainer var10000 = this.startup.getStartup();
        if (var10000 != null) {
            var10000.forResult((ResultCallBack)call);
        }

    }

    public final void forUriResult( UriResultCallBack call) {
        IContainer var10000 = this.startup.getStartup();
        if (var10000 != null) {
            var10000.forResult((ResultCallBack)call);
        }

    }
}