package com.web.framework.common.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.util.ResourceUtils;

import com.web.framework.common.component.EncryptorComponent;
import com.web.framework.common.constants.ActiveProfilesConstants;
import com.web.framework.common.constants.FrameworkConstants;
import com.web.framework.common.constants.KeysConstants;

/**
 * 处理 Environment
 * 
 * @author cm_wang
 *
 */
public class ApplicationEnvironmentPreparedListener
		implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private ConfigurableEnvironment env;
	/**
	 * 需要解密的字段
	 */
	private String[] needDecryptMapKeys = new String[] { "framework.datasource.username",
			"framework.datasource.password" };

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

		this.env = event.getEnvironment();

		handleEnvironment();

	}

	public void handleEnvironment() {
		Objects.requireNonNull(env);

		initFrameworkConstants();

		initKeysConstants();

		initActiveProfilesConstants();

		MutablePropertySources propSources = env.getPropertySources();
		StreamSupport.stream(propSources.spliterator(), false).forEach(p -> {
			if (p instanceof OriginTrackedMapPropertySource) {
				OriginTrackedMapPropertySource o = (OriginTrackedMapPropertySource) p;
				initEncryptorConstants(p.getName(), o);
			}
		});
	}

	private void initFrameworkConstants() {
		File frameworkPath = getDirPath(File.separatorChar + "framework");
		FrameworkConstants.FRAMEWORK_PROPERTIES = new File(frameworkPath, "framework.properties");

	}

	/**
	 * 自动替换 加密的内容
	 */
	private void initEncryptorConstants(String name, OriginTrackedMapPropertySource propertySource) {
		Map<String, Object> map = propertySource.getSource();
		for (String key : needDecryptMapKeys) {
			if (map.containsKey(key)) {
				String message = (String) propertySource.getProperty(key);
				if (StringUtils.startsWith(message, "ENC(")) {
					logger.info("解密配置文件中的 {}", key);
					try {
						propertySource.getSource().put(key, decrypt(KeysConstants.SALT_KEY, message));
					} catch (Exception e) {
						logger.error("解密出错,密钥【{}】,加密密码【{}】， 程序中止!", KeysConstants.SALT_KEY, message, e);
						System.exit(1);
					}
				}
			}
		}
	}

	private String decrypt(String salt, String message) {
		String encryptedPassword = StringUtils.substringAfter(message, "ENC(");
		encryptedPassword = StringUtils.substringBefore(encryptedPassword, ")");
		EncryptorComponent encryptorComponent = new EncryptorComponent();
		encryptorComponent.setSalt(salt);
		return encryptorComponent.passwordDecrypt(encryptedPassword);
	}

	private File getDirPath(String subPath) {
		if (null == FrameworkConstants.ROOT_DIR) {
			try {
				FrameworkConstants.ROOT_DIR = ResourceUtils
						.getFile(System.getProperty("framework.filePath", env.getProperty("framework.filePath")));

				logger.info("FrameworkConstants.ROOT_DIR :{}", FrameworkConstants.ROOT_DIR.getAbsolutePath());
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
			}

		}
		File dir = new File(FrameworkConstants.ROOT_DIR, subPath);
		dir.mkdirs();
		return dir;
	}

	private void initKeysConstants() {

		if (env.containsProperty(KeysConstants.COMMAND_LINE_SALT_KEY)
				&& StringUtils.isNotBlank(env.getProperty(KeysConstants.COMMAND_LINE_SALT_KEY))) {
			KeysConstants.SALT_KEY = env.getProperty(KeysConstants.COMMAND_LINE_SALT_KEY);
		} else {
		}

		if (env.containsProperty(KeysConstants.COMMAND_LINE_SECRET_KEY)
				&& StringUtils.isNotBlank(env.getProperty(KeysConstants.COMMAND_LINE_SECRET_KEY))) {
			KeysConstants.SECRET_KEY = env.getProperty(KeysConstants.COMMAND_LINE_SECRET_KEY);
			KeysConstants.JWT_KEY_BYTES = KeysConstants.SECRET_KEY.getBytes(Charset.forName("UTF-8"));
		} else {
		}

	}

	private void initActiveProfilesConstants() {

		String[] actives = env.getActiveProfiles();

		// 如果有prd
		if (Arrays.binarySearch(actives, "prd") > -1) {
			ActiveProfilesConstants.isPrd = true;
		} else {
			ActiveProfilesConstants.isPrd = false;
		}
		// 如果有dev
		if (Arrays.binarySearch(actives, "dev") > -1) {
			ActiveProfilesConstants.isDev = true;
		} else {
			ActiveProfilesConstants.isDev = false;
		}

	}

	public ConfigurableEnvironment getEnv() {
		return env;
	}

	public void setEnv(ConfigurableEnvironment env) {
		this.env = env;
	}

}
