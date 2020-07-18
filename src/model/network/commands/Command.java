package model.network.commands;

import java.io.Serializable;

import model.network.ClientHandler;

public interface Command extends Serializable {

	public void handle(ClientHandler clientHandler);

}
