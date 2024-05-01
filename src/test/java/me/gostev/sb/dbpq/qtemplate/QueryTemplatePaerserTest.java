package me.gostev.sb.dbpq.qtemplate;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

public class QueryTemplatePaerserTest {
	@Test
	void singleLineTemplateTest() {
		QueryTemplateParser parser = new QueryTemplateParserImpl(new ByteArrayInputStream(
				"What is the ${birth name#PREDICATE=dbp:birthname|dbp:birthName|dbo:birthName|dbo:birthname} of ${*#RESOURCE}"
						.getBytes()),
				"UTF-8");
		parser.parse();
	}
}
