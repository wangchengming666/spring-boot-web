package com.web.framework.common.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 不复制输出流方式
 * 
 * @author cm_wang
 *
 */
public class JwtResponseWrapper extends HttpServletResponseWrapper {
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private PrintWriter printWriter = new PrintWriter(outputStream);

	public JwtResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return printWriter;
	}

	public void flush() {
		printWriter.flush();
		printWriter.close();
	}

	public byte[] toByteArray() {
		return outputStream.toByteArray();
	}
}
