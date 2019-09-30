//
// Created by rodolfo on 27/09/19.
//

#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_rodolfogusson_testepag_infrastructure_service_Store_getApiKey(JNIEnv *env, jobject instance) {

 return (*env)->  NewStringUTF(env, "MWY1NGJkOTkwZjFjZGZiMjMwYWRiMzEyNTQ2ZDc2NWQ=");
}