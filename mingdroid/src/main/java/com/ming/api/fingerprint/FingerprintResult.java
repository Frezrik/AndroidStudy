package com.ming.api.fingerprint;


import java.util.Arrays;

public class FingerprintResult {
	private byte[] captureImage;
	private byte[] featureCode;

	public FingerprintResult(byte[] captureImage, byte[] featureCode) {
		this.captureImage = captureImage;
		this.featureCode = featureCode;
	}

	public byte[] getCaptureImage() {
		return captureImage;
	}

	public byte[] getFeatureCode() {
		return featureCode;
	}

	@Override
	public String toString() {
		return "FingerprintResult{" +
				"captureImage=" + Arrays.toString(captureImage) +
				", featureCode=" + Arrays.toString(featureCode) +
				'}';
	}
}