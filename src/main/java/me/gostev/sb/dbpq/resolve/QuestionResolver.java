package me.gostev.sb.dbpq.resolve;

public interface QuestionResolver {
	String getAnswer(String question) throws NoAnswerException;
}
