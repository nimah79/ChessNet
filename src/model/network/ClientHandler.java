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

	public List<String> getUsernamesStartingWith(String prefix) {
		return this.server.getUsernamesStaringWith(prefix);
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
