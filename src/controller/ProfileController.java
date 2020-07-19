package controller;

import java.io.IOException;

import db.DB;
import helper.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.network.commands.ChangePasswordCommand;
import model.network.responses.Response;

public class ProfileController implements Controller {

	@FXML
	private PasswordField passwordField;

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

	public void changePassword(ActionEvent actionEvent) throws IOException {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					if (passwordField == null) {
						return;
					}
					String password = passwordField.getText();
					DB.oos.writeObject(new ChangePasswordCommand(
							DB.user.getUsername(), password));
					DB.user.setPassword(password);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void handleResponse(Response response) {
	}

}