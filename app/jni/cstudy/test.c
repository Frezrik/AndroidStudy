#include "jniapi.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL Java_com_frezrik_jniapi_JNIStudy_test(JNIEnv *env, jclass jclazz)
{
    return (*env)->NewStringUTF(env, "test");
}

JNIEXPORT void JNICALL Java_com_frezrik_jniapi_JNIStudy_getStringUTFChars(JNIEnv *env, jclass jclazz, jstring prompt)
{
    char buf[128];
    const char *str;
    str = (*env)->GetStringUTFChars(env, prompt, NULL);
    if(str == NULL)
    {
        return; //OutOfMemoryError
    }
    LOGE("%s", str);

    (*env)->ReleaseStringUTFChars(env, prompt, str);
}


#ifdef __cplusplus
}
#endif
