package net.lynchTech.HAD;


import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class DeIdentifierController {

	
	public static File inFile;
	public static File outFile;
	
	
	@FXML
    private Button open;

    @FXML
    private Button save;

    @FXML
    private Button back;

    @FXML
    private Button finish;

    @FXML
    private Label openD;

    @FXML
    private Label saveD;
    
    @FXML
	void cancel(ActionEvent event)
	{
		// get a handle to the stage
		Stage stage = (Stage) back.getScene().getWindow();
		stage.close();
	}

    @FXML
    void backBtn(ActionEvent event) 
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
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		stage.setTitle("HAD Health");
		stage.setResizable(false);
		cancel(event);

    }

    @FXML
    void finishBtn(ActionEvent event)
    {
    	try
		{
			DeIdentifier1.deidentify();
		}
		catch (IOException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("File Error");
			alert.setContentText("You must use .xlsx file \n wrong file");

			alert.showAndWait();
			e.printStackTrace();
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

    }

    @FXML
    void openBtn(ActionEvent event)
    {
    	Stage stage = (Stage) open.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Data File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Excel Files", "*.xlsx"));
		File file = fileChooser.showOpenDialog(stage);
		DeIdentifierController.inFile=file.getAbsoluteFile();
		openD.setText(file.getAbsolutePath());

    }

    @FXML
    void saveBtn(ActionEvent event) 
    {
    	Stage stage = (Stage) save.getScene().getWindow();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Save file");
    	fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Excel Files", "*.xlsx"));
    	File file = fileChooser.showSaveDialog(stage);

    	DeIdentifierController.outFile=file.getAbsoluteFile();
    	saveD.setText(file.getAbsolutePath());
    	
		

    }

}
