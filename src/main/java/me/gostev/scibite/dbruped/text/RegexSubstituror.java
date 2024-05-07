package me.gostev.scibite.dbruped.text;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility service that finds and substitutes place holders with values
 * provided by the specified evaluator.
 */
public class RegexSubstituror {
	private Pattern pattern;

	private Function<String, String> evaluator;

	/**
	 * Constructs a substitutor.
	 * 
	 * @param prefix    a prefix of a place holder.
	 * @param postfix   a postfix of a place holder
	 * @param evaluator a function that converts a name of a place holder to a
	 *                  concrete value.
	 */
	public RegexSubstituror(String prefix, String postfix, Function<String, String> evaluator) {
		this.evaluator = evaluator;

		pattern = Pattern.compile(Pattern.quote(prefix) + "(.*?)" + Pattern.quote(postfix));
	}

	/**
	 * Runs a process of substitution and returns a modified string.
	 * 
	 * @param a string to modify
	 * @return a modified string
	 */
	public String process(String str) {
		Matcher matcher = pattern.matcher(str);

		int lastmatchEnd = 0;
		boolean result = matcher.find();
		if (result) {
			StringBuilder sb = new StringBuilder();
			do {
				sb.append(str.substring(lastmatchEnd, matcher.start()));
				sb.append(evaluator.apply(matcher.group(1)));
				lastmatchEnd = matcher.end();
				result = matcher.find();
			} while (result);
			sb.append(str.substring(lastmatchEnd));
			return sb.toString();
		}
		return str;
	}
}
