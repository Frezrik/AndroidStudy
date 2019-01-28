package com.frezrik.core;

import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import com.frezrik.core.api.API;

public class Core {
    private static final LruCache serviceLruCache = new LruCache(20);

    public static <T extends API> T service(@NonNull Class<T> serviceClass) {
        return (T) serviceLruCache.get(serviceClass);
    }

    public static <T extends API> void registerService(@NonNull Class<T> serviceClass, @NonNull T implApi) {
        serviceLruCache.put(serviceClass, implApi);
    }
}
