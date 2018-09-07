#ifndef JNIAPI_H_
#define JNIAPI_H_

#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <android/log.h>

#define DEBUG 1
#define TAG "JNIDEBUG"
#define LOGD(...) if(DEBUG) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGE(...) if(DEBUG) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

#ifndef NULL
#define NULL   ((void *) 0)
#endif

/* get #of elements in a static array */
#define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))

#ifdef __cplusplus
extern "C"  {
#endif
/*
 * Register one or more native methods with a particular class.
 */
int jniRegisterNativeMethods(JNIEnv* env, const char* className,
    const JNINativeMethod* gMethods, int numMethods);

char* jstringToChar(JNIEnv* env, jstring jstr);

jstring getFromJava(JNIEnv *env, jobject obj);

void persistent_init(JNIEnv *env, jobject obj);

#ifdef __cplusplus
}
#endif

#endif
