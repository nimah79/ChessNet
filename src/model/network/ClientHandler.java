package model.network;

import java.io.*;
import java.net.Socket;
import java.util.*;

import model.User;
import model.network.commands.Command;

public class ClientHandler implements Runnable {

	private User user;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Server server;

	public ClientHandler(Server server, Socket socket) {
		try {
			this.ois = new ObjectInputStream(socket.getInputStream());
			this.oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Couldn't start client handler.");
			System.exit(0);
		}
		this.server = server;
	}

	@Override
	public void run() {
		boolean clientConnected = true;
		try {
			while (clientConnected) {
				Command command = (Command) this.ois.readObject();
				command.handle(this);
			}
		} catch (ClassNotFoundException | IOException e) {
			clientConnected = false;
			this.logout();
		}
	}

	public void sendResponse(Object response) {
		try {
			this.oos.writeObject(response);
		} catch (IOException e) {
			System.err.println("Failed to send response.");
		}
	}

	public List<User> getUsers(String prefix) {
		return this.server.getUsers(prefix);
	}

	public List<User> getScoreboard() {
		List<User> scoreboard = Arrays.asList(this.server.getUsers("").stream()
				.sorted((u1, u2) -> u1.getWinsCount() - u2.getWinsCount())
				.limit(10).toArray(User[]::new));
		if (!this.loggedIn() || scoreboard.contains(this.user)) {
			return scoreboard;
		}
		scoreboard.add(this.user);
		scoreboard = Arrays.asList(scoreboard.stream()
				.sorted((u1, u2) -> u1.getWinsCount() - u2.getWinsCount())
				.toArray(User[]::new));
		return scoreboard;
	}

	public User getUser(String username, String password) {
		return this.server.getUser(username, password);
	}

	public User createUser(String username, String password) {
		return this.server.createUser(username, password);
	}

	private boolean loggedIn() {
		return this.user != null;
	}

	private void logout() {
		if (!this.loggedIn()) {
			return;
		}
		server.removeClient(this.user.getUsername());
		this.user = null;
	}

}
