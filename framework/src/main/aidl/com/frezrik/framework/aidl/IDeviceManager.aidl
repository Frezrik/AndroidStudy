package com.frezrik.framework.aidl;
import com.frezrik.framework.aidl.ITestListener;

interface IDeviceManager {

    int setOnTestListener(ITestListener listener);

}
