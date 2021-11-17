package application;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {

	/**
	 * Initialization function. Creates the editor window and loads css file.
	 * @param primaryStage stage
	 * @throws Exception exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("../layout/Editor.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add("/layout/application.css");

		primaryStage.setScene(scene);
		primaryStage.setTitle("Editor");
		primaryStage.setMaximized(true);
		primaryStage.getIcons().add(new Image("/layout/app_logo_neu.png"));
		primaryStage.show();
		
	}

	/**
	 * Main function.
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		try{
			launch(args);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
