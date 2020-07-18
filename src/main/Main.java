package main;

import javafx.application.Application;
import javafx.stage.Stage;
import helper.SceneLoader;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		SceneLoader.initStage(primaryStage, "ChessNet");
		SceneLoader.setBasePath("../view");
		SceneLoader.show("Connect");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
