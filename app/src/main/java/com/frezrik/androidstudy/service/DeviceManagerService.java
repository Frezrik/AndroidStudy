package com.frezrik.androidstudy.service;

import android.content.Context;
import android.os.RemoteException;
import com.frezrik.framework.aidl.IDeviceManager;
import com.frezrik.framework.aidl.ITestListener;
import com.frezrik.framework.aidl.TestResult;
import com.frezrik.framework.utils.ConvertUtils;

public class DeviceManagerService extends IDeviceManager.Stub {
    public DeviceManagerService(Context mContext) {

    }

    @Override
    public int setOnTestListener(ITestListener listener) throws RemoteException {
        TestResult result = new TestResult(ConvertUtils.randomBytes(16), ConvertUtils.randomBytes(16));
        listener.onSuccess(result);

        return 1;
    }
}
