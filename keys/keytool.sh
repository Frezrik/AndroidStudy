# 以下三个需要系统签名
# android:sharedUserId="android.uid.system"
# android:sharedUserId="android.uid.shared"
# android:sharedUserId="android.media"

# 查看jks：keytool -v -list -keystore  ******.jks

# 方法一：配置Android.mk
# LOCAL_CERTIFICATE := platform 或 shared 或 media

# 方法二：java -jar signapk.jar platform.x509.pem platform.pk8 old.apk new.apk

# 方法三：导入证书
keytool -genkey -v -alias platformkey -dname "CN=AS,OU=AS,O=AS,L=Shenzhen,ST=Guangdong,C=CN" -keyalg RSA -keysize 2048 -keypass platformkey -keystore platformKey.jks -storepass platformkey -validity 9000  
./keytool-importkeypair -k ./platformKey.jks -p platformkey -pk8 platform.pk8 -cert platform.x509.pem -alias platformkey