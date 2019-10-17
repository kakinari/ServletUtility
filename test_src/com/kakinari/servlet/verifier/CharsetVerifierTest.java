package com.kakinari.servlet.verifier;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.kakinari.servlet.exception.IllegalParameterException;

public class CharsetVerifierTest {

	public static void main(String[] args) {
		try {
			String str = "のゔぇ";
			if (args.length>0)
				str = args[0];
			CharsetVerifier verifier = new CharsetVerifier(str, Charset.forName("SJIS"));
			ByteBuffer buffer = verifier.get();
			for ( byte c : buffer.array())
				System.out.print(String.format("0X%02X,", c));
			System.out.println();
			System.out.println(verifier.get().toString());
		} catch (IllegalParameterException e) {
			System.out.println(e.getLocalizedMessage());
		}
		
	}

}
