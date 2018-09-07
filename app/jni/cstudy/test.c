#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL Java_com_frezrik_jniapi_NativeLib_test(JNIEnv *env, jclass jclazz)
{
    return (*env)->NewStringUTF(env, "test");
}


#ifdef __cplusplus
}
#endif
