package com.frezrik.androidstudy.service;

import android.content.Context;
import android.os.RemoteException;
import com.frezrik.common.aidl.IDeviceManager;
import com.frezrik.common.aidl.ITestListener;
import com.frezrik.common.aidl.TestResult;
import com.frezrik.core.api.ConvertUtil;
import com.frezrik.router.Router;
import com.frezrik.router.annotation.BindApi;

public class DeviceManagerService extends IDeviceManager.Stub {

    @BindApi
    ConvertUtil convertUtil;

    public DeviceManagerService(Context mContext) {
        Router.bind(this);
    }

    @Override
    public int setOnTestListener(ITestListener listener) throws RemoteException {
        TestResult result = new TestResult(convertUtil.randomBytes(16), convertUtil.randomBytes(16));
        listener.onSuccess(result);

        return 1;
    }
}
