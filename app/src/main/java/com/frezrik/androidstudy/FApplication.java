package com.frezrik.androidstudy;

import android.app.Application;
import android.content.Context;
import com.frezrik.androidstudy.service.DeviceManagerService;
import com.frezrik.framework.utils.ServiceManager;

public class FApplication extends Application {
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        // 在Appliction里面设置我们的异常处理器为UncaughtExceptionHandler处理器
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(getApplicationContext());

        startDeviceService();

    }

    private void startDeviceService() {
        if (ServiceManager.checkService("FrezrikDeviceManager") == null) {
            //ServiceManager.addService("FrezrikDeviceManager", new DeviceManagerService(mContext));
        }
    }
}
