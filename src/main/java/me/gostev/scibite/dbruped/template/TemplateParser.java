package me.gostev.scibite.dbruped.template;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.gostev.scibite.dbruped.util.StringUtils;

/**
 * Reads and parses template file or other source in line by line manner and
 * holds a parsed template. You need to instantiate the new parser to read
 * another template. Parsers can't be reused.
 */
public class TemplateParser {
	private static enum Section {
		BEFORE, QUESTION, QUERY, RETURN, AFTER
	}

	private Section section = Section.BEFORE;

	private static final String QUESTION_PREFIX = "#QUESTION:";
	private static final String QUERY_PREFIX = "#QUERY:";
	private static final String RETURN_PREFIX = "#RETURN:";
	private static final Pattern RETURN_WITH_QUALIFIER_PATTERN = Pattern.compile("#RETURN\\[(\\p{Alpha}*?)\\]:");

	private static final Pattern variablePattern = Pattern.compile("\\{(\\p{Alpha}\\w*?)\\}");

	private List<Question> questions = new ArrayList<>();
	private StringBuilder query = new StringBuilder();
	private Map<String, String> returnExpressions = new LinkedHashMap<>();

	/**
	 * Parses another line of a template description.
	 * 
	 * @param line a string to parse
	 * @return true if this line was accepted as a part of the current template and
	 *         false if this line is unrelated and no more line are required.
	 * @throws ParserException if template syntax is violated in this line.
	 */
	public boolean addLine(String line) throws ParserException {
		if (section == Section.AFTER) {
			throw new ParserException("Template parsing has finished. No extra lines are required");
		}

		line = line.trim();

		// Comment lines
		if (line.startsWith("##")) {
			return true;
		}

		if (section == Section.BEFORE) {
			if (line.length() > 0) {
				if (line.startsWith(QUESTION_PREFIX)) {
					line = line.substring(QUESTION_PREFIX.length()).trim();
					line = StringUtils.removeExtraSpaces(line);
					questions.add(extractVarsAndCreatePattern(line));
				}
				section = Section.QUESTION;
			}

		} else if (section == Section.QUESTION) {
			if (line.length() > 0) {
				if (line.startsWith(QUESTION_PREFIX)) {
					line = line.substring(QUESTION_PREFIX.length()).trim();
					line = StringUtils.removeExtraSpaces(line);
					questions.add(extractVarsAndCreatePattern(line));
				} else if (line.startsWith(QUERY_PREFIX)) {
					section = Section.QUERY;
					line = line.substring(QUERY_PREFIX.length()).trim();
					if (line.length() > 0) {
						query.append(line).append("\n");
					}
				}
			}
		} else if (section == Section.QUERY || section == Section.RETURN) {
			if (line.startsWith(RETURN_PREFIX)) {
				returnExpressions.put("", line.substring(RETURN_PREFIX.length()).trim());
				section = Section.RETURN;
				return true;
			} else {
				Matcher mtch = RETURN_WITH_QUALIFIER_PATTERN.matcher(line);

				if (mtch.find() && mtch.start() == 0) {
					section = Section.RETURN;
					String qualifier = mtch.group(1);
					returnExpressions.put(qualifier, line.substring(mtch.end()).trim());
					// TODO Check that no repeating qualifiers are in the list of return
					// expressions.
				} else {
					if (section == Section.QUERY) {
						query.append(line).append("\n");
					} else {
						// We have met some non-RETURN statement being in RETURN block. It means the end
						// of the template
						section = Section.AFTER;
						return false;
					}
				}
			}
		}

		return true;
	}

	private Question extractVarsAndCreatePattern(String line) {
		Question.Builder qBuilder = Question.builder();
		int pos = 0;
		int len = line.length();
		while (pos < len) {
			int dollPos = line.indexOf('$', pos);

			if (dollPos < 0 || dollPos == len - 1) {
				qBuilder.addPart(line.substring(pos));
				break;
			} else {
				if (line.charAt(dollPos + 1) == '$') {
					qBuilder.addPart(line.substring(pos, dollPos + 1));
					pos = dollPos + 2;
				} else {
					Matcher matcher = variablePattern.matcher(line);
					if (matcher.find(dollPos + 1) && matcher.start() == dollPos + 1) {
						qBuilder.addPart(line.substring(pos, dollPos));
						qBuilder.addVariable(matcher.group(1));
						pos = matcher.end();
					} else {
						qBuilder.addPart(line.substring(pos, dollPos + 1));
						pos = dollPos + 1;
					}
				}
			}
		}

		return qBuilder.build();
	}

	/**
	 * @return a parsed template if parsing has been finished. (See
	 *         {@link TemplateParser#finished()})
	 */
	public Template getTemplate() {
		return Template.build(questions, query.toString(), returnExpressions);
	}

	/**
	 * @return true if all necessary lines are read and a template as ready to be
	 *         consumed.
	 */
	public boolean finished() {
		return section == Section.AFTER || section == Section.RETURN;
	}

}
