package me.gostev.scibite.dbruped.text;

import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.function.Function;

/**
 * In a provided string substitutes place holder like ${VAR} according to a
 * provided name->value map. It is possible to modify a value before actual
 * substitution by a list of predefined functions. The syntax is:
 * ${NAME:function}. Multiple functions can be involved.
 */
public class SimpleSubstitutorAndEvaluator {
	public static final String EXPRESSION_START = "${";
	public static final String EXPRESSION_END = "}";

	private static final Map<String, Function<String, String>> functions = Map.of(
			"space2underscore", s -> s.replaceAll(" ", "_"),
			"date2year", s -> s.substring(2, 4),
			"date2age", SimpleSubstitutorAndEvaluator::date2age);

	public String substitute(String orig, Map<String, String> variables) {
		RegexSubstituror substitutor = new RegexSubstituror(EXPRESSION_START, EXPRESSION_END,
				new SubstAndProcessEvaluator(variables, functions));

		return substitutor.process(orig);
	}

	private static String date2age(String dobStr) {
		return date2age(dobStr, LocalDate.now());
	}

	static String date2age(String dobStr, LocalDate now) {
		try {
			LocalDate dobDate = LocalDate.parse(dobStr);
			return String.valueOf(Period.between(dobDate, now).getYears());
		} catch (Exception e) {
			return "ERROR: invalid date: " + dobStr;
		}
	}
}
