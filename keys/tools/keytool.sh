#! /bin/bash

# 查看jks：keytool -v -list -keystore  ******.jks

# 生成jks
keytool -genkey -v -alias signKey -dname "CN=AS,OU=AS,O=AS,L=Shenzhen,ST=Guangdong,C=CN" -keyalg RSA -keysize 2048 -keypass signKey -keystore signKey.jks -storepass signKey -validity 9000 -deststoretype pkcs12
mv signKey.jks ../

#===============================================#
# 以下三个需要系统签名
# android:sharedUserId="android.uid.system"
# android:sharedUserId="android.uid.shared"
# android:sharedUserId="android.media"

# 方法一：配置Android.mk
# LOCAL_CERTIFICATE := platform 或 shared 或 media

# 方法二：java -jar signapk.jar platform.x509.pem platform.pk8 old.apk new.apk

# 方法三：导入证书
keytool -genkey -v -alias platform -dname "CN=AS,OU=AS,O=AS,L=Shenzhen,ST=Guangdong,C=CN" -keyalg RSA -keysize 2048 -keypass platformKey -keystore platformKey.jks -storepass platformKey -validity 9000 -deststoretype pkcs12
./keytool-importkeypair -k ./platformKey.jks -p platformKey -pk8 platform.pk8 -cert platform.x509.pem -alias platformKey
mv platformKey.jks ../

