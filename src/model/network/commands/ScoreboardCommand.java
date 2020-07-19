package model.network.commands;

import model.network.ClientHandler;
import model.network.responses.ScoreboardResponse;

public class ScoreboardCommand implements Command {

	private static final long serialVersionUID = -3655831423169965180L;

	@Override
	public void handle(ClientHandler clientHandler) {
		clientHandler.sendResponse(
				new ScoreboardResponse(clientHandler.getScoreboard()));
	}

}
