#include "jniapi.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL Java_com_frezrik_jniapi_NativeLib_NewStringUTF(JNIEnv *env, jclass jclazz)
{
	LOGD("NewStringUTF Demo");
	return env->NewStringUTF("NewStringUTF");
}

JNIEXPORT jstring JNICALL Java_com_frezrik_jniapi_NativeLib_native_1init(JNIEnv *env, jclass jclazz)
{
	LOGD("native_init");
	return env->NewStringUTF("native_init");
}

JNIEXPORT jstring JNICALL Java_com_frezrik_jniapi_JMethod_getFromJava(JNIEnv *env, jobject obj)
{
	LOGD("getFromJava");
	return getFromJava(env, obj);
}

/*
 * Class:     com_frezrik_jniapi_JPersistent
 * Method:    init
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_frezrik_jniapi_JPersistent_init(JNIEnv *env, jobject obj)
{
    persistent_init(env, obj);
	return 0;
}

/*
 * Class:     com_frezrik_jniapi_JPersistent
 * Method:    test
 * Signature: ()Ljava/lang/String;
 *//*
JNIEXPORT jstring JNICALL Java_com_frezrik_jniapi_JPersistent_test(JNIEnv *env, jobject obj)
{
	return env->NewStringUTF("JPersistent_test");
}*/

#ifdef __cplusplus
}
#endif
