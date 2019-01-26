@REM 根据私有密钥生成公钥
keytool -export ^
	-alias tomcat ^
	-keystore www.2tiger.site_keystore.jks ^
	-storetype PKCS12 ^
	-storepass xxooxxoo ^
	-rfc ^
	-file tomcat-PublicKey.cer
