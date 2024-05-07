package me.gostev.scibite.dbruped.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import me.gostev.scibite.dbruped.resolve.Answer;
import me.gostev.scibite.dbruped.resolve.QuestionResolver;

/**
 * Application controller that serves REST API
 */
@RestController
@RequestMapping("/api")
public class ApiController {
	private static Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	QuestionResolver resolver;

	/**
	 * REST GET method that converts natural language questions to DBpedia data.
	 * 
	 * @param question   a question to resolve.
	 * @param useChatGPT enable/disable help of ChatGPT for building SPARQL queries.
	 * @return Answer object that contains necessary information about received data
	 *         and metadata.
	 */
	@GetMapping("/question")
	public @ResponseBody Answer resolveQuestion(@RequestParam String question,
			@RequestParam(defaultValue = "true") boolean useChatGPT) {
		try {
			logger.debug("Got request: {}", question);

			return resolver.getAnswer(question, useChatGPT);

		} catch (Exception e) {
			logger.error("Error while resolving question: " + e.getMessage(), e);
			return Answer.createInvalid("Internal server error", "");
		}
	}
}
