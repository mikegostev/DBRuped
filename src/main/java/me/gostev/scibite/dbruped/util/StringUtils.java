package me.gostev.scibite.dbruped.util;

/**
 * An utility class for string transformations.
 */
public final class StringUtils {
	/**
	 * Removes extra spaces between words in a sentence.
	 * 
	 * @param line a sentence to normalise.
	 * @return a normalised string.
	 */
	public static String removeExtraSpaces(String line) {
		return line.replaceAll("\\s+", " ").trim();
	}

	public static String normalizeQuestion(String question) {
		String[] parts = question.split("[\s\\.,\\?]");

		StringBuilder sb = new StringBuilder();

		String separator = " ";
		for (String part : parts) {
			if (part.length() > 0) {
				sb.append(part).append(separator);
			}
		}

		if (sb.length() == 0) {
			sb.append("?");
		} else {
			sb.setCharAt(sb.length() - 1, '?');
		}

		return sb.toString();
	}
}
