package model;

import java.util.*;

public class Game {

	public User host;
	public User guest;
	public long startedAt;
	public List<Message> chat;

	public Game(User host, User guest) {
		this.host = host;
		this.guest = guest;
		this.startedAt = System.currentTimeMillis() / 1000;
		this.chat = new Vector<>();
	}

	public User getHost() {
		return this.host;
	}

	public User getGuest() {
		return this.guest;
	}

	public long getStartedAd() {
		return this.startedAt;
	}

	public List<Message> getChat() {
		return new Vector<>(this.chat);
	}

	public void addMessage(Message message) {
		this.chat.add(message);
	}

}
