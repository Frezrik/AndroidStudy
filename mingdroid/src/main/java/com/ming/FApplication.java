package com.ming;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class FApplication extends Application {
    private Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        // 在Appliction里面设置我们的异常处理器为UncaughtExceptionHandler处理器
        CrashHandler.getInstance().init();

        startDeviceService();

    }

    private void startDeviceService() {
        /*if (ServiceManager.checkService("FrezrikDeviceManager") == null) {
            ServiceManager.addService("FrezrikDeviceManager", new DeviceManagerService(mContext));
        }*/

    }
}
