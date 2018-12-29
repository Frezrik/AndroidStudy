package com.frezrik.framework.utils;

import android.os.IBinder;

public class ServiceManager {
    public static IBinder checkService(String name) {
        return android.os.ServiceManager.checkService(name);
    }

    public static void addService(String name, IBinder service) {
        android.os.ServiceManager.addService(name, service);
    }
}
