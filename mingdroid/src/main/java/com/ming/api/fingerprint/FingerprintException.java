package com.ming.api.fingerprint;

import android.util.Log;
import com.ming.api.BaseException;

public class FingerprintException extends BaseException {
	private static final String TAG = FingerprintException.class.getSimpleName();
	private static final long serialVersionUID = 1L;

	/**
	 * 设备上电失败
	 */
	public static final int FINGER_RETVAL_NOT_POWER_ON = -10;
	
	/**
	 * 设备下电失败
	 */
	public static final int FINGER_RETVAL_NOT_POWER_OFF = -11;
	
	/**
	 * 指纹模块已open
	 */
	public static final int FINGER_RETVAL_IS_OPEND = -12;
	
	/**
	 * 指纹模块未open
	 */
	public static final int FINGER_RETVAL_NOT_OPEND = -13;
	
	/**
	 * 指纹模块已start
	 */
	public static final int FINGER_RETVAL_IS_STARTED = -14;
	
	/**
	 * 指纹模块未start
	 */
	public static final int FINGER_RETVAL_NOT_STARTED = -15;
	
	
	/**
	 * 使用当前堆栈跟踪和具体的详细信息构造新的异常。
	 * 
	 * @param exCode
	 *            异常码
	 * 
	 */
	public FingerprintException(int exCode) {
		super(searchMessage(exCode));
	}

	/**
	 * 使用当前堆栈跟踪和具体的详细信息构造新的异常。
	 *
	 * @param message 异常的具体信息
	 *
	 */
	public FingerprintException(String message) {
		super(searchMessage(message));
	}
	
	private static String searchMessage(int exCode) {
		String msg = searchBaseMessage(exCode);
		try {
			Integer.parseInt(msg);
			switch (exCode) {
				case FINGER_RETVAL_NOT_POWER_ON:
					msg = "The fingerprint module does not power on";
					break;
				case FINGER_RETVAL_NOT_POWER_OFF:
					msg = "The fingerprint module does not power off";
					break;
				case FINGER_RETVAL_IS_OPEND:
					msg = "The fingerprint module is opened";
					break;
				case FINGER_RETVAL_NOT_OPEND:
					msg = "The fingerprint module is not opened";
					break;
				case FINGER_RETVAL_IS_STARTED:
					msg = "The fingerprint module is started";
					break;
				case FINGER_RETVAL_NOT_STARTED:
					msg = "The fingerprint module is not started";
					break;
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
