//
// Created by ololee on 2020/7/25.
//

#include <cstring>
#include "safe.h"
#define ActivityThread "android/app/ActivityThread"
#define currentApplication "currentApplication"
#define currentApplicationSignature "()Landroid/app/Application;"

static jobject getApplication(JNIEnv* env){
    jobject application=NULL;
    jclass activity_thread_clz=env->FindClass(ActivityThread);
    if(activity_thread_clz!=NULL)
    {
        jmethodID currentApplicationID=env->GetStaticMethodID(activity_thread_clz,currentApplication,currentApplicationSignature);
        if(currentApplication!=NULL)
        {
            application=env->CallStaticObjectMethod(activity_thread_clz,currentApplicationID);
        } else{
            LOGE("cannot find method:currentApplication in ActivityThread");
        }
        env->DeleteLocalRef(activity_thread_clz);
    } else{
        LOGE("can not find class android/app/ActivityThread");
    }
    return application;
}


/*
PackageManager manager=this.getPackageManager();
PackageInfo info=manager.getPackageInfo(this.getPackageName(),PackageManager.GET_SIGNATURES);
Signature[] signatures=info.signatures;
Log.d(TAG, "onCreate: "+signatures[0].toCharsString());
Toast.makeText(getApplicationContext(),signatures[0].toCharsString(),Toast.LENGTH_LONG).show();
 */
jstring getSignature(JNIEnv* env){
    jobject context=getApplication(env);
    //context class
    jclass context_clz=env->GetObjectClass(context);
    jmethodID packageManagerID=env->GetMethodID(context_clz,"getPackageManager","()Landroid/content/pm/PackageManager;");
    jobject packageManager=env->CallObjectMethod(context,packageManagerID);
    jclass packageManagerClass=env->GetObjectClass(packageManager);
    //getPackageInfo
    jmethodID packageInfoID=env->GetMethodID(packageManagerClass,"getPackageInfo","(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    //this.getPackageName()
    jmethodID packageNameID=env->GetMethodID(context_clz,"getPackageName","()Ljava/lang/String;");
    jstring packageName= (jstring)(env->CallObjectMethod(context, packageNameID));
    //packageInfo实例
    if(packageManager==NULL)
        LOGE("packageManager is NULL");
    jobject packageInfo=env->CallObjectMethod(packageManager,packageInfoID,packageName,64);
    jclass packageInfoClass=env->GetObjectClass(packageInfo);
    jfieldID signaturesID=env->GetFieldID(packageInfoClass,"signatures","[Landroid/content/pm/Signature;");
    jobjectArray signatures= (jobjectArray)(env->GetObjectField(packageInfo,signaturesID));
    //Signature Class
    jclass signatureClass=env->FindClass("android/content/pm/Signature");
    jobject signature=env->GetObjectArrayElement(signatures,0);
    jmethodID toCharsStringID=env->GetMethodID(signatureClass,"toCharsString","()Ljava/lang/String;");
    jstring signatureString= static_cast<jstring>(env->CallObjectMethod(signature,toCharsStringID));
    env->DeleteLocalRef(context);
    env->DeleteLocalRef(context_clz);
    env->DeleteLocalRef(packageManager);
    env->DeleteLocalRef(packageManagerClass);
    env->DeleteLocalRef(packageName);
    env->DeleteLocalRef(packageInfo);
    env->DeleteLocalRef(packageInfoClass);
    env->DeleteLocalRef(signatures);
    env->DeleteLocalRef(signatureClass);//signature,signatureString
    env->DeleteLocalRef(signature);
    return signatureString;
}


jboolean verify(JNIEnv* env,jstring verifyStr, const char * sign)
{
    char buff[1000];
    jboolean isCopy=JNI_FALSE;
    jboolean* copy=&isCopy;
    int cmp=-1;
    const char * signatureChars=env->GetStringUTFChars(verifyStr,copy);
    cmp=strcmp(signatureChars,sign);
    strcpy(buff,signatureChars);
    LOGD("%s",buff);
    env->ReleaseStringUTFChars(verifyStr,signatureChars);
    env->DeleteLocalRef(verifyStr);
    return cmp==0?JNI_OK:JNI_ERR;
}

jint JNI_OnLoad(JavaVM *vm,void *reserved)
{
    JNIEnv* env=NULL;
    if(vm->GetEnv((void**)&env,JNI_VERSION_1_4)!=JNI_OK){
        return JNI_ERR;
    }
    if(verify(env,getSignature(env),SIGNATURE_LOCAL)==JNI_OK)
        return JNI_VERSION_1_4;
    LOGE("签名不一致");
    return JNI_ERR;
}