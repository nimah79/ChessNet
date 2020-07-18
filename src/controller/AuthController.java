package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.network.commands.*;
import model.network.responses.*;
import helper.*;

import java.io.IOException;

import db.DB;

class AuthControllerLoginEventHandler implements EventHandler<KeyEvent> {

	AuthController controller;

	public AuthControllerLoginEventHandler(AuthController controller) {
		this.controller = controller;
	}

	@Override
	public void handle(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ENTER) {
			this.controller.login();
		}
	}

}

public class AuthController {

	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button loginBtn;

	@FXML
	private Button signUpBtn;

	@FXML
	private Label errorLbl;

	public void initialize() {
		usernameField
				.setOnKeyPressed(new AuthControllerLoginEventHandler(this));
		passwordField
				.setOnKeyPressed(new AuthControllerLoginEventHandler(this));
	}

	public void login(ActionEvent actionEvent) throws IOException {
		this.login();
	}

	public void signUp(ActionEvent actionEvent) throws IOException {
		this.signUp();
	}

	void login() {
		(new Thread(this.getLoginHandler())).start();
	}

	private Runnable getLoginHandler() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String username = usernameField.getText();
				if (username.isEmpty()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							errorLbl.setText("No username provided.");
							errorLbl.setVisible(true);
						}
					});
					return;
				}
				if (passwordField.getText().isEmpty()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							errorLbl.setText("No password provided.");
							errorLbl.setVisible(true);
						}
					});
					return;
				}
				if (!InputValidator.isAlphaNumeric(usernameField.getText())) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							errorLbl.setText("Invalid username.");
							errorLbl.setVisible(true);
						}
					});
					return;
				}
				if (!InputValidator.isAlphaNumeric(passwordField.getText())
						|| passwordField.getText().length() < 8) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							errorLbl.setText("Invalid password.");
							errorLbl.setVisible(true);
						}
					});
					return;
				}
				String password = passwordField.getText();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						errorLbl.setText("Logging in...");
						errorLbl.setVisible(true);
					}
				});
				try {
					DB.oos.writeObject(new LoginCommand(username, password));
					LoginResponse response = (LoginResponse) DB.ois
							.readObject();
					if (!response.successful) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								errorLbl.setText("Invalid credentials.");
								errorLbl.setVisible(true);
							}
						});
						return;
					}
					DB.user = response.user;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								SceneLoader.show("Dashboard");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							errorLbl.setText("Couldn't connect to server.");
							errorLbl.setVisible(true);
						}
					});
				}
			}
		};
		return runnable;
	}

	private void signUp() {
		(new Thread(this.getSignUpHandler())).start();
	}

	private Runnable getSignUpHandler() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String username = usernameField.getText();
				if (username.isEmpty()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							errorLbl.setText("No username provided.");
							errorLbl.setVisible(true);
						}
					});
					return;
				}
				if (passwordField.getText().isEmpty()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							errorLbl.setText("No password provided.");
							errorLbl.setVisible(true);
						}
					});
					return;
				}
				if (!InputValidator.isAlphaNumeric(usernameField.getText())) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							errorLbl.setText("Invalid username.");
							errorLbl.setVisible(true);
						}
					});
					return;
				}
				if (!InputValidator.isAlphaNumeric(passwordField.getText())
						|| passwordField.getText().length() < 8) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							errorLbl.setText("Invalid password.");
							errorLbl.setVisible(true);
						}
					});
					return;
				}
				String password = passwordField.getText();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						errorLbl.setText("Signing up...");
						errorLbl.setVisible(true);
					}
				});
				try {
					DB.oos.writeObject(new SignUpCommand(username, password));
					SignUpResponse response = (SignUpResponse) DB.ois
							.readObject();
					if (!response.successful) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								errorLbl.setText("Username already exists.");
								errorLbl.setVisible(true);
							}
						});
						return;
					}
					DB.user = response.user;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								SceneLoader.show("Dashboard");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							errorLbl.setText("Couldn't connect to server.");
							errorLbl.setVisible(true);
						}
					});
				}
			}
		};
		return runnable;
	}

}
