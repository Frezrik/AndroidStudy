#include <jni.h>
#include <assert.h>

#include "jniapi.h"

JNIEXPORT jstring JNICALL native_dynamicString(JNIEnv *env, jclass clazz)
{
	return env->NewStringUTF("dynamicString");
}

//--------------------------------------------------------------------------------------
static JNINativeMethod gMethods[] =
{
{ "dynamicString", "()Ljava/lang/String;", (void *) native_dynamicString } };

static const char* const kClassPathName = "com/frezrik/jniapi/NativeLib";

int register_dynamicString(JNIEnv *env)
{
	return jniRegisterNativeMethods(env, kClassPathName, gMethods,
			NELEM(gMethods));
}
