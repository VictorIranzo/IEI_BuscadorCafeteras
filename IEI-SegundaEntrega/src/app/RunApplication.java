package app;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RunApplication extends Application{
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewApp.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		
		ControllerViewApp controller = loader.<ControllerViewApp>getController();
		controller.initializeLayout();
		
		stage.setScene(scene);		
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
		
	}
}
