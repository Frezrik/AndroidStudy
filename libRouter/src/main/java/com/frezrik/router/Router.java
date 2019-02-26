package com.frezrik.router;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

public class Router {

    private static final String TAG = "Router";
    private static boolean debug = false;
    static final Map<Class<?>, Constructor> BINDINGS = new LinkedHashMap<>();
    static final LruCache serviceLruCache = new LruCache(50);

    public static void setDebug(boolean debug) {
        Router.debug = debug;
    }

    public static void bind(@NonNull Object obj) {
        String classFullName = obj.getClass().getName() + "$$BindApi";
        try {
            Constructor constructor = findBindingConstructorForClass(classFullName.getClass());
            constructor.newInstance(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Constructor findBindingConstructorForClass(Class<?> bindingClass) {
        Constructor bindingCtor = BINDINGS.get(bindingClass);
        if (bindingCtor != null || BINDINGS.containsKey(bindingClass)) {
            if (debug) Log.d(TAG, "HIT: Cached in binding map.");
            return bindingCtor;
        }

        try {
            bindingCtor = bindingClass.getConstructor(bindingClass);
            if (debug) Log.d(TAG, "HIT: Loaded binding class and constructor.");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find binding constructor for " + bindingClass.getName(), e);
        }
        BINDINGS.put(bindingClass, bindingCtor);
        return bindingCtor;
    }

    public static <T> T service(@NonNull Class<T> serviceClass) {
        return (T) serviceLruCache.get(serviceClass);
    }

    public static <T> void registerService(@NonNull Class<T> serviceClass, @NonNull T implApi) {
        serviceLruCache.put(serviceClass, implApi);
    }

    public static <T> void unbind(@NonNull Class<T> serviceClass) {
        if(serviceLruCache != null)
            serviceLruCache.remove(serviceClass);
    }
}
