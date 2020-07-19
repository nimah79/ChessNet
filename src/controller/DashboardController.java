package controller;

import java.io.IOException;
import java.util.*;

import db.DB;
import helper.*;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.User;
import model.network.commands.*;
import model.network.responses.*;
import widgets.AutoCompleteTextField;

public class DashboardController implements Controller {

	ObservableList<User> users = FXCollections.<User>observableArrayList();
	ObservableList<User> scoreboard = FXCollections.<User>observableArrayList();

	@FXML
	private Label welcomeLbl;

	@FXML
	private Button historyBtn;

	@FXML
	private Button profileBtn;

	@FXML
	private Button aboutBtn;

	@FXML
	private Button logoutBtn;

	@FXML
	private AutoCompleteTextField searchField;

	@FXML
	private TableView<User> usersTbl;

	@FXML
	private TableView<User> scoreboardTbl;

	public void initialize() {
		ResponseHandler.controller = this;
		usersTbl.setItems(this.users);
		scoreboardTbl.setItems(this.users);
		this.startSearchHandler();
		this.welcomeLbl.setText("Welcome, " + DB.user.getUsername() + '!');
		this.sendSearchCommand("");
		this.sendScoreboardCommand();
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
					List<User> users = searchResponse.users;
					List<String> usernames = new ArrayList<>();
					for (User user : users) {
						usernames.add(user.getUsername());
					}
					searchField.getEntries().addAll(usernames);
					usersTbl.getItems().clear();
					usersTbl.getItems().addAll(users);
				}
			});
		} else if (response instanceof ScoreboardResponse) {
			ScoreboardResponse scoreboardResponse = (ScoreboardResponse) response;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					scoreboardTbl.getItems().clear();
					scoreboardTbl.getItems().addAll(scoreboardResponse.users);
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
							String keyword = searchField.getText();
							if (keyword == null) {
								keyword = "";
							}
							sendSearchCommand(keyword);
						}
					};
					(new Thread(runnable)).start();
				});
	}

	private void sendSearchCommand(String prefix) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					DB.oos.writeObject(new SearchCommand(prefix));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void sendScoreboardCommand() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					DB.oos.writeObject(new ScoreboardCommand());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
