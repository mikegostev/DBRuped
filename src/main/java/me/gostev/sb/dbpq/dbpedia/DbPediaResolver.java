package me.gostev.sb.dbpq.dbpedia;

import org.springframework.stereotype.Component;

import me.gostev.sb.dbpq.resolve.NoAnswerException;
import me.gostev.sb.dbpq.resolve.QuestionResolver;

@Component("dbpedia")
public class DbPediaResolver implements QuestionResolver {
	public static String DBPEDIA_API_URL="";
	
	
	@Override
	public String getAnswer(String question) throws NoAnswerException {
		// TODO Auto-generated method stub
		return null;
	}

}
