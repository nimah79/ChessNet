package model.network.responses;

import java.util.*;

import model.User;

public class ScoreboardResponse implements Response {

	private static final long serialVersionUID = -5374624602783310658L;
	public List<User> users;

	public ScoreboardResponse(List<User> users) {
		this.users = new ArrayList<>(users);
	}

}
