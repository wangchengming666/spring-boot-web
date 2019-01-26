package com.web.framework.common.utils;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.CompressionCodecResolver;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.DefaultJwt;
import io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver;
import io.jsonwebtoken.impl.io.InstanceLocator;
import io.jsonwebtoken.io.DeserializationException;
import io.jsonwebtoken.io.Deserializer;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Classes;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.Keys;

/**
 * JSON Web Tokens (JWT) <br>
 * JJWT (io.jsonwebtoken) jwt实现之一 <br>
 * 
 * JWT=Header.Payload.Signature <br/>
 * Header={ "alg": "HS256", "typ": "JWT"} <br/>
 * Payload=reserved, public 和 private claim <br/>
 * <p>
 * Registered Claim Names= <br/>
 * iss: “iss” (issuer) claim 定义了发布这个 JWT 的当事人 <br/>
 * sub: The subject of the token，token 主题 <br/>
 * exp: Expiration Time。 token 过期时间，Unix 时间戳格式 <br/>
 * iat: Issued At。 token 创建时间， Unix 时间戳格式 <br/>
 * jti: JWT ID。针对当前 token 的唯一标识 <br/>
 * </p>
 * 
 * 
 * @author cm_wang
 * @date 20181026
 * @dete 20181224 增加 "iss" (Issuer) Claim
 * @date 20181228 修改过期时间单位为seconds
 * 
 */
public class JWTUtil {
  private static Deserializer<Map<String, ?>> deserializer;
  private static Decoder base64UrlDecoder = Base64.getUrlDecoder();
  private static CompressionCodecResolver compressionCodecResolver =
      new DefaultCompressionCodecResolver();

  /**
   * Authorization header using the Bearer schema.<br/>
   * The content of the header should look like the following:
   * 
   * Authorization: Bearer <token>
   */
  public static final String BEARER_SCHEMA = "Bearer ";

  static {
    // try to find one based on the runtime environment:
    InstanceLocator<Deserializer<Map<String, ?>>> locator =
        Classes.newInstance("io.jsonwebtoken.impl.io.RuntimeClasspathDeserializerLocator");
    deserializer = locator.getInstance();
  }

  /**
   * @see
   */
  public static String generateToken(byte[] signingKey, String issuer, long seconds,
      Map<String, Object> claims) {
    return generateToken(signingKey, issuer, null, seconds, claims);
  }

  /**
   * 
   * @param signingKey 密钥<br>
   *        一般是 "key".getBytes(Charset.forName("UTF-8"))
   * @param issuer 发行人
   * @param subject 主题
   * @param seconds 多少毫秒后过期 <br>
   *        30天:2592000<br>
   *        1天:86400<br>
   *        1小时:3600<br>
   * @param claims 内容 Public Claim Names
   * @return
   */
  public static String generateToken(byte[] signingKey, String issuer, String subject, long seconds,
      Map<String, Object> claims) {
    long now = System.currentTimeMillis();
    JwtBuilder builder = Jwts.builder();
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    if (signingKey == null) {
      throw new NullPointerException();
    }
    if (subject != null) {
      builder.setSubject(subject);
    }
    if (issuer != null) {
      builder.setIssuer(issuer);
    }
    // 内容
    if (claims != null) {
      builder.addClaims(claims);
    }

    // Expiration Time。 token 过期时间，Unix 时间戳格式
    builder.setExpiration(new Date(now + seconds * 1000L));

    builder.setIssuedAt(new Date(now));
    SecretKey key = Keys.hmacShaKeyFor(signingKey);

    // 设置算法（必须）
    builder.signWith(key, signatureAlgorithm);
    return builder.compact();
  }

  /**
   * 生成新的token
   * 
   * @return
   */
  public static String generateToken(byte[] signingKey, Map<String, Object> jwsHeader,
      Claims claims) {
    if (jwsHeader == null || claims == null) {
      throw new NullPointerException();
    }

    JwtBuilder builder = Jwts.builder();
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    SecretKey key = Keys.hmacShaKeyFor(signingKey);

    builder.setHeader(jwsHeader);
    builder.setClaims(claims);
    // 设置算法（必须）
    builder.signWith(key, signatureAlgorithm);
    return builder.compact();
  }

  /**
   * 生成完整的 JWT=Bearer + token
   * 
   * @param signingKey
   * @param subject
   * @param ttlmilliseconds
   * @param claims
   * @return BEARER_SCHEMA + token
   */
  public static String generateAuthorization(byte[] signingKey, String subject,
      long ttlmilliseconds, Map<String, Object> claims) {
    return BEARER_SCHEMA + generateToken(signingKey, subject, ttlmilliseconds, claims);

  }

