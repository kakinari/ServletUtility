/**
 * 
 */
package com.kakinari.servlet.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * @author Takashi Kakinari
 *
 */
public class ReadableServletOutputStream extends ServletOutputStream {

	private ByteArrayOutputStream buffer = null;
	protected WriteListener writeListener = null;

	/**
	 * 
	 */
	public ReadableServletOutputStream() {
		this.buffer = new ByteArrayOutputStream();
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {
		this.writeListener= writeListener;
	}

	@Override
	public void write(int b) throws IOException {
		buffer.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		buffer.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		buffer.write(b, off, len);
	}

	@Override
	public void flush() throws IOException {
		buffer.flush();
	}

	@Override
	public void close() throws IOException {
		buffer.close();
	}

	@Override
	public String toString() {
		return buffer.toString();
	}
	
	public byte[] toByteArray() {
		return buffer.toByteArray();
	}

	public void reset() {
		buffer.reset();
	}
}
