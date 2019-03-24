#include "jniapi.h"
#include <string>

using namespace std;

class Persistent {
public:
    string test();
};

void persistent_init(JNIEnv *env, jobject obj) {
    LOGD("persistent init");

    jclass clazz = env->GetObjectClass(obj);
    jfieldID fid = env->GetFieldID(clazz, "mObj", "J");

    Persistent *persistent = new Persistent();
    env->SetLongField(obj, fid, (jlong) persistent);
}


