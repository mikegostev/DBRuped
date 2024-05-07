package me.gostev.scibite.dbruped.chatgpt;

import java.util.List;

/**
 * A container class that corresponds ChatGPT response JSON
 */
public class Response {
	private List<Choice> choices;

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}
}
