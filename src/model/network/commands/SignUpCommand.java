package model.network.commands;

import model.User;
import model.network.ClientHandler;
import model.network.responses.*;

public class SignUpCommand implements Command {

	private static final long serialVersionUID = -8983933997175516656L;
	private String username;
	private String password;

	public SignUpCommand(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void handle(ClientHandler clientHandler) {
		User user = clientHandler.createUser(this.username, this.password);
		if (user == null) {
			clientHandler.sendResponse(new SignUpResponse(false));
			return;
		}
		clientHandler.setUser(user);
		clientHandler.addClientHandler();
		clientHandler.sendResponse(new SignUpResponse(true, user));
	}

}
