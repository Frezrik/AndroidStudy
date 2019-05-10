package com.ming.androidstudy.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.orhanobut.logger.Logger;

import java.io.*;
import java.util.Locale;

public class PkgParser {

    /**
     * 获取App安装路径
     * @param context
     * @param packageName
     * @return
     */
    public static String getAppCodePath(Context context, String packageName) {
        PackageManager mPackageManager = context.getApplicationContext().getPackageManager();
        ApplicationInfo mApplicationInfo = null;
        String appCodePath = null;
        try {
            mApplicationInfo = mPackageManager.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (mApplicationInfo != null) {
            appCodePath = mApplicationInfo.sourceDir;
        }
        return appCodePath;
    }

    /**
     * 通过PID获取进程名
     * @param pid
     * @return
     */
    public static String getProcessNameByPid(int pid) {
        String processName = "";
        String cmdline = String.format(Locale.US, "/proc/%d/cmdline", pid);
        File f = new File(cmdline);
        if (f.exists()) {
            try {
                InputStream in = new FileInputStream(f);
                byte[] buffer = new byte[256];
                in.read(buffer);
                processName = new String(buffer).trim();
                in.close();
                String[] argv = processName.split("\0");
                if (argv != null) {
                    processName = argv[0];
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return processName;
    }

    /**
     * 通过UID获取App名
     * @param context
     * @param uID
     * @return
     */
    public static String getAppNameByUid(Context context, int uID) {
        PackageManager mPackageManager = context.getApplicationContext().getPackageManager();
        return mPackageManager.getNameForUid(uID);
    }

}
