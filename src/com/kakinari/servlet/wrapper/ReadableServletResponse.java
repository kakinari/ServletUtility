/**
 * 
 */
package com.kakinari.servlet.wrapper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Takashi Kakinari
 *
 */
public class ReadableServletResponse extends HttpServletResponseWrapper {
	ReadableServletOutputStream buffer = null;
	private PrintWriter writer = null;
	private ServletOutputStream stream = null;
	/**
	 * @param response
	 */
	public ReadableServletResponse(HttpServletResponse response) {
		super(response);
		buffer = new ReadableServletOutputStream();
	}
	@Override
	public void flushBuffer() throws IOException {
		super.flushBuffer();
	}
	@Override
	public int getBufferSize() {
		return super.getBufferSize();
	}
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (this.writer != null) throw new IOException();
		if (this.stream == null)
			this.stream = (ServletOutputStream) buffer;
		return this.stream;
	}
	@Override
	public PrintWriter getWriter() throws IOException {
		if (this.stream != null) throw new IOException();
		if (this.writer == null)
			this.writer = new PrintWriter(buffer);
		return this.writer;
	}
	@Override
	public void reset() {
		resetBuffer();
		super.reset();
	}
	@Override
	public void resetBuffer() {
		this.buffer.reset();
	}
	@Override
	public String toString() {
		return buffer.toString();
	}
}
