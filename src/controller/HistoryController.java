package controller;

import java.io.IOException;

import db.DB;
import helper.ResponseHandler;
import helper.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Game;
import model.network.responses.Response;

public class HistoryController implements Controller {

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

	@FXML
	private TableView<Game> historyTbl;

	public void initialize() {
		ResponseHandler.controller = this;
		historyTbl.getItems().clear();
		historyTbl.getItems().addAll(DB.user.getGames());
		historyTbl.refresh();
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
	}

}
