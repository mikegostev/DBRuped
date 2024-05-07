package me.gostev.scibite.dbruped.template;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.gostev.scibite.dbruped.util.StringUtils;

/**
 * Runs matching and holds correspondent information for the provided question
 * against a template.
 */
public class QuestionMatcher {
	private Boolean isMatch = null;
	private Template template;
	private String question;
	private Map<String, String> varMatches = new HashMap<>();

	public QuestionMatcher(Template template, String question) {
		this.template = template;
		this.question = StringUtils.normalizeQuestion(question);
	}

	/**
	 * Checks whether the question matches one of the questions of the template.
	 * 
	 * @return true if matches.
	 */
	public boolean matches() {
		if (isMatch == null) {
			doMatch();
		}

		return isMatch;
	}

	/**
	 * If matches returns a map of captured variables together and their values.
	 * 
	 * @return a map of name->value data for variables.
	 */
	public Map<String, String> getVariables() {
		return varMatches;
	}

	private void doMatch() {
		for (Question templateQuestion : template.getQuestions()) {
			Pattern pattern = templateQuestion.getPattern();
			Matcher matcher = pattern.matcher(question);

			if (!matcher.matches()) {
				continue;
			}

			for (int i = 1; i <= matcher.groupCount(); i++) {
				varMatches.put(templateQuestion.getVariables().get(i - 1), matcher.group(i));
			}

			isMatch = true;
			return;
		}

		isMatch = false;
	}
}
