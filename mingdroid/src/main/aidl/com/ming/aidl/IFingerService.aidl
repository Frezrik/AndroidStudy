package com.ming.aidl;
import com.ming.aidl.IFingerprintListener;

interface IFingerService {
    /**
     * 打开指纹
     */
    boolean fingerOpen();

    /**
     * 关闭指纹
     */
    boolean fingerClose();

    /**
     * 开始获取指纹.
     */
    boolean fingerStart(in IFingerprintListener listener);

    /**
     * 停止获取指纹.
     */
    void fingerStop();

    /**
     * 设置获取指纹超时时间.
     */
    void setTimeOut(int timeOut);
}
