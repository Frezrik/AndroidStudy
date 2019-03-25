package com.ming.api.fingerprint;


import android.os.RemoteException;
import android.os.ServiceManager;
import com.ming.aidl.IFingerService;

public class FingerprintManager {
	private static FingerprintManager uniqueInstance = null;
	private IFingerService mIFingerManager = null;
	private boolean isFingerOpened = false;
	private boolean isFingerStarted = false;

	private FingerprintManager() {
	}

	public static FingerprintManager getInstance() throws FingerprintException {
		if (uniqueInstance == null) {
			uniqueInstance = new FingerprintManager();
		}
		return uniqueInstance;
	}

	private IFingerService getBinderService() throws FingerprintException {
		if (mIFingerManager == null) {
			mIFingerManager = IFingerService.Stub.asInterface(ServiceManager.getService("MingFingerService"));
		}

		if (mIFingerManager == null) {
			throw new FingerprintException(FingerprintException.SERVICE_NOT_AVAILABLE);
		}

		return mIFingerManager;
	}

	/**
	 * 打开指纹采集模块
	 * 
	 * @throws FingerprintException
	 */
	public void fingerOpen() throws FingerprintException {
		if (isFingerOpened) {
			throw new FingerprintException(FingerprintException.FINGER_RETVAL_IS_OPEND);
		}
		
		isFingerOpened = true;
		IFingerService binder = getBinderService();
		try {
			boolean ret = binder.fingerOpen();
			if (!ret) {
				throw new FingerprintException(FingerprintException.FINGER_RETVAL_NOT_POWER_ON);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new FingerprintException(e.getMessage());
		} catch (NoSuchMethodError e) {
			e.printStackTrace();
			throw new FingerprintException(FingerprintException.NO_SUPPORT_ERROR);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new FingerprintException(e.getMessage());
		}
	}

	/**
	 * 关闭指纹采集模块
	 * 
	 * @throws FingerprintException
	 */
	public void fingerClose() throws FingerprintException {
		if (isFingerStarted) {
			fingerStop();
		}
		
		isFingerOpened = false;
		IFingerService binder = getBinderService();
		try {
			boolean ret = binder.fingerClose();
			if (!ret) {
				throw new FingerprintException(FingerprintException.FINGER_RETVAL_NOT_POWER_OFF);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new FingerprintException(e.getMessage());
		} catch (NoSuchMethodError e) {
			e.printStackTrace();
			throw new FingerprintException(FingerprintException.NO_SUPPORT_ERROR);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new FingerprintException(e.getMessage());
		}
	}

	/**
	 * 开始获取指纹
	 * 
	 * @param fingerListener 回调指纹采集结果
	 * @throws FingerprintException
	 */
	public void fingerStart(FingerprintListener fingerListener) throws FingerprintException {
		if (!isFingerOpened) {
			throw new FingerprintException(FingerprintException.FINGER_RETVAL_NOT_OPEND);
		}
		
		if (fingerListener == null) {
			throw new FingerprintException(FingerprintException.ERR_INVALID_ARGUMENT);
		}

		isFingerStarted = true;
		IFingerService binder = getBinderService();
		try {
			boolean ret = binder.fingerStart(fingerListener);
			if (!ret) {
				throw new FingerprintException(FingerprintException.FINGER_RETVAL_NOT_POWER_ON);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new FingerprintException(e.getMessage());
		} catch (NoSuchMethodError e) {
			e.printStackTrace();
			throw new FingerprintException(FingerprintException.NO_SUPPORT_ERROR);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new FingerprintException(e.getMessage());
		}
	}

	/**
	 * 停止获取指纹
	 * 
	 * @throws FingerprintException
	 */
	public void fingerStop() throws FingerprintException {
		if (!isFingerStarted) {
			throw new FingerprintException(FingerprintException.FINGER_RETVAL_NOT_STARTED);
		}
		
		isFingerStarted = false;
		IFingerService binder = getBinderService();
		try {
			binder.fingerStop();
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new FingerprintException(e.getMessage());
		} catch (NoSuchMethodError e) {
			e.printStackTrace();
			throw new FingerprintException(FingerprintException.NO_SUPPORT_ERROR);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new FingerprintException(e.getMessage());
		}
	}

	/**
	 * 设置获取指纹超时时间.如果设置了FingerprintListener,超时后,FingerprintListener.onError(int
	 * errorCode)将会被调用
	 * 
	 * @param timeOut
	 *            超时时间(单位:ms。范围:3~60s) 
	 * @throws FingerprintException
	 */
	public void setTimeOut(int timeOut) throws FingerprintException {
		if(timeOut <= 0) {
			throw new FingerprintException(FingerprintException.ERR_INVALID_ARGUMENT);
		}
		
		IFingerService binder = getBinderService();
		try {
			binder.setTimeOut(timeOut);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new FingerprintException(e.getMessage());
		} catch (NoSuchMethodError e) {
			e.printStackTrace();
			throw new FingerprintException(FingerprintException.NO_SUPPORT_ERROR);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new FingerprintException(e.getMessage());
		}
	}
}
