package com.ming.aidl;
import com.ming.aidl.IFingerprintResult;

interface IFingerprintListener {
    /**
     * 获取指纹结果
     */
    void onSuccess(in IFingerprintResult result);

    /**
     * 失败时，通过errorCode通知调用者，对应的失败的原因
     */
    void onError(int errorCode);
}
