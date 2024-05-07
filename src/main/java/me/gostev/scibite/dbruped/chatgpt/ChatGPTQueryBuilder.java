package me.gostev.scibite.dbruped.chatgpt;

import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Component;

import me.gostev.scibite.dbruped.query.NLQueryBuilder;
import me.gostev.scibite.dbruped.query.Query;

/**
 * Converts a natural language query to a DBpedia SPARQL query with help of
 * ChatGPT
 */
@Component("chatGPT")
public class ChatGPTQueryBuilder implements NLQueryBuilder {
	public static String prefixes = """
			prefix dbr: <http://dbpedia.org/resource/>
			prefix dbp: <http://dbpedia.org/property/>
			prefix dbo: <http://dbpedia.org/ontology/>
			""";

	public static String prompt = """
			For dbpedia.com create SPARQL query for the following request: "{Q}".
			Limit response to 1 entry. Represent result as ?result variable.
			Use prefixes:
			"""
			+ prefixes;

	private ChatGPTConnector connector = new ChatGPTConnector();

	@Override
	public Query buildQuery(String question) {
		String query = connector.sendRequest(prompt.replaceAll("\\{Q\\}", question));

		if (query.length() < 6) {
			return Query.createInvalidQuery("ChatGPT provided no answer");
		}

		query = query.replaceAll("\\n", "\n");

		if (!query.substring(0, 6).equalsIgnoreCase("prefix")) {
			query = prefixes + "\n" + query;
		}

		return new Query(query, Collections.emptyMap(), Map.of("", "${result}"));
	}

}
