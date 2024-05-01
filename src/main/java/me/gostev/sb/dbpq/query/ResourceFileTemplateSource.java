package me.gostev.sb.dbpq.query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service("resourceFileSource")
public class ResourceFileTemplateSource implements TemplateSource {
	public static final String resourceFile = "/templates/QuestionTemplates.txt";

	private List<String> rawTemplates = Collections.emptyList();

	public ResourceFileTemplateSource() {
	}

	public void load() throws IOException {
		try (BufferedReader input = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream(resourceFile), Charset.forName("UTF-8")))) {
			rawTemplates = input.lines().collect(Collectors.toList());
		}
	}

	@Override
	public java.util.List<String> getTemplates() {
		return rawTemplates;
	}
}
