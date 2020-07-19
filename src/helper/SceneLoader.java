package helper;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.*;

public class SceneLoader {

	private static Stage stage;
	private static String basePath;

	public static void initStage(Stage primaryStage, String windowTitle) {
		initStage(primaryStage);
		setWindowTitle(windowTitle);
	}

	public static void initStage(Stage primaryStage) {
		stage = primaryStage;
	}

	public static void setWindowTitle(String title) {
		stage.setTitle(title);
	}

	public static void show(String name) throws IOException {
		Parent root = FXMLLoader.load(
				SceneLoader.class.getResource(basePath + "/" + name + ".fxml"));
		stage.setScene(new Scene(root));
		stage.show();
	}

	public static void setBasePath(String basePath) {
		SceneLoader.basePath = basePath;
	}
}
