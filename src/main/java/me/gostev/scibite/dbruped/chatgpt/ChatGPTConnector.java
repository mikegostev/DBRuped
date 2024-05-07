package me.gostev.scibite.dbruped.chatgpt;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This class implements communications with ChatGPT through its API.
 */
public class ChatGPTConnector {
	public static String CHATGPT_API_URL = "https://api.openai.com/v1/chat/completions";
	public static String CHATGPT_API_KEY = System.getenv("CHATGPT_API_KEY");

	/**
	 * Send a prompt to ChatGPT and returns it's response.
	 * 
	 * @param prompt a prompt to respond
	 * @return a string (possibly empty) with ChatGPT response
	 */
	public String sendRequest(String prompt) {

		WebClient webClient = WebClient.builder()
				.baseUrl(CHATGPT_API_URL)
				.defaultHeader("Content-Type", "application/json")
				.defaultHeader("Authorization", "Bearer " + CHATGPT_API_KEY)
				.build();

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();

		ObjectNode queryNode = mapper.createObjectNode();
		queryNode.put("role", "user");
		queryNode.put("content", prompt);

		ArrayNode msgArray = mapper.createArrayNode();
		msgArray.add(queryNode);

		// ObjectNode requestNode = mapper.createObjectNode();
		rootNode.put("model", "gpt-3.5-turbo");
		rootNode.set("messages", msgArray);
		rootNode.put("temperature", 0.1);

		String jsonString;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
		} catch (JsonProcessingException e) {
			return "ERROR: " + e.getMessage();
		}

		Response response = webClient.post()
				.body(BodyInserters.fromValue(jsonString))
				.retrieve()
				.bodyToMono(Response.class)
				.block();

		if (response.getChoices() != null && response.getChoices().size() > 0
				&& response.getChoices().get(0).getMessage() != null) {
			String reply = response.getChoices().get(0).getMessage().getContent();

			return reply != null ? reply : "";
		}

		return "";
	}
}
