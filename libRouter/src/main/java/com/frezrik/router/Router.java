package com.frezrik.router;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

public class Router {

    private static final String TAG = "Router";
    private static boolean debug = true;
    static final Map<Class<?>, Constructor<? extends Unbinder>> BINDINGS = new LinkedHashMap<>();
    static final LruCache serviceLruCache = new LruCache(50);

    public static void setDebug(boolean debug) {
        Router.debug = debug;
    }

    public static void bind(@NonNull Object target) {
        Class<?> targetClass = target.getClass();
        if (debug) Log.d(TAG, "Looking up binding for " + targetClass.getName());
        try {
            Constructor<? extends Unbinder> constructor = findBindingConstructorForClass(targetClass);
            constructor.newInstance(target);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static Constructor<? extends Unbinder> findBindingConstructorForClass(Class<?> cls) {
        Constructor<? extends Unbinder> bindingCtor = BINDINGS.get(cls);
        if (bindingCtor != null || BINDINGS.containsKey(cls)) {
            if (debug) Log.d(TAG, "HIT: Cached in binding map.");
            return bindingCtor;
        }

        String clsName = cls.getName();

        try {
            Class<?> bindingClass = cls.getClassLoader().loadClass(clsName + "$$BindApi");
            bindingCtor = (Constructor<? extends Unbinder>) bindingClass.getConstructor(cls);
            if (debug) Log.d(TAG, "HIT: Loaded binding class and Constructor<? extends Unbinder>.");
        } catch (ClassNotFoundException e) {
            if (debug) Log.d(TAG, "Not found. Trying superclass " + cls.getSuperclass().getName());
            bindingCtor = findBindingConstructorForClass(cls.getSuperclass());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find binding Constructor<? extends Unbinder> for " + clsName, e);
        }
        BINDINGS.put(cls, bindingCtor);
        return bindingCtor;
    }

    public static <T> T service(@NonNull Class<T> serviceClass) {
        return (T) serviceLruCache.get(serviceClass);
    }

    public static <T> void registerService(@NonNull Class<T> serviceClass, @NonNull T implApi) {
        serviceLruCache.put(serviceClass, implApi);
    }

    public static <T> void unbind(@NonNull Class<T> serviceClass) {
        if (serviceLruCache != null)
            serviceLruCache.remove(serviceClass);
    }
}
