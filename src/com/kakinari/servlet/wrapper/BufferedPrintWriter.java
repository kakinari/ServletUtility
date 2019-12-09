package com.kakinari.servlet.wrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class BufferedPrintWriter extends PrintWriter {
	private static BufferedWriter buffer = null;

	public BufferedPrintWriter(Writer out) {
		super(buffer = new BufferedWriter(out));
	}

	public BufferedPrintWriter(OutputStream out) {
		super(buffer = new BufferedWriter(new OutputStreamWriter(out)));
	}

	public BufferedPrintWriter(String fileName) throws FileNotFoundException {
		super(fileName);
	}

	public BufferedPrintWriter(File file) throws FileNotFoundException {
		super(file);
	}

	public BufferedPrintWriter(Writer out, boolean autoFlush) {
		super(new BufferedWriter(out), autoFlush);
	}

	public BufferedPrintWriter(OutputStream out, boolean autoFlush) {
		super(buffer = new BufferedWriter(new OutputStreamWriter(out)), autoFlush);
	}

	public BufferedPrintWriter(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(fileName, csn);
	}

	public BufferedPrintWriter(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(file, csn);
	}

	@Override
	public void close() {
		if (buffer != null)
			try {
				buffer.close();
			} catch (IOException e) {}
		super.close();
	}

	@Override
	public void flush() {
		if (buffer != null) {
			try {
				buffer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.flush();
	}
	
}
