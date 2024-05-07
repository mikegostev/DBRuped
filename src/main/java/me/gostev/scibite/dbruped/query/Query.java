package me.gostev.scibite.dbruped.query;

import java.util.Map;

/**
 * Represents a SPARQL query.
 */
public class Query {

	/** The text of a query. */
	private String queryText;
	/** A map with captured variables. */
	private Map<String, String> variablesMap;
	/**
	 * If a query wasn't generated if contains a textual description of the reason.
	 */
	private String failureReason;
	/** Hold extra information about how query result should be formated */
	private Map<String, String> returnExpressionMap;

	public static Query createInvalidQuery(String reason) {
		Query query = new Query();
		query.failureReason = reason != null ? reason : "";
		return query;
	}

	private Query() {
	}

	public Query(String processedQuery, Map<String, String> varMap, Map<String, String> returnExpressionMap) {
		this.queryText = processedQuery;
		this.variablesMap = varMap;
		this.returnExpressionMap = returnExpressionMap;
	}

	public String getQueryText() {
		return queryText;
	}

	public boolean isValid() {
		return failureReason == null;
	}

	public Map<String, String> getVariablesMap() {
		return variablesMap;
	}

	public Map<String, String> getReturnExpressions() {
		return returnExpressionMap;
	}
}
