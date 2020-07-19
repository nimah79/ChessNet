package controller;

import java.io.IOException;

import db.DB;
import helper.ResponseHandler;
import helper.SceneLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.network.commands.SearchCommand;
import model.network.responses.*;
import widgets.AutoCompleteTextField;

public class DashboardController implements Controller {

	@FXML
	private Label welcomeLbl;

	@FXML
	private AutoCompleteTextField searchField;

	public void initialize() {
		ResponseHandler.controller = this;
		this.startSearchHandler();
		this.welcomeLbl.setText("Welcome, " + DB.user.getUsername() + '!');
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

	public void handleResponse(Response response) {
		if (response instanceof SearchResponse) {
			SearchResponse searchResponse = (SearchResponse) response;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					searchField.getEntries().addAll(searchResponse.usernames);
				}
			});
		}
	}

	public void logout(ActionEvent actionEvent) throws IOException {
		DB.user = null;
		SceneLoader.show("Auth");
	}

	private void startSearchHandler() {
		searchField.textProperty()
				.addListener((observable, oldValue, newValue) -> {
					Runnable runnable = new Runnable() {
						@Override
						public void run() {
							if (searchField.getText().isEmpty()) {
								return;
							}
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									try {
										DB.oos.writeObject(new SearchCommand(
												searchField.getText()));
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							});
						}
					};
					(new Thread(runnable)).start();
				});
	}

}
