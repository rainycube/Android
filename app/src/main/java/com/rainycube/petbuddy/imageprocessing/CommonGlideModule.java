package com.rainycube.petbuddy.imageprocessing;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by SBKim on 2016-06-14.
 */
public class CommonGlideModule implements GlideModule {

    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private final int cacheSize = maxMemory / 8; // 최대 메모리의 12.5%
    private final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10485760 : 10MB External Cache Size

    /*
    DecodeFormat.PREFER_ARGB_8888 : 메모리 용량을 많이 차지하지만 화질이 좋음.
    DecodeFormat.PREFER_RGB_565 : 메모리 용량을 적게 차지하지만 화질이 조금 떨어짐.

    RGB_565는 ARGB_8888에 비해서 화질은 떨어지지만 메모리 용량을 50% 적게 사용.

    .skipMemoryCache(true) : 메모리 캐시를 사용하지 않겠다.
    .diskCacheStrategy(DiskCacheStrategy.NONE) : 디스크 캐시를 사용하지 않겠다.
 */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache", DISK_CACHE_SIZE))
                .setMemoryCache(new LruResourceCache(cacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }

}
