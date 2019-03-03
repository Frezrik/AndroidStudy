package com.frezrik.androidstudy;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;
import com.alibaba.android.arouter.launcher.ARouter;
import com.frezrik.core.api.ConvertUtil;
import com.frezrik.core.api.ImageTools;
import com.frezrik.router.Router;
import com.frezrik.router.annotation.BindApi;
import com.squareup.leakcanary.LeakCanary;

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

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);

        // 在Appliction里面设置我们的异常处理器为UncaughtExceptionHandler处理器
        CrashHandler.getInstance().init();

        startDeviceService();

    }

    @BindApi
    ConvertUtil convertUtil;

    @BindApi
    ImageTools imageTools;

    private void startDeviceService() {
        /*if (DeviceManager.checkService("FrezrikDeviceManager") == null) {
            DeviceManager.addService("FrezrikDeviceManager", new DeviceManagerService(mContext));
        }*/

        Router.bind(this);


        byte[] bytes = convertUtil.randomBytes(5);
        for (byte b : bytes) {
            Log.d("zmzm", "" + b);
        }
    }


}
