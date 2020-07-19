package controller;

import helper.ResponseHandler;
import model.network.responses.Response;

public class HistoryController implements Controller {

	public void initialize() {
		ResponseHandler.controller = this;
	}

	@Override
	public void handleResponse(Response response) {
		// TODO
	}

}
