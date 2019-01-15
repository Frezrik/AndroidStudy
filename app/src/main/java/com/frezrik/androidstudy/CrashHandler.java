package com.frezrik.androidstudy;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.util.Log;

public class CrashHandler implements UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    volatile private static CrashHandler sCrashHandler;

    private CrashHandler() {
    }

    public synchronized static CrashHandler getInstance() {
        if (sCrashHandler == null) {
            synchronized (CrashHandler.class){
                if (sCrashHandler == null) {
                    //使用Application Context
                    sCrashHandler=new CrashHandler();
                }
            }
        }
        return sCrashHandler;
    }

    /**
     * 初始化，把当前对象设置成UncaughtExceptionHandler处理器
     */
    public void init() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //当有未处理的异常发生时，就会来到这里
        Log.e(TAG, "uncaughtException, thread: " + thread
                + " name: " + thread.getName() + " id: " + thread.getId() + "exception: "
                + ex);

    }
}
