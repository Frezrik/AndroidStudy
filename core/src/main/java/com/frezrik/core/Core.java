package com.frezrik.core;

import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

public class Core {
    private static final LruCache serviceLruCache = new LruCache(20);

    public static <T> T service(@NonNull Class<T> serviceClass) {
        return (T) serviceLruCache.get(serviceClass);
    }

    public static <T> void registerService(@NonNull Class<T> serviceClass) {
        serviceLruCache.put(serviceClass, "");
    }

    public static <T> void unRegisterService(@NonNull Class<T> serviceClass) {
        serviceLruCache.remove(serviceClass);
    }
}
