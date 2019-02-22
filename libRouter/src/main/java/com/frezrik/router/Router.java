package com.frezrik.router;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

public class Router {

    private static final String TAG = "Router";
    private static boolean debug = false;
    static final Map<Class<?>, Constructor> BINDINGS = new LinkedHashMap<>();

    public static void setDebug(boolean debug) {
        Router.debug = debug;
    }

    public static void bind(Object obj) {
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

}
