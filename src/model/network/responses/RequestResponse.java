package model.network.responses;

import model.Game;

public class RequestResponse implements Response {

	private static final long serialVersionUID = -8461512140044291406L;
	public Game game;

	public RequestResponse(Game game) {
		this.game = game;
	}

}
