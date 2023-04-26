package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.tencent.libavif.AvifSequenceDrawable;
import com.tencent.qcloud.image.avif.glide.avif.ByteBufferAvifDecoder;
import com.tencent.qcloud.image.avif.glide.avif.ByteBufferAvifSequenceDecoder;
import com.tencent.qcloud.image.avif.glide.avif.StreamAvifDecoder;
import com.tencent.qcloud.image.avif.glide.avif.StreamAvifSequenceDecoder;

import java.io.InputStream;
import java.nio.ByteBuffer;

// 注册自定义 GlideModule
 // 开发者应该创建此类注册相关解码器<br>
 // 类库开发者可以继承 LibraryGlideModule 创建类似的注册类
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, Registry registry) {
        /*------------------解码器 开始-------------------------*/
        //注册 AVIF 静态图片解码器
        registry.prepend(Registry.BUCKET_BITMAP, InputStream.class, Bitmap.class, new StreamAvifDecoder(glide.getBitmapPool(), glide.getArrayPool()));
        registry.prepend(Registry.BUCKET_BITMAP, ByteBuffer.class, Bitmap.class, new ByteBufferAvifDecoder(glide.getBitmapPool()));
        //注册 AVIF 动图解码器
        registry.prepend(InputStream.class, AvifSequenceDrawable.class, new StreamAvifSequenceDecoder(glide.getBitmapPool(), glide.getArrayPool()));
        registry.prepend(ByteBuffer.class, AvifSequenceDrawable.class, new ByteBufferAvifSequenceDecoder(glide.getBitmapPool()));
        /*------------------解码器 结束-------------------------*/
    }
}
