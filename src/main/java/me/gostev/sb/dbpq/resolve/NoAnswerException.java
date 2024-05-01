package me.gostev.sb.dbpq.resolve;

public class NoAnswerException extends Exception {
	public NoAnswerException(String reason) {
		super(reason);
	}
}
