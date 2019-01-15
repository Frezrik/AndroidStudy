package com.frezrik.androidstudy;

import android.app.Application;
import android.content.Context;
import com.squareup.leakcanary.LeakCanary;

public class FApplication extends Application {
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        mContext = this;

        // 在Appliction里面设置我们的异常处理器为UncaughtExceptionHandler处理器
        CrashHandler.getInstance().init();

        startDeviceService();

    }

    private void startDeviceService() {
        //if (ServiceManager.checkService("FrezrikDeviceManager") == null) {
            //ServiceManager.addService("FrezrikDeviceManager", new DeviceManagerService(mContext));
        //}

    }
}
