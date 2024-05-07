package me.gostev.scibite.dbruped.template;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import me.gostev.scibite.dbruped.query.NLQueryBuilder;
import me.gostev.scibite.dbruped.query.Query;
import me.gostev.scibite.dbruped.text.SimpleSubstitutorAndEvaluator;

/**
 * Build a SPARQL query according to templates described in a resource file. See
 * {@link ResourceFileTemplateSource}
 */
@Component("template")
public class TemplateQueryBuilder implements NLQueryBuilder {
	@Autowired
	@Qualifier("resourceFileSource")
	private TemplateSource source;
	private boolean isValid = false;

	private List<Template> templates;

	public TemplateQueryBuilder() {
	}

	public void init() {
		source.load();
		templates = source.getTemplates();
		isValid = true;
	}

	@Override
	public Query buildQuery(String question) {
		init();

		if (!isValid) {
			return Query.createInvalidQuery("TemplateQueryBuilder was not initialized properly");
		}

		for (Template template : templates) {
			QuestionMatcher matcher = template.matcher(question);
			if (matcher.matches()) {
				Map<String, String> varMap = matcher.getVariables();
				String processedQuery = substituteVariables(template.getQuery(), varMap);
				return new Query(processedQuery, varMap, template.getReturnExpressions());
			}
		}

		return Query.createInvalidQuery("No template matched");
	}

	private String substituteVariables(String query, Map<String, String> varMap) {
		return new SimpleSubstitutorAndEvaluator().substitute(query, varMap);
	}
}
