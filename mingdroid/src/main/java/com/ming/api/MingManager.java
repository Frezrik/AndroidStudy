package com.ming.api;

import android.content.Context;
import android.os.IBinder;
import android.os.IMingManager;
import android.os.RemoteException;
import android.os.ServiceManager;

/**
 * Created by frezrik on 18-4-19.
 */

public class MingManager {

    private static MingManager mingManager;
    private static Context mContext;

    private MingManager(){}

    public static MingManager getInstance(Context context) {
        mContext = context.getApplicationContext();
        if(mingManager == null) {
            mingManager = new MingManager();
        }

        return mingManager;
    }

    private IMingManager getMMBinder() {
        IBinder binder = ServiceManager.getService("MingManagerService");
        return binder == null?null:IMingManager.Stub.asInterface(binder);
    }

    public void test() {
        IMingManager mm = getMMBinder();
        if(mm != null) {
            try {
                mm.test();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
