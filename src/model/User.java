package model;

import java.io.Serializable;
import java.util.*;

import helper.PasswordHasher;

public class User implements Serializable {

	private static final long serialVersionUID = -565170801344232017L;
	protected String username;
	protected String password;
	private List<Game> games = new Vector<>();
	private Game currentGame;
	private int winsCount = 0;
	private int losesCount = 0;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setPassword(String password) {
		this.password = PasswordHasher.generateHash(password);
	}

	public boolean checkPassword(String passwordHash) {
		return this.password.equals(PasswordHasher.generateHash(passwordHash));
	}

	public List<Game> getGames() {
		return new ArrayList<>(this.games);
	}

	public void setCurrentGame(Game game) {
		this.games.add(game);
		this.currentGame = game;
	}

	public boolean isInGame() {
		return this.currentGame != null;
	}

	public Game getCurrentGame() {
		return this.currentGame;
	}

	public void incrementWinsCount() {
		this.winsCount++;
	}

	public void incrementLosesCount() {
		this.losesCount++;
	}

	public int getWinsCount() {
		return this.winsCount;
	}

	public int getLosesCount() {
		return this.losesCount;
	}

	@Override
	public String toString() {
		return this.username;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof User)) {
			return false;
		}
		return ((User) o).getUsername().equals(this.getUsername());
	}

}
