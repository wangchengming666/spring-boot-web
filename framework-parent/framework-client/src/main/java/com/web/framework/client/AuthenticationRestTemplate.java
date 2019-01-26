package com.web.framework.client;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 带自动认证，填充header authorization功能的 rest接口
 * 
 * @author cm_wang
 * @since 20181212
 */
public class AuthenticationRestTemplate extends RestTemplate {

	// http Authorization
	private String authorization;

	private HeaderAuthorization headerAuthorization;

	public AuthenticationRestTemplate() {
		super();
	}

	public AuthenticationRestTemplate(ClientHttpRequestFactory requestFactory) {
		this();
		setRequestFactory(requestFactory);
	}

	protected <T> T doExecute(URI url, @Nullable HttpMethod method, @Nullable RequestCallback requestCallback,
			@Nullable ResponseExtractor<T> responseExtractor) throws RestClientException {
		Assert.notNull(headerAuthorization, "headerAuthorization is required");
		Assert.notNull(url, "URI is required");
		Assert.notNull(method, "HttpMethod is required");

		ClientHttpResponse response = null;
		try {
			ClientHttpRequest request = createRequest(url, method);
			addAuthorization(request);
			if (requestCallback != null) {
				requestCallback.doWithRequest(request);
			}

			response = request.execute();

			if (headerAuthorization.needAuthorization(response)) {
				response = authenticationResponse(url, method, requestCallback);
			}

			handleResponse(url, method, response);
			return (responseExtractor != null ? responseExtractor.extractData(response) : null);
		} catch (IOException ex) {
			String resource = url.toString();
			String query = url.getRawQuery();
			resource = (query != null ? resource.substring(0, resource.indexOf('?')) : resource);
			throw new ResourceAccessException(
					"I/O error on " + method.name() + " request for \"" + resource + "\": " + ex.getMessage(), ex);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	private void addAuthorization(ClientHttpRequest ClientHttpRequest) {
		if (authorization != null && authorization.length() != 0) {
			ClientHttpRequest.getHeaders().put(HttpHeaders.AUTHORIZATION, Arrays.asList(authorization));
		}
	}

	/**
	 * 重新获取token，再提交之前的请求
	 * 
	 * @param url
	 * @param method
	 * @param requestCallback
	 * @return
	 * @throws IOException
	 */
	protected ClientHttpResponse authenticationResponse(URI url, HttpMethod method, RequestCallback requestCallback)
			throws IOException {
		Assert.notNull(headerAuthorization, "HeaderAuthorization not null");

		try {
			authorization = headerAuthorization.getAuthorization();
		} catch (Exception e) {
			throw e;
		}

		// 重新提交
		ClientHttpRequest request = createRequest(url, method);
		if (requestCallback != null) {
			requestCallback.doWithRequest(request);
		}
		// 用最新的token
		addAuthorization(request);
		return request.execute();
	}

	public HeaderAuthorization getHeaderAuthorization() {
		return headerAuthorization;
	}

	public void setHeaderAuthorization(HeaderAuthorization headerAuthorization) {
		this.headerAuthorization = headerAuthorization;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

}
