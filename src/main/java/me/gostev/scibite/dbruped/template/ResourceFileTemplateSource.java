package me.gostev.scibite.dbruped.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Reads templates from a predefined resource file.
 */
@Component("resourceFileSource")
public class ResourceFileTemplateSource implements TemplateSource {
	public static final String resourceFile = "/templates/QuestionTemplates.txt";

	private List<Template> templates = new ArrayList<>();

	public ResourceFileTemplateSource() {
	}

	/**
	 * Loads templates from the resource file.
	 * 
	 * @throws ParserException if the resource file can't be read or have incorrect
	 *                         syntax.
	 */
	@Override
	public void load() {
		try (BufferedReader input = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream(resourceFile), Charset.forName("UTF-8")))) {

			String line;
			TemplateParser parser = new TemplateParser();
			line = input.readLine();
			while (line != null) {
				if (!parser.addLine(line)) {
					templates.add(parser.getTemplate());
					parser = new TemplateParser();
				} else {
					line = input.readLine();
				}
			}

			if (!parser.finished()) {
				// TODO: log message
			} else {
				templates.add(parser.getTemplate());
			}
		} catch (IOException e) {
			throw new ParserException("IO error while reading resource file", e);
		}
	}

	/**
	 * @return a list of loaded templates if successfully loaded.
	 */
	@Override
	public List<Template> getTemplates() {
		return templates;
	}
}
