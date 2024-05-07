package me.gostev.scibite.dbruped.text;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Converts a string representing a variable name to its corresponding value
 * according to the provided map. It is possible to modify final value calling a
 * string->string conversion function that syntax is "name:function".
 */
public class SubstAndProcessEvaluator implements Function<String, String> {
	public static final String FUNCTION_SEPARATOR = ":";

	private Map<String, String> variables;

	private Map<String, Function<String, String>> functions;

	/**
	 * Constructs an evaluator.
	 * 
	 * @param variables a map of variables name->value
	 * @param functions a map of functions function name->function implementation
	 */
	public SubstAndProcessEvaluator(Map<String, String> variables, Map<String, Function<String, String>> functions) {
		this.variables = variables;
		this.functions = functions;
	}

	@Override
	public String apply(String expr) {
		expr = expr.trim();
		int colpos = expr.indexOf(FUNCTION_SEPARATOR);

		String varName = colpos < 0 ? expr : expr.substring(0, colpos);

		String varVal = variables.getOrDefault(varName, "");

		if (colpos < 0) {
			return varVal;
		}

		Map<String, Function<String, String>> processors = new LinkedHashMap<String, Function<String, String>>();

		while (colpos > 0) {
			int nextColPos = expr.indexOf(FUNCTION_SEPARATOR, colpos + 1);
			String fName;
			if (nextColPos < 0) {
				fName = expr.substring(colpos + 1);
			} else {
				fName = expr.substring(colpos + 1, nextColPos);
			}

			Function<String, String> proc = functions.get(fName);
			if (proc == null) {
				return "ERROR: unknown function: " + fName;
			}

			processors.put(fName, proc);
			colpos = nextColPos;
		}

		for (Map.Entry<String, Function<String, String>> proc : processors.entrySet()) {
			try {
				varVal = proc.getValue().apply(varVal);
			} catch (Exception e) {
				varVal = String.format("ERROR: evaluating function '%s' with parameter '%s'", proc.getKey(), varVal);
				break;
			}
		}

		return varVal;
	}

}
