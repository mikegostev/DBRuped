package me.gostev.scibite.dbruped.resolve;

/**
 * A common interface for services that respond to natural language queries
 * providing some textual data.
 */
public interface QuestionResolver {
	/**
	 * Returns acquired data according to a provided question.
	 * 
	 * @param question        a question to resolve.
	 * @param enablesAIHelper enables/disables usage of AI-based helpers that
	 *                        converts a NL question to a formal query.
	 * @return an {@link Answer} object for the provided query.
	 */
	Answer getAnswer(String question, boolean enableAIHelper);
}
