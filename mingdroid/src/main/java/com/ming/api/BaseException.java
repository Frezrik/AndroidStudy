package com.ming.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseException extends Exception {

    /**
     * 异常码
     */
    public static final int UNKNOWN_ERROR = -100;

    /**
     * 非法参数异常
     */
    public static final int ERR_INVALID_ARGUMENT = -101;

    /**
     * 服务不可用错误
     */
    public static final int SERVICE_NOT_AVAILABLE = -102;

    /**
     * 不支持的异常
     */
    public static final int NO_SUPPORT_ERROR = -103;

    /**
     * 没有权限异常
     */
    public static final int NO_PERMISSION_ERROR = -104;

    /**
     * 异常码
     */
    public static int exceptionCode = UNKNOWN_ERROR;

    /**
     * 使用当前堆栈跟踪和具体的详细信息构造新的异常。
     *
     * @param msg 异常的详细信息
     */
    public BaseException(String msg) {
        super(msg);
    }

    /**
     * 查询异常码对应的异常信息
     *
     * @param exCode 异常码
     * @return 异常码
     */
    public static String searchBaseMessage(int exCode) {
        String message = String.valueOf(exCode);
        switch (exCode) {
            case ERR_INVALID_ARGUMENT:
                message = "Parameter invalid";
                break;
            case SERVICE_NOT_AVAILABLE:
                message = "Service not available";
                break;
            case NO_SUPPORT_ERROR:
                message = "Not Support for this device";
                break;
            case NO_PERMISSION_ERROR:
                message = "No Permission for this device";
                break;
        }
        return message;
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str
     * @return
     */
    protected static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[-]?[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
}
