package model.network.responses;

import java.util.*;

import model.User;

public class SearchResponse implements Response {

	private static final long serialVersionUID = 8034760052086661224L;
	public List<User> users;

	public SearchResponse(List<User> users) {
		this.users = new ArrayList<>(users);
	}

}
