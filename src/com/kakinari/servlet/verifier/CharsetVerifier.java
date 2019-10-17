package com.kakinari.servlet.verifier;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import com.kakinari.servlet.exception.IllegalParameterException;

public class CharsetVerifier {
	private ByteBuffer buffer = null;

	public CharsetVerifier(String value, String charsetName) throws IllegalParameterException {
		try {
			this.buffer  = Charset.forName(charsetName).newEncoder().encode(CharBuffer.wrap(value));
		} catch (CharacterCodingException e) {
			throw new IllegalParameterException("CharsetVerifier:" +  value + ":"+ charsetName + ":"+ e.getLocalizedMessage(),e);
		}
	}

	public CharsetVerifier(String value, Charset cs) throws IllegalParameterException {
		try {
			this.buffer  = cs.newEncoder().encode(CharBuffer.wrap(value));
		} catch (CharacterCodingException e) {
			throw new IllegalParameterException("CharsetVerifier:" +  value + ":"+ cs.toString() + ":"+ e.getLocalizedMessage(),e);
		}
	}

	public ByteBuffer get( ) {
		return buffer;
	}
}
