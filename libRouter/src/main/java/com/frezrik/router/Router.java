package com.frezrik.router;

import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

public class Router {

    private static final String TAG = "Router";
    private static boolean debug = true;
    private static final Map<Class<?>, Constructor> BINDINGS = new LinkedHashMap<>();

    public static void setDebug(boolean debug) {
        Router.debug = debug;
    }

    public static void bind(@NonNull Object target) {
        Class<?> targetClass = target.getClass();
        if (debug) Log.d(TAG, "Looking up binding for " + targetClass.getName());
        try {
            Constructor constructor = findBindingConstructorForClass(targetClass);
            constructor.newInstance(target);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static Constructor findBindingConstructorForClass(Class<?> cls) {
        Constructor bindingCtor = BINDINGS.get(cls);
        if (bindingCtor != null || BINDINGS.containsKey(cls)) {
            if (debug) Log.d(TAG, "HIT: Cached in binding map.");
            return bindingCtor;
        }

        String clsName = cls.getName();

        try {
            Class<?> bindingClass = cls.getClassLoader().loadClass(clsName + "$$BindApi");
            bindingCtor = (Constructor) bindingClass.getConstructor(cls);
            if (debug) Log.d(TAG, "HIT: Loaded binding class and Constructor.");
        } catch (ClassNotFoundException e) {
            if (debug) Log.d(TAG, "Not found. Trying superclass " + cls.getSuperclass().getName());
            bindingCtor = findBindingConstructorForClass(cls.getSuperclass());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find binding Constructor for " + clsName, e);
        }
        BINDINGS.put(cls, bindingCtor);
        return bindingCtor;
    }

}
