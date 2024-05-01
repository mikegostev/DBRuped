package me.gostev.sb.dbpq.resolve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class DefaultQuestionResolver implements QuestionResolver {
	private boolean useChatGPT=false;
	
	@Autowired
	@Qualifier("dbpedia")
	private QuestionResolver dbPediaResolver;
	
	@Override
	public String getAnswer(String question) throws NoAnswerException {
		return dbPediaResolver.getAnswer(question);
	}

}
