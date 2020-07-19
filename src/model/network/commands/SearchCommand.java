package model.network.commands;

import model.network.ClientHandler;
import model.network.responses.SearchResponse;

public class SearchCommand implements Command {

	private static final long serialVersionUID = -2069220933552710550L;
	private String prefix;

	public SearchCommand(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void handle(ClientHandler clientHandler) {
		clientHandler.sendResponse(
				new SearchResponse(clientHandler.getUsers(this.prefix)));
	}

}
