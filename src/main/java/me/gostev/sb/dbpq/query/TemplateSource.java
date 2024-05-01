package me.gostev.sb.dbpq.query;

import java.util.List;

public interface TemplateSource {
	List<String> getTemplates();

	void load() throws Exception;
}
