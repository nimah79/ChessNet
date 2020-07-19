package controller;

import helper.ResponseHandler;
import model.network.responses.Response;

public class AboutController implements Controller {

	public void initialize() {
		ResponseHandler.controller = this;
	}

	@Override
	public void handleResponse(Response response) {
		// TODO
	}

}