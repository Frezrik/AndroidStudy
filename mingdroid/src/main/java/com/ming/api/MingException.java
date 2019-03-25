package com.ming.api;

import android.util.Log;

public class MingException extends BaseException {
    private static final String TAG = MingException.class.getSimpleName();

    /**
     * 使用当前堆栈跟踪和具体的详细信息构造新的异常。
     *
     * @param exCode
     *            异常码
     *
     */
    public MingException(int exCode) {
        super(searchMessage(exCode));
    }

    /**
     * 使用当前堆栈跟踪和具体的详细信息构造新的异常。
     *
     * @param message 异常的具体信息
     *
     */
    public MingException(String message) {
        super(searchMessage(message));
    }

    /**
     * 查询异常码对应的异常信息
     *
     * @param exCode 异常码
     * @return 异常码
     */
    private static String searchMessage(int exCode) {
        String msg = searchBaseMessage(exCode);
        try {
            Integer.parseInt(msg);
            switch (exCode) {
                default:
                    exCode = UNKNOWN_ERROR;
                    msg = "Unknown error";
                    break;
            }
        } catch (NumberFormatException e) {

        }
        exceptionCode = exCode;
        return msg;
    }

    /**
     * 异常信息转换
     *
     * @param message
     * @return 异常信息
     */
    private static String searchMessage(String message) {
        try {
            return searchMessage(Integer.parseInt(message));
        } catch (NumberFormatException e) {
            Log.e(TAG, Thread.currentThread().getStackTrace()[4].getMethodName() + "=>" + message);
            return searchMessage(UNKNOWN_ERROR);
        }
    }
}
