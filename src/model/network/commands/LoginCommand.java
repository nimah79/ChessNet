package model.network.commands;

import model.User;
import model.network.ClientHandler;
import model.network.responses.LoginResponse;

public class LoginCommand implements Command {

	private static final long serialVersionUID = -8983933997175516656L;
	private String username;
	private String password;

	public LoginCommand(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void handle(ClientHandler clientHandler) {
		User user = clientHandler.getUser(this.username, this.password);
		if (user == null) {
			clientHandler.sendResponse(new LoginResponse(false));
			return;
		}
		clientHandler.sendResponse(new LoginResponse(true, user));
	}

}
