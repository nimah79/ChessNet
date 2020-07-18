package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.net.Socket;

import db.DB;
import helper.InputValidator;
import helper.SceneLoader;

public class ConnectController {

	@FXML
	private TextField hostField;

	@FXML
	private TextField portField;

	@FXML
	private Button connectBtn;

	@FXML
	private Label errorLbl;

	private Runnable getSubmitHandler() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String host = hostField.getText();
				if (host.isEmpty()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							errorLbl.setText("No host provided.");
							errorLbl.setVisible(true);
						}
					});
					return;
				}
				if (portField.getText().isEmpty()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							errorLbl.setText("No port provided.");
							errorLbl.setVisible(true);
						}
					});
					return;
				}
				if (!InputValidator.isNumeric(portField.getText())) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							errorLbl.setText("Invalid port.");
							errorLbl.setVisible(true);
						}
					});
					return;
				}
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						errorLbl.setText("Connecting...");
						errorLbl.setVisible(true);
					}
				});
				int port = Integer.parseInt(portField.getText());
				try {
					DB.socket = new Socket(host, port);
					DB.oos = new ObjectOutputStream(
							DB.socket.getOutputStream());
					DB.ois = new ObjectInputStream(DB.socket.getInputStream());
				} catch (Exception e) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							errorLbl.setText("Couldn't connect to server.");
							errorLbl.setVisible(true);
						}
					});
					return;
				}
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							SceneLoader.show("Auth");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}
		};
		return runnable;
	}

	public void connect(ActionEvent actionEvent) throws IOException {
		(new Thread(this.getSubmitHandler())).start();
	}

}
