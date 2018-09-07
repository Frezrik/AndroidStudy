package com.frezrik.jniapi;

public class JPersistent {
	long mObj; //用于保存从native传回的对象地址
	
	public native int init();
	
	public native String test();
}
