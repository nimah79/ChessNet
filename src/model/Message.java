package model;

public class Message {

	private User sender;
	private String message;

	public Message(User sender, String message) {
		this.sender = sender;
		this.message = message;
	}

	public User getSender() {
		return this.sender;
	}

	public String getMessage() {
		return this.message;
	}

	@Override
	public String toString() {
		return this.sender.getUsername() + ": " + this.message;
	}

}
