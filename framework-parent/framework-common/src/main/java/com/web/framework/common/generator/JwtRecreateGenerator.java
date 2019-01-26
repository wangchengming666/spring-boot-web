package com.web.framework.common.generator;

import java.util.Date;
import java.util.Map;

import com.web.framework.common.utils.JWTUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwt;

public class JwtRecreateGenerator {
	private Jwt<JwsHeader, Claims> oldJwt;
	private Map<String, Object> jwsHeader;
	private Claims claims;

	/**
	 * 
	 * @param jwt
	 *            Old jwt
	 */
	public JwtRecreateGenerator(String jwt) {
		super();
		parseJwt(jwt);
	}

	private void parseJwt(String jwt) {
		oldJwt = JWTUtil.getJwsNoSign(jwt);
		jwsHeader = oldJwt.getHeader();
		claims = oldJwt.getBody();
		claims.setIssuedAt(new Date(System.currentTimeMillis()));
	}

	public String getFromOld(String key) {
		return (String) oldJwt.getBody().get(key);
	}

	public void add(String key, String value) {
		claims.put(key, value);
	}

	/**
	 * @param ttlmilliseconds
	 *            多少毫秒后过期 <br>
	 *            30天:2592000000<br>
	 *            1天:86400000<br>
	 *            1小时:3600000<br>
	 */
	public void setExpiration(long ttlmilliseconds) {
		claims.setExpiration(new Date(System.currentTimeMillis() + ttlmilliseconds));
	}

	public String build(byte[] signingKey) {
		return JWTUtil.generateToken(signingKey, jwsHeader, claims);
	}

	public void setIssuer(String issuer) {
		claims.setIssuer(issuer);
	}
}
