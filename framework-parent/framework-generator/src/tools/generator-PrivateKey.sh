
keytool -genkeypair \
        -alias tomcat \
        -keyalg RSA \
        -keysize 4096 \
        -keypass xxooxxoo \
        -sigalg SHA256withRSA \
        -dname "cn=www.2tiger.site,ou=cm_wang,o=guojing,l=chengdu,st=sichuang,c=CN" \
        -validity 3650 \
        -keystore www.2tiger.site_keystore.jks \
        -storetype PKCS12 \
        -storepass xxooxxoo
