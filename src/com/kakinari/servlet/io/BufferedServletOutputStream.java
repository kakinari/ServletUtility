/**
 * 
 */
package com.kakinari.servlet.io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * @author Takashi Kakinari
 *
 */
public class BufferedServletOutputStream extends ServletOutputStream {
	private DataOutputStream buffer = null;
	protected WriteListener writeListener = null;

	/**
	 * 
	 */
	public BufferedServletOutputStream(OutputStream out) {
		super();
		this.buffer = new DataOutputStream(out);
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {
		if (writeListener == null)
			throw new NullPointerException();;
		this.writeListener= writeListener;
		try {
			writeListener.onWritePossible();
		} catch (IOException e) {
			throw new IllegalStateException();
		}
	}

	@Override
	public void write(int b) throws IOException {
		try {
			this.buffer.write(b);
		} catch (IOException e) {
			if (writeListener != null)
				writeListener.onError(e);
			throw new IOException(e);
		}
	}

	@Override
	public void write(byte[] b) throws IOException {
		try {
			this.buffer.write(b);
		} catch (IOException e) {
			if (writeListener != null)
				writeListener.onError(e);
			throw new IOException(e);
		}
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		try {
			this.buffer.write(b, off, len);
		} catch (IOException e) {
			if (writeListener != null)
				writeListener.onError(e);
			throw new IOException(e);
		}
	}

	@Override
	public void flush() throws IOException {
		try {
			this.buffer.flush();
		} catch (IOException e) {
			if (writeListener != null)
				writeListener.onError(e);
			throw new IOException(e);
		}
	}

	@Override
	public void close() throws IOException {
		try {
			this.buffer.close();
		} catch (IOException e) {
			if (writeListener != null)
				writeListener.onError(e);
			throw new IOException(e);
		}

	}

	public String toString() {
		try {
			this.buffer.flush();
			return this.buffer.toString();
		} catch (IOException e) {
			if (writeListener != null)
				writeListener.onError(e);
			return null;
		}
	}
}
