package me.gostev.scibite.dbruped.controller;

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
			return resolver.getAnswer(question, useChatGPT);
		} catch (Exception e) {
			// TODO: log exception
			return Answer.createInvalid("Internal server error", "");
		}
	}
}
