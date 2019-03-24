#include <jni.h>
#include <stdio.h>
#include <assert.h>

#include "jniapi.h"

extern int register_dynamicString(JNIEnv *env);

jint JNI_OnLoad(JavaVM *vm, void *reserved)
{
	JNIEnv* env = NULL;
    jint result = -1;


    if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
    	LOGE("ERROR: GetEnv failed\n");
        goto bail;
    }

    assert(env != NULL);

    if (register_dynamicString(env) < 0) {
        LOGE("ERROR: dynamicString native registration failed\n");
        goto bail;
    }

    result = JNI_VERSION_1_4;

bail:
    return result;
}
