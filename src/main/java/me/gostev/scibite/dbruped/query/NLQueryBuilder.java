package me.gostev.scibite.dbruped.query;

/**
 * A common interface for services that convert natural language queries to
 * SPARQL queries.
 */
public interface NLQueryBuilder {
	/**
	 * Return a {@link Query} object for NL query/question.
	 * 
	 * @param question a string with NL query
	 * @return {@link Query} object that describes SPARQL query
	 */
	Query buildQuery(String question);
}
