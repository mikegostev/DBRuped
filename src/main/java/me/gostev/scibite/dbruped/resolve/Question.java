package me.gostev.scibite.dbruped.resolve;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A convenience class to provide a question resolving service by a static
 * method.
 */
@Service
public class Question implements InitializingBean {
	@Autowired
	private DefaultQuestionResolver resolver;

	private static Question instance;

	@Override
	public void afterPropertiesSet() throws Exception {
		instance = this;
	}

	/**
	 * Returns a data (String or int) after resolving a NL question.
	 * 
	 * @param question a natural language question to resolve.
	 * @return int or String that represents an answer.
	 */
	public static Object ask(String question) {
		return instance.askInst(question);
	}

	public Object askInst(String question) {
		String answer = resolver.getAnswer(question, true).getAnswerText();

		try {
			return Integer.parseInt(answer);
		} catch (Exception e) {
			return answer;
		}
	}
}
