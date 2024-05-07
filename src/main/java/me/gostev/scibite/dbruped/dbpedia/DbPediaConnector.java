package me.gostev.scibite.dbruped.dbpedia;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Implement communications with DBpedia
 */
@Component("dbpedia")
public class DbPediaConnector {
	public static String DBPEDIA_API_URL = "https://dbpedia.org/sparql";

	/**
	 * Returns a data according to provided query.
	 * 
	 * @param query SPARQL query to execute at DBpedia.
	 * @return a map of values (according to SELECT statement). E.g. SELECT ?a, ?b
	 *         will return a map with "a" and "b" keys.
	 */
	public Map<String, String> getAnswer(String query) {

		WebClient webClient = WebClient.create(DBPEDIA_API_URL);
		Response response = webClient.get()
				.uri(uriBuilder -> uriBuilder
						.queryParam("query", "{Q}")
						.queryParam("format", "application/json")
						.build(query))
				.retrieve()
				.bodyToMono(Response.class)
				.block();

		Map<String, String> resultMap = Collections.emptyMap();

		if (response.getResult() != null && response.getResult().getBindings() != null) {
			resultMap = response.getResult().getBindings().stream()
					.findFirst()
					.orElse(Collections.emptyMap())
					.entrySet().stream()
					.collect(Collectors.toMap(
							Map.Entry::getKey,
							e -> e.getValue().getValue()));
		}

		return resultMap;
	}
}
