package com.ming.androidstudy.server;


import android.os.RemoteException;
import com.ming.aidl.IFingerprintResult;
import com.ming.aidl.IFingerService;
import com.ming.aidl.IFingerprintListener;
import com.ming.api.BaseException;
import com.ming.api.fingerprint.FingerprintException;

public class FingerprintManagerBinder extends IFingerService.Stub {
    private static FingerprintManagerBinder mFingerprintManagerBinder = null;

    private FingerprintManagerBinder() {}

    public static FingerprintManagerBinder getInstance() {
        if (mFingerprintManagerBinder == null) {
            mFingerprintManagerBinder = new FingerprintManagerBinder();
        }
        return mFingerprintManagerBinder;
    }

    @Override
    public boolean fingerOpen() throws RemoteException {
        return true;
    }

    @Override
    public boolean fingerClose() throws RemoteException {
        return true;
    }

    @Override
    public boolean fingerStart(IFingerprintListener listener) throws RemoteException {
        IFingerprintResult result = new IFingerprintResult("captureImage".getBytes(), "featureCode".getBytes());
        listener.onSuccess(result);
        return true;
    }

    @Override
    public void fingerStop() throws RemoteException {
        throw new IllegalStateException("no");
    }

    @Override
    public void setTimeOut(int timeOut) throws RemoteException {

    }
}
