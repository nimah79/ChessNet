package model.network.responses;

import model.User;

public class SignUpResponse implements Response {

	private static final long serialVersionUID = 680063956007295369L;
	public boolean successful;
	public User user;

	public SignUpResponse(boolean successful, User user) {
		this(successful);
		this.user = user;
	}

	public SignUpResponse(boolean successful) {
		this.successful = successful;
	}

}
