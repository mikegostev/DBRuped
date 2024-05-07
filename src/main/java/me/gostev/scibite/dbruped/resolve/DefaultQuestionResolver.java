package me.gostev.scibite.dbruped.resolve;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import me.gostev.scibite.dbruped.dbpedia.DbPediaConnector;
import me.gostev.scibite.dbruped.query.NLQueryBuilder;
import me.gostev.scibite.dbruped.query.Query;
import me.gostev.scibite.dbruped.text.SimpleSubstitutorAndEvaluator;

/**
 * An implementation of {@link QuestionResolver} that utilises query templates
 * and optionally ChatGPT to build SPARQL queries.
 */
@Component
public class DefaultQuestionResolver implements QuestionResolver {
	@Autowired
	@Qualifier("template")
	private NLQueryBuilder templateQueryBuilder;

	@Autowired
	@Qualifier("chatGPT")
	private NLQueryBuilder chatGPTQueryBuilder;

	@Autowired
	private DbPediaConnector dbPediaConnector;

	@Override
	public Answer getAnswer(String question, boolean useChatGPT) {
		Query query = templateQueryBuilder.buildQuery(question);

		if (query.isValid()) {
			return new Answer(processDbPediaResponse(
					dbPediaConnector.getAnswer(query.getQueryText()),
					query.getVariablesMap(),
					query.getReturnExpressions()), Answer.NLProcessor.TEMPLATE, query.getQueryText());
		} else if (useChatGPT) {
			query = chatGPTQueryBuilder.buildQuery(question);
			if (query.isValid()) {
				return new Answer(processDbPediaResponse(
						dbPediaConnector.getAnswer(query.getQueryText()),
						query.getVariablesMap(),
						query.getReturnExpressions()), Answer.NLProcessor.CHATGPT, query.getQueryText());
			}
		}

		return Answer.createInvalid("Can't build query from the provided text", query.getQueryText());
	}

	private Map<String, String> processDbPediaResponse(Map<String, String> response, Map<String, String> variables,
			Map<String, String> returnExpression) {
		Map<String, String> combinedVars = new HashMap<String, String>();
		combinedVars.putAll(variables);
		combinedVars.putAll(response);

		SimpleSubstitutorAndEvaluator substitutor = new SimpleSubstitutorAndEvaluator();

		return returnExpression.entrySet().stream().collect(Collectors.toMap(
				me -> me.getKey(),
				me -> substitutor.substitute(me.getValue(), combinedVars)));
	}
}
