package me.gostev.sb.dbpq.qtemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import me.gostev.sb.dbpq.query.NLQueryBuilder;
import me.gostev.sb.dbpq.query.Query;
import me.gostev.sb.dbpq.query.TemplateSource;

public class TemplateQueryBuilder implements NLQueryBuilder {
	@Autowired
	@Qualifier("resourceFileSource")
	private TemplateSource source;
	private boolean isValid = true;
	
	private static Query INVALID_QUERY;
	
	public TemplateQueryBuilder() {
		try {
			source.load();
		} catch (Exception e) {
			isValid = false;
			return;
		}
		
		source.getTemplates().stream();
	}
	
	private static Query createInvalidQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query buildQuery(String question) {
		if( !isValid ) {
			return createInvalidQuery();
		}
		return null;
	}

}
