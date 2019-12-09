/**
 * 
 */
package com.kakinari.servlet.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.kakinari.core.io.BufferedPrintWriter;
import com.kakinari.servlet.io.BufferedServletOutputStream;

/**
 * @author Takashi Kakinari
 *
 */
public class ReadableServletResponse extends HttpServletResponseWrapper {
	private ByteArrayOutputStream out = null;
	private PrintWriter writer = null;
	private ServletOutputStream stream = null;
	/**
	 * @param response
	 */
	public ReadableServletResponse(HttpServletResponse response) {
		super(response);
	}
	@Override
	public void flushBuffer() throws IOException {
		if (writer != null)
			writer.flush();
		if (stream != null)
			stream.flush();
		super.flushBuffer();
	}
	@Override
	public int getBufferSize() {
		return super.getBufferSize();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (this.writer != null) throw new IOException("getWriter is already called.");
		if (out == null)
			out = new ByteArrayOutputStream();
		if (this.stream == null)
			this.stream = new BufferedServletOutputStream(out);
		return this.stream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (this.stream != null) throw new IOException("getOutputStream is already called.");
		if (out == null)
			out = new ByteArrayOutputStream();
		if (this.writer == null)
			this.writer = new BufferedPrintWriter(out, true);
		return this.writer;
	}
	@Override
	public void reset() {
		resetBuffer();
		super.reset();
	}
	@Override
	public void resetBuffer() {
		if (this.out != null)
			this.out.reset();
	}

	public byte[] toByteArray() {
		try {
			if (writer != null) {
				writer.close();
				writer = null;
			}
			if (stream != null) {
				stream.close();
				stream = null;
			}
		} catch (IOException e) {
		}
		return out.toByteArray();
	}
	
	public String toString() {
		try {
			return new String(toByteArray(), getResponse().getCharacterEncoding());
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
