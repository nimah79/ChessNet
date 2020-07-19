package model.network.commands;

import model.User;
import model.network.ClientHandler;
import model.network.responses.InvitationResponse;

public class RequestCommand implements Command {

	private static final long serialVersionUID = 7031870746609346380L;
	public User inviter;
	public User inviting;

	public RequestCommand(User inviter, User inviting) {
		this.inviter = inviter;
		this.inviting = inviting;
	}

	@Override
	public void handle(ClientHandler clientHandler) {
		ClientHandler invitingClientHandler = clientHandler
				.getClientHandler(this.inviting.getUsername());
		if (invitingClientHandler == null) {
			return;
		}
		invitingClientHandler
				.sendResponse(new InvitationResponse(this.inviter));
	}

}
