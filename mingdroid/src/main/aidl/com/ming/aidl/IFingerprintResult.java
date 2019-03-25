package com.ming.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class IFingerprintResult implements Parcelable {
	private byte[] captureImage;
	private byte[] featureCode;

	@Override
	public int describeContents() {
		return 0;
	}

	public IFingerprintResult(byte[] captureImage, byte[] featureCode) {
		this.captureImage = captureImage;
		this.featureCode = featureCode;
	}

	/**
	 * 获取捕获的指纹
	 * @return 指纹
	 */
	public byte[] getCaptureImage() {
		return captureImage;
	}

	/**
	 * 获取指纹特征码
	 * @return 指纹特征码
	 */
	public byte[] getFeatureCode() {
		return featureCode;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByteArray(this.captureImage);
		dest.writeByteArray(this.featureCode);
	}

	public void readFromParcel(Parcel source) {
		source.readByteArray(captureImage);
		source.readByteArray(featureCode);
	}

	private IFingerprintResult(Parcel in) {
		this.captureImage = in.createByteArray();
		this.featureCode = in.createByteArray();
	}

	public static final Parcelable.Creator<IFingerprintResult> CREATOR = new Parcelable.Creator<IFingerprintResult>() {
		@Override
		public IFingerprintResult createFromParcel(Parcel source) {
			return new IFingerprintResult(source);
		}

		@Override
		public IFingerprintResult[] newArray(int size) {
			return new IFingerprintResult[size];
		}
	};
}