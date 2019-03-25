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
    private IMingManager mIMingManager;

    private MingManager(){}

    public static MingManager getInstance(Context context) {
        mContext = context.getApplicationContext();
        if(mingManager == null) {
            synchronized(MingManager.class) {
                if (mingManager == null)
                    mingManager = new MingManager();
            }
        }

        return mingManager;
    }

    private IMingManager getMMBinder() throws MingException {
        if (mIMingManager == null) {
            mIMingManager = IMingManager.Stub.asInterface(ServiceManager.getService("MingManagerService"));
        }

        if (mIMingManager != null) {
            boolean binderAlive = mIMingManager.asBinder().pingBinder();
            if (!binderAlive) {
                mIMingManager = IMingManager.Stub.asInterface(ServiceManager.getService("MingManagerService"));
            }
        } else {
            throw new MingException(MingException.SERVICE_NOT_AVAILABLE);
        }

        return  mIMingManager;
    }

    public void test() throws MingException {
        IMingManager mm = getMMBinder();
        if(mm != null) {
            try {
                mm.test();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                throw new MingException(e.toString());
            }
        }
    }
}
