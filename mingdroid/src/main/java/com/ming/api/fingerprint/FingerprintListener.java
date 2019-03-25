package com.ming.api.fingerprint;

import com.ming.aidl.IFingerprintListener;
import com.ming.aidl.IFingerprintResult;

public abstract class FingerprintListener extends IFingerprintListener.Stub {

    public void onSuccess(IFingerprintResult result) {
        onSuccess(new FingerprintResult(result.getCaptureImage(), result.getFeatureCode()));
    }

    /**
     * 指纹获取成功后，通过FingerprintResult获取结果
     */
    public abstract void onSuccess(FingerprintResult result);

    /**
     * 指纹获取失败时，通过errorCode通知调用者，对应的失败的原因
     *
     * @param errorCode 异常码
     */
    public abstract void onError(int errorCode);
}
