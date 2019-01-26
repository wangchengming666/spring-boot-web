package com.web.framework.common.component;

import org.jasypt.digest.PooledStringDigester;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.RandomSaltGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.web.framework.common.constants.KeysConstants;

/**
 * 加解密工具
 * 
 * @author cm_wang
 *
 */
@Component
public class EncryptorComponent {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 密码 PBE (Password Based Encryption) encryption operations.
	 */
	private PBEStringEncryptor pbeEncrytoer;
	/**
	 * salt
	 */
	private String salt;
	/**
	 * 消费摘要
	 */
	private PooledStringDigester stringDigester;

	public EncryptorComponent() {
	}

	private void initPasswordEncryptor() {
		initSalt();
		if (pbeEncrytoer == null) {
			// Algorithm: PBEWithMD5AndDES
			pbeEncrytoer = new StandardPBEStringEncryptor();
			pbeEncrytoer.setPassword(getSalt());
		}
	}

	private void initTextDigester() {
		if (stringDigester == null) {
			stringDigester = new PooledStringDigester();
			stringDigester.setPoolSize(Runtime.getRuntime().availableProcessors());
			stringDigester.setIterations(5);
			stringDigester.setSaltGenerator(new RandomSaltGenerator());
			// ((PooledStringDigester) stringDigester).setConfig(new
			// SimpleDigesterConfig());
		}
	}

	private void initSalt() {
		if (this.salt == null) {
			setSalt(KeysConstants.SALT_KEY);
		}
	}

	/**
	 * 密码加密 <br/>
	 * 加密出来的长度与你输入的密码长度有关，与salt无关
	 * 
	 * @param inputPassword
	 * @return 长度 至少 24位
	 */
	public String passwordEncrypt(String inputPassword) {
		initPasswordEncryptor();
		return pbeEncrytoer.encrypt(inputPassword);
	}

	/**
	 * 密码解密<br/>
	 * 
	 * @param inputPassword
	 * @return
	 */
	public String passwordDecrypt(String encryptedMessage) {
		initPasswordEncryptor();
		return pbeEncrytoer.decrypt(encryptedMessage);
	}

	/**
	 * 生成消息摘要<br/> 性能非常快
	 * 
	 * @param message
	 * @return
	 */
	public String textDigester(String message) {
		initTextDigester();
		return stringDigester.digest(message);
	}

	/**
	 * 消息摘要是否正确
	 * 
	 * @param message 原始数据
	 * @param digest 消息摘要
	 * @return
	 */
	public boolean textDigesterMather(String message, String digest) {
		initTextDigester();
		return stringDigester.matches(message, digest);
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
