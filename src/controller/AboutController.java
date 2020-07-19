package controller;

import java.io.IOException;

import db.DB;
import helper.ResponseHandler;
import helper.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.network.responses.Response;

public class AboutController implements Controller {
	
	@FXML
	private Button dashboardBtn;

	@FXML
	private Button historyBtn;

	@FXML
	private Button profileBtn;

	@FXML
	private Button aboutBtn;

	@FXML
	private Button logoutBtn;

	public void initialize() {
		ResponseHandler.controller = this;
	}
	
	public void showDashboardPage(ActionEvent actionEvent) throws IOException {
		SceneLoader.show("Dashboard");
	}

	public void showHistoryPage(ActionEvent actionEvent) throws IOException {
		SceneLoader.show("History");
	}

	public void showProfilePage(ActionEvent actionEvent) throws IOException {
		SceneLoader.show("Profile");
	}

	public void showAboutPage(ActionEvent actionEvent) throws IOException {
		SceneLoader.show("About");
	}

	public void logout(ActionEvent actionEvent) throws IOException {
		DB.user = null;
		SceneLoader.show("Auth");
	}

	@Override
	public void handleResponse(Response response) {
		//
	}

}