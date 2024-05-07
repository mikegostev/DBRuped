package me.gostev.scibite.dbruped.template;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/** Represents one of NL questions of a template */
public class Question {
	/** Original string to build a regex pattern */
	private String patternString;
	/** A regex pattern compiled from the pattern string */
	private Pattern pattern;
	/** A list of variable named captured from the correspondent NL question */
	private List<String> variables;

	public static class Builder {
		private StringBuilder patternStringBuilder = new StringBuilder();
		private List<String> variables = new ArrayList<>();

		public void addPart(String part) {
			patternStringBuilder.append(Pattern.quote(part));
		}

		public void addVariable(String varName) {
			variables.add(varName);
			patternStringBuilder.append("(.*?)");
		}

		public Question build() {
			return new Question(patternStringBuilder.toString(), variables);
		}

	}

	public static Builder builder() {
		return new Builder();
	}

	private Question(String patternString, List<String> variables) {
		this.patternString = patternString;
		this.variables = variables;
		this.pattern = Pattern.compile(patternString);
	}

	public Pattern getPattern() {
		return pattern;
	}

	public List<String> getVariables() {
		return variables;
	}

	public String getPatternString() {
		return patternString;
	}
}