/*
 * Copyright 2017-2018 the original author(https://github.com/wj596)
 * 
 * <p> Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License. </p>
 */
package com.web.framework.common.matcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.web.framework.common.constants.KeysConstants;
import com.web.framework.common.token.JwtToken;
import com.web.framework.common.utils.JWTUtil;

/**
 * JWT匹配器
 * 
 * @author cm_wang
 */
public class JwtMatcher implements CredentialsMatcher {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		if (!(token instanceof JwtToken))
			return false;
		String jwt = ((JwtToken) token).getJwt();
		JWTUtil.validateToken(jwt, KeysConstants.JWT_KEY_BYTES);
		return true;
	}

}
