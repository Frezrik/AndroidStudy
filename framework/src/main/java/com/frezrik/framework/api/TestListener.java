package com.frezrik.framework.api;

import com.frezrik.framework.aidl.ITestListener;
import com.frezrik.framework.aidl.TestResult;

public abstract class TestListener extends ITestListener.Stub {
    @Override
    public abstract void onSuccess(TestResult result);

    @Override
    public abstract void onError(int errorCode);
}
