package net.lynchTech.HAD;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Login extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("LogIn.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("HAD   Log In");
			primaryStage.show();
			primaryStage.setResizable(false);
			
		}
		catch (Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Enternal Error");
			alert.setContentText("You must Reinstall HAD \n Resources missing");

			alert.showAndWait();
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
