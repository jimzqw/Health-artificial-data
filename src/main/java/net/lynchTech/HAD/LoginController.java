
package net.lynchTech.HAD;



/**
 *LogIn.fxml Controller Class
 * using buttons and check MAC
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController
{
	
	@FXML
	private Button deidentify;
	
	@FXML
	private Button artificial;
	
	@FXML
	private Button loginBtn;
	
	@FXML
	private Button cancelBtn;
	
	@FXML
	private Button closeBtn;
	
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private CheckBox rememberMe;
	
	private static String getMac()
	{
		InetAddress ip;
		try
		{
			ip = InetAddress.getLocalHost();
			
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++)
			{
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			return sb.toString();
		}
		catch (UnknownHostException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Internet Error");
			alert.setContentText("You must be connected to the internet");

			alert.showAndWait();
			
			e.printStackTrace();
		}
		catch (SocketException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Internet Error");
			alert.setContentText("You must be connected to the internet");

			alert.showAndWait();
			
			e.printStackTrace();
		}
		return null;
	}
	
	@FXML
	void cancel(ActionEvent event)
	{
		// get a handle to the stage
		Stage stage = (Stage) cancelBtn.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	void login(ActionEvent event) 
	{
		
		JAXB j = new JAXB();
		try
		{
			j.read("default.had");
		}
		catch (JAXBException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("File Error");
			alert.setContentText("You must use .had file \n wrong file");

			alert.showAndWait();
			e.printStackTrace();
		}
		String user = username.getText().trim();
		String pass = password.getText().trim();
		String mac=getMac();
		if (mac.equals("00-00-00-00-00"))
		{
			// to do
		}
		
		Stage stage = new Stage();
		
		if(user.equals(j.username))
		{
			if(pass.equals(j.password))
			{
				
					AnchorPane root = null;
					try
					{
						root = FXMLLoader.load(HomeController.class.getResource("ChoseType.fxml"));
					}
					catch (IOException e)
					{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Enternal Error");
						alert.setContentText("You must Reinstall HAD \n Resource missing");

						alert.showAndWait();
						e.printStackTrace();
					}
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
					stage.setTitle("HAD Health");
					stage.setResizable(false);
					cancel(event);
			
			}
			else
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Password Error");
				alert.setHeaderText("Password is not valid");
				alert.setContentText("Please double check the password \n Access Denied");

				alert.showAndWait();
			}
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Username Error");
			alert.setHeaderText("Username is not valid");
			alert.setContentText("Please double check the username \n Access Denied");

			alert.showAndWait();
		}
	}
	
	@FXML
	void closeSorry(ActionEvent event)
	{
		Stage stage = (Stage) closeBtn.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	void deidentifyBtn(ActionEvent event) 
	{
		
		Stage stage = (Stage) deidentify.getScene().getWindow();
		AnchorPane root = null;
		try
		{
			root = FXMLLoader.load(HomeController.class.getResource("DeIdentifier.fxml"));
		}
		catch (IOException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Enternal Error");
			alert.setContentText("You must Reinstall HAD \n Resource missing");

			alert.showAndWait();
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.setTitle("HAD Health");
		stage.setResizable(false);
		
		
	}

	@FXML
	void artificialBtn(ActionEvent event) 
	{
		artificial.getScene().getWindow();
		AnchorPane root = null;
		try
		{
			root = FXMLLoader.load(HomeController.class.getResource("Home.fxml"));
		}
		catch (IOException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Enternal Error");
			alert.setContentText("You must Reinstall HAD \n Resource missing");

			alert.showAndWait();
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		stage.setTitle("HAD Health");
		stage.setResizable(false);
		Stage s = (Stage) artificial.getScene().getWindow();
		s.close();
	}
	
	@FXML
	void remember(ActionEvent event)
	{
		
	}
	
}
