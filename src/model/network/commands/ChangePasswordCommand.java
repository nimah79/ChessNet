package model.network.commands;

import model.network.ClientHandler;

public class ChangePasswordCommand implements Command {

	private static final long serialVersionUID = 1654644775336729171L;
	private String username;
	private String password;

	public ChangePasswordCommand(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void handle(ClientHandler clientHandler) {
		System.out.println(username);
		System.out.println(password);
		clientHandler.changePassword(username, password);
	}

}
