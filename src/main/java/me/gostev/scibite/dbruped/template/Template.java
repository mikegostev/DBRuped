package me.gostev.scibite.dbruped.template;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A template that if matches an NL sentence provides a SPARQL query to resolve
 * that question along with patterns for formatting the output.
 */
public class Template {
	/** A list question patterns to match this template */
	private List<Question> questions;
	/** SPARQL query text */
	private String query;
	/**
	 * A map of output formatting patterns. Keys in the map are tags attached to a
	 * formats so each format can be uniquely identified.
	 */
	private Map<String, String> returnExpressions;

	public static Template build(List<Question> questions, String query, Map<String, String> returnExpressions) {
		return new Template(questions, query, returnExpressions);
	}

	private Template(List<Question> questions, String query, Map<String, String> returnExpressions) {
		this.questions = questions;
		this.query = query;
		this.returnExpressions = returnExpressions;
	}

	public QuestionMatcher matcher(String question) {
		return new QuestionMatcher(this, question);
	}

	public List<Pattern> getQuestionPatterns() {
		return questions.stream().map(Question::getPattern).collect(Collectors.toList());
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public String getQuery() {
		return query;
	}

	public String getReturnExpression() {
		return returnExpressions.size() > 0 ? returnExpressions.values().iterator().next() : "";
	}

	public Map<String, String> getReturnExpressions() {
		return returnExpressions;
	}
}
