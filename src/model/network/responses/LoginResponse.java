package model.network.responses;

import model.User;

public class LoginResponse implements Response {

	private static final long serialVersionUID = 680063956007295369L;
	public boolean successful;
	public User user;

	public LoginResponse(boolean successful, User user) {
		this(successful);
		this.user = user;
	}

	public LoginResponse(boolean successful) {
		this.successful = successful;
	}

}
