package demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Demo extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("demo.fxml"));
		primaryStage.setTitle("Projet M2105");
		primaryStage.setScene(new Scene(root, 300, 600));
		primaryStage.setWidth(800.0);
		primaryStage.setHeight(800.0);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
