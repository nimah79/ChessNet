package model;

import java.io.Serializable;
import java.util.*;

import helper.PasswordHasher;

public class User implements Serializable {

	private static final long serialVersionUID = -565170801344232017L;
	protected String username;
	protected String password;
	private List<Game> games = new Vector<>();

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean checkPassword(String passwordHash) {
		return password.equals(passwordHash);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = PasswordHasher.generateHash(password);
	}

	public List<Game> getGames() {
		return new ArrayList<>(this.games);
	}

}
