package com.ming;

import android.app.Application;
import android.content.Context;
import android.os.ServiceManager;
import android.support.multidex.MultiDex;
import com.ming.androidstudy.server.FingerprintManagerBinder;

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

        startFingerprintService();

    }

    private void startFingerprintService() {
        if (ServiceManager.checkService("MingFingerService") == null) {
            ServiceManager.addService("MingFingerService", FingerprintManagerBinder.getInstance());
        }

    }
}
