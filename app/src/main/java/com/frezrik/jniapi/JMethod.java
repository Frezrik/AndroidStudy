package com.frezrik.jniapi;

public class JMethod {

	public native String getFromJava();
	
	public String ccalljava() {
		return "c call java";
	}
}
