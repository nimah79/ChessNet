package model.network.responses;

import model.User;

public class InvitationResponse implements Response {

	private static final long serialVersionUID = -8249831093119224662L;
	public User user;

	public InvitationResponse(User user) {
		this.user = user;
	}

}
