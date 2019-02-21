package com.frezrik.androidstudy.service;

import android.content.Context;
import android.os.RemoteException;
import com.frezrik.common.aidl.IDeviceManager;
import com.frezrik.common.aidl.ITestListener;
import com.frezrik.common.aidl.TestResult;
import com.frezrik.core.Core;
import com.frezrik.core.api.ConvertUtil;

public class DeviceManagerService extends IDeviceManager.Stub {
    public DeviceManagerService(Context mContext) {

    }

    @Override
    public int setOnTestListener(ITestListener listener) throws RemoteException {
        TestResult result = new TestResult(Core.service(ConvertUtil.class).randomBytes(16), Core.service(ConvertUtil.class).randomBytes(16));
        listener.onSuccess(result);

        return 1;
    }
}
