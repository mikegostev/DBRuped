package me.gostev.scibite.dbruped.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import me.gostev.scibite.dbruped.template.TemplateParser;

class TemplateParserTest {

	private static String testTemplate1 = """
			#QUESTION: How old is ${PERSON}?
			#QUERY:
			prefix dbr: <http://dbpedia.org/resource/>
			prefix dbp: <http://dbpedia.org/property/>
			prefix dbo: <http://dbpedia.org/ontology/>

			SELECT  ?dob
			FROM <http://dbpedia.org>
			WHERE {
			       dbr:${PERSON} dbo:birthDate  ?dob .
			  } LIMIT 1
			#RETURN: ${1}
			""";

	private static int testTemplate1LineCount = countLines(testTemplate1);

	private static int countLines(String text) {
		int count = 0;
		BufferedReader reader = new BufferedReader(new StringReader(testTemplate1));

		try {
			while (reader.readLine() != null) {
				count++;
			}
		} catch (IOException e) {
			// Should not happen
		}

		return count;
	}

	@Test
	void testSingleTemplate() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(testTemplate1));
		int n = 0;

		TemplateParser parser = new TemplateParser();
		String line;
		while ((line = reader.readLine()) != null) {
			if (parser.addLine(line)) {
				n++;
			} else {
				break;
			}
		}

		assertEquals(testTemplate1LineCount, n);
	}

}
