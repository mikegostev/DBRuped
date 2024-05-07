package me.gostev.scibite.dbruped.chatgpt;

/**
 * A container class that corresponds ChatGPT response JSON
 */
public class Choice {
	public static class Message {
		private String role;
		private String content;

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}

	private int index;
	private Message message;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
