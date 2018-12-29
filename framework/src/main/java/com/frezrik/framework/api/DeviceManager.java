package com.frezrik.framework.api;

import android.os.RemoteException;
import android.os.ServiceManager;
import com.frezrik.framework.aidl.IDeviceManager;

public class DeviceManager {
    private static DeviceManager uniqueInstance = null;
    private IDeviceManager mIDeviceManager = null;

    private DeviceManager() {
    }

    /**
     * 获取DeviceManager实例
     *
     * @return DeviceManager实例
     */
    public static DeviceManager getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new DeviceManager();
        }
        return uniqueInstance;
    }

    /**
     * 获取DeviceManagerService
     *
     * @return DeviceManagerService
     * @throws DeviceException
     */
    private IDeviceManager getDMS() throws DeviceException {
        if (mIDeviceManager == null) {
            mIDeviceManager = IDeviceManager.Stub
                    .asInterface(ServiceManager.getService("FrezrikDeviceManager"));
        }

        if (mIDeviceManager != null) {
            boolean binderAlive = mIDeviceManager.asBinder().pingBinder();
            if (!binderAlive) {
                mIDeviceManager = IDeviceManager.Stub
                        .asInterface(ServiceManager.getService("FrezrikDeviceManager"));
            }
        }

        if (mIDeviceManager == null) {
            throw new DeviceException(DeviceException.SERVICE_NOT_AVAILABLE);
        }

        return mIDeviceManager;
    }

    /**
     * aidl传参DEMO
     * @param listener 传入TestListener
     * @throws DeviceException
     */
    public void setOnTestListener(TestListener listener) throws DeviceException {
        if (listener == null)
            throw new DeviceException(DeviceException.ERR_INVALID_ARGUMENT);

        IDeviceManager dms = getDMS();
        try {
            dms.setOnTestListener(listener);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new DeviceException(DeviceException.CONN_ERROR);
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
            throw new DeviceException(DeviceException.NO_SUPPORT_ERROR);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            throw DeviceException.getDeviceException(e.getMessage());
        }
    }
}
