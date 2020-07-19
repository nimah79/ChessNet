package controller;

import java.io.IOException;
import java.util.*;

import db.DB;
import helper.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import model.User;
import model.network.commands.*;
import model.network.responses.*;
import widgets.AutoCompleteTextField;

public class DashboardController implements Controller {

	@FXML
	private Label welcomeLbl;

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
	private AutoCompleteTextField searchField;

	@FXML
	private Button requestBtn;

	@FXML
	private TableView<User> usersTbl;

	@FXML
	private TableView<User> scoreboardTbl;

	public void initialize() {
		ResponseHandler.controller = this;
		this.startSearchHandler();
		if (this.welcomeLbl != null) {
			this.welcomeLbl.setText("Welcome, " + DB.user.getUsername() + '!');
		}
		this.sendSearchCommand("");
		this.sendScoreboardCommand();
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

	public void requestGame(ActionEvent actionEvent) throws IOException {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (usersTbl == null) {
					return;
				}
				User inviting = usersTbl.getSelectionModel().getSelectedItem();
				if (inviting == null) {
					return;
				}
				try {
					DB.oos.writeObject(new RequestCommand(DB.user, inviting));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
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
					if (usersTbl != null) {
						usersTbl.getItems().clear();
						usersTbl.getItems().addAll(users);
						usersTbl.refresh();
					}
				}
			});
		} else if (response instanceof ScoreboardResponse) {
			ScoreboardResponse scoreboardResponse = (ScoreboardResponse) response;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if (scoreboardTbl == null) {
						return;
					}
					scoreboardTbl.getItems().clear();
					scoreboardTbl.getItems().addAll(scoreboardResponse.users);
					scoreboardTbl.refresh();
				}
			});
		} else if (response instanceof InvitationResponse) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					InvitationResponse invitationResponse = (InvitationResponse) response;
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Game invitation");
					alert.setHeaderText("Game invitation");
					alert.setContentText(invitationResponse.user.getUsername()
							+ " has sent you a game request. Do you want to play with him?");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						// TODO
					} else {
						// TODO
					}
				}
			});
		}
	}

	private void startSearchHandler() {
		if (searchField == null) {
			return;
		}
		searchField.textProperty()
				.addListener((observable, oldValue, newValue) -> {
					Runnable runnable = new Runnable() {
						@Override
						public void run() {
							if (searchField == null) {
								return;
							}
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
