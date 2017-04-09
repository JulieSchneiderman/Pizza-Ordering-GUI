package application;
/*
 * A starting program for the GUI.  Links are made to the *.css and the *.fxml files.	
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = (VBox)FXMLLoader.load(Main.class.getResource("..\\Assignment5.fxml"));
			Scene scene = new Scene(root, 800, 480);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pizza Order System");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	} // end start
	
	public static void main(String[] args) {
		launch(args);
	} // end main
	
} // end class Main
