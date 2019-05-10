package com.ming;

import android.app.Application;
import android.content.Context;
import android.os.ServiceManager;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import com.ming.androidstudy.server.FingerprintManagerBinder;
import com.ming.framework.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

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

        // logger init
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(3)         // (Optional) How many method line to show. Default 2
                .methodOffset(4)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("zmzm")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });

        startFingerprintService();
    }

    private void startFingerprintService() {
        if (ServiceManager.checkService("MingFingerService") == null) {
            ServiceManager.addService("MingFingerService", FingerprintManagerBinder.getInstance());
        }

    }
}
