package me.gostev.scibite.dbruped.resolve;

import java.util.Map;

/**
 * Represents an answer that will be sent by REST API
 */
public class Answer {
	public static enum NLProcessor {
		CHATGPT,
		TEMPLATE
	}

	/**
	 * For valid answers contains an actual answer text. Otherwise the reason of
	 * failure.
	 */
	private Map<String, String> answersText;
	/** The SPQRQL query that was user to get an answer. */
	private String sparqlQuery;
	/** Shows a way how the query was generated. */
	private NLProcessor nlProcessor;
	/** Indicates whether this answer is a valid answer. */
	private boolean isValid;

	public static Answer createInvalid(String message, String sparql) {
		Answer answer = new Answer(Map.of("", message), null, sparql);
		answer.isValid = false;
		return answer;
	}

	public static Answer create(Map<String, String> answer, NLProcessor nlProcessor, String sparql) {
		return new Answer(answer, nlProcessor, sparql);
	}

	public Answer(Map<String, String> answers, NLProcessor nlProcessor, String sparql) {
		super();
		this.answersText = answers;
		this.nlProcessor = nlProcessor;
		this.sparqlQuery = sparql;
		this.isValid = true;
	}

	public String getAnswerText() {
		return answersText.values().iterator().next();
	}

	public Map<String, String> getAnswersMap() {
		return answersText;
	}

	public NLProcessor getNlProcessor() {
		return nlProcessor;
	}

	public boolean isValid() {
		return isValid;
	}

	public String getSparqlQuery() {
		return sparqlQuery;
	}
}
