package me.gostev.scibite.dbruped.template;

import java.io.IOException;

public class ParserException extends RuntimeException {

	public ParserException(String message) {
		super(message);
	}

	public ParserException(String message, IOException e) {
		super(message, e);
	}

}
