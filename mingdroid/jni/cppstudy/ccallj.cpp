#include "jniapi.h"

jstring getFromJava(JNIEnv *env, jobject obj)
{
	LOGD("getFromJava");

	LOGD("FindClass");
	jclass clazz = env->FindClass("com/frezrik/jniapi/JMethod");

	LOGD("GetMethodID");
	jmethodID mid = env->GetMethodID(clazz, "ccalljava", "()Ljava/lang/String;");

	LOGD("CallObjectMethod");
	jstring jstr = (jstring) env->CallObjectMethod(env->NewObject(clazz, mid), mid);//无对象，自己new

	LOGD("CallObjectMethod end");

	char *s = jstringToChar(env, jstr);

	LOGD("%s", s);

	return jstr;

}