  /**
   * 验证 token
   * 
   * @param token
   * @param signingKey 一般是 "key".getBytes(Charset.forName("UTF-8"))
   */
  public static void validateToken(String token, byte[] signingKey) {
    Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
  }

  /**
   * 验证 token
   * 
   * @param authorization
   * @param signingKey 一般是 "key".getBytes(Charset.forName("UTF-8"))
   */
  public static void validateAuthorization(String authorization, byte[] signingKey) {
    Jwts.parser().setSigningKey(signingKey).parseClaimsJws(removeBearerSchema(authorization))
        .getBody();
  }

  /**
   * 去掉 jwt token start
   * 
   * @param authorization http authorization
   * @return
   */
  public static String removeBearerSchema(String authorization) {
    return authorization.replaceFirst(BEARER_SCHEMA, "");
  }

  /**
   * 返回 Payload,即body
   * 
   * @param token
   * @param signingKey 一般是 "key".getBytes(Charset.forName("UTF-8"))
   */
  public static Map<String, Object> getBody(String token, byte[] signingKey) {
    return getJws(token, signingKey).getBody();
  }

  /**
   * 
   * @param token
   * @param signingKey 一般是 "key".getBytes(Charset.forName("UTF-8"))
   */
  public static Map<String, Object> getHead(String token, byte[] signingKey) {
    return getJws(token, signingKey).getHeader();
  }

  /**
   * 
   * @param token
   * @param signingKey 一般是 "key".getBytes(Charset.forName("UTF-8"))
   */
  public static String getSignature(String token, byte[] signingKey) {
    return getJws(token, signingKey).getSignature();
  }

  /**
   * 
   * @param token
   * @param signingKey 一般是 "key".getBytes(Charset.forName("UTF-8"))
   */
  public static Jws<Claims> getJws(String token, byte[] signingKey) {
    return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
  }

  /**
   * 取得jwt中的head和body信息，没做签名校验<br/>
   * ,参考DefaultJwtParser
   * 
   * @param token
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static Jwt<JwsHeader, Claims> getJwsNoSign(String token) {
    Assert.hasText(token, "JWT String argument cannot be null or empty.");

    String base64UrlEncodedHeader = null;
    String base64UrlEncodedPayload = null;
    String base64UrlEncodedDigest = null;

    int delimiterCount = 0;
    StringBuilder sb = new StringBuilder(128);
    for (char c : token.toCharArray()) {
      if (c == '.') {
        CharSequence tokenSeq = io.jsonwebtoken.lang.Strings.clean(sb);
        String part = tokenSeq != null ? tokenSeq.toString() : null;

        if (delimiterCount == 0) {
          base64UrlEncodedHeader = part;
        } else if (delimiterCount == 1) {
          base64UrlEncodedPayload = part;
        }

        delimiterCount++;
        sb.setLength(0);
      } else {
        sb.append(c);
      }
    }
    if (delimiterCount != 2) {
      String msg = "JWT strings must contain exactly 2 period characters. Found: " + delimiterCount;
      throw new MalformedJwtException(msg);
    }
    if (sb.length() > 0) {
      base64UrlEncodedDigest = sb.toString();
    }
    if (base64UrlEncodedPayload == null) {
      throw new MalformedJwtException("JWT string '" + token + "' is missing a body/payload.");
    }
    // =============== Header =================
    Header header = null;
    CompressionCodec compressionCodec = null;
    if (base64UrlEncodedHeader != null) {
      byte[] bytes = base64UrlDecoder.decode(base64UrlEncodedHeader);
      String origValue = new String(bytes, Strings.UTF_8);
      Map<String, Object> m = (Map<String, Object>) readValue(origValue);
      if (base64UrlEncodedDigest != null) {
        header = new DefaultJwsHeader(m);
      } else {
        header = new DefaultHeader(m);
      }
      compressionCodec = compressionCodecResolver.resolveCompressionCodec(header);
    }
    // =============== Body =================
    byte[] bytes = base64UrlDecoder.decode(base64UrlEncodedPayload);
    if (compressionCodec != null) {
      bytes = compressionCodec.decompress(bytes);
    }
    String payload = new String(bytes, Strings.UTF_8);

    Claims claims = null;

    if (payload.charAt(0) == '{' && payload.charAt(payload.length() - 1) == '}') { // likely to be
                                                                                   // json, parse
                                                                                   // it:
      Map<String, Object> claimsMap = (Map<String, Object>) readValue(payload);
      claims = new DefaultClaims(claimsMap);
    }

    return new DefaultJwt(header, claims);
  }

  protected static Map<String, ?> readValue(String val) {
    try {
      byte[] bytes = val.getBytes(Strings.UTF_8);
      return deserializer.deserialize(bytes);
    } catch (DeserializationException e) {
      throw new MalformedJwtException("Unable to read JSON value: " + val, e);
    }
  }
}
