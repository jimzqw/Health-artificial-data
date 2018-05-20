package net.lynchTech.HAD;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import net.lynchtech.NumberTextField;

public class HomeController
{
	
	@FXML
	private Button back;
	
	@FXML
	private Button finish;
	
	@FXML
	private Button Load;
	
	@FXML
	private Button save;
	
	@FXML
	private Button csv;
	
	@FXML
	private Button xlsx;
	
	@FXML
	private Button backToArtificial;
	
	@FXML
	private NumberTextField rowNum;
	
	@FXML
	private NumberTextField whitePer;
	
	@FXML
	private NumberTextField blackPer;
	
	@FXML
	private NumberTextField asianPer;
	
	@FXML
	private NumberTextField latinoPer;
	
	@FXML
	private NumberTextField nativePer;
	
	@FXML
	private NumberTextField nativeIslandPer;
	
	@FXML
	private NumberTextField otherPer;
	
	@FXML
	private NumberTextField male;
	
	@FXML
	private NumberTextField female;
	
	@FXML
	private NumberTextField ageMin;
	
	@FXML
	private NumberTextField ageMax;
	
	@FXML
	private NumberTextField femaleWeightMin;
	
	@FXML
	private NumberTextField maleWeightMin;
	
	@FXML
	private NumberTextField maleWeightMax;
	
	@FXML
	private NumberTextField femaleWeightMax;
	
	@FXML
	private NumberTextField femaleHeightMin;
	
	@FXML
	private NumberTextField maleHeightMin;
	
	@FXML
	private NumberTextField maleHeightMax;
	
	@FXML
	private NumberTextField femaleHeightMax;
	
	@FXML
	private NumberTextField topBloodMin;
	
	@FXML
	private NumberTextField bottomBloodMin;
	
	@FXML
	private NumberTextField bottomBloodMax;
	
	@FXML
	private NumberTextField topBloodMax;
	
	@FXML
	private NumberTextField drugPer;

	BigDecimal bigD(Number n)
	{
		// Number n = s.getAgeMax();
		BigDecimal b = new BigDecimal(n.doubleValue());
		return b;
	}
	
	void closeScene(Button b)
	{
		Stage stage = (Stage) b.getScene().getWindow();
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
			alert.setContentText("You must Reinstall HAD \n Resources missing");

			alert.showAndWait();
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		stage.setTitle("HAD Health");
		stage.setResizable(false);
		closeScene(back);
		
	}
	
	@FXML
	void finishBtn(ActionEvent event) 
	{
		JAXB j = new JAXB();
		SetXML s = new SetXML();
		s.setGoalNumber(rowNum.getNumber().intValue());
		s.setWhitePer(whitePer.getNumber().doubleValue());
		s.setBlackPer(blackPer.getNumber().doubleValue());
		s.setAsianPer(asianPer.getNumber().doubleValue());
		s.setNativePer(nativePer.getNumber().doubleValue());
		s.setNativeIslandPer(nativeIslandPer.getNumber().doubleValue());
		s.setOtherPer(otherPer.getNumber().doubleValue());
		s.setGenderMale(male.getNumber().doubleValue());
		s.setGenderFemale(female.getNumber().doubleValue());
		s.setAgeMin(ageMin.getNumber().intValue());
		s.setAgeMax(ageMax.getNumber().intValue());
		s.setFemale_Weight_max(femaleWeightMax.getNumber().intValue());
		s.setFemale_Weight_min(femaleWeightMin.getNumber().intValue());
		s.setMale_Weight_max(maleWeightMax.getNumber().intValue());
		s.setMale_Weight_min(maleWeightMin.getNumber().intValue());
		s.setFemale_Height_max(femaleHeightMax.getNumber().intValue());
		s.setFemale_Height_min(femaleHeightMin.getNumber().intValue());
		s.setMale_Height_max(maleHeightMax.getNumber().intValue());
		s.setMale_Height_min(maleHeightMin.getNumber().intValue());
		s.setTopBloodMax(topBloodMax.getNumber().intValue());
		s.setTopBloodMin(topBloodMin.getNumber().intValue());
		s.setBotBloodMax(bottomBloodMax.getNumber().intValue());
		s.setBotBloodMin(bottomBloodMin.getNumber().intValue());
		s.setDrugs(drugPer.getNumber().intValue());
		try
		{
			j.write("temp.had", s);
		}
		catch (JAXBException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("File Error");
			alert.setContentText("Write file error \n You must allow HAD to write a file ");

			alert.showAndWait();
			e.printStackTrace();
		}

		AnchorPane root = null;
		try
		{
			root = FXMLLoader.load(HomeController.class.getResource("FinishPopup.fxml"));
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
		closeScene(finish);
	}
	
	@FXML
	void csvBtn(ActionEvent event)
	{
		HadInterface hi = new HAD();
		try
		{
			hi.write();
		}
		catch (IOException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("File Error");
			alert.setContentText("Write file error \n You must allow HAD to write a file ");

			alert.showAndWait();
			e.printStackTrace();
		}
		catch (JAXBException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("File Error");
			alert.setContentText("Write file error \n You must allow HAD to write a file ");

			alert.showAndWait();
			e.printStackTrace();
		}
	}
	
	@FXML
	void xlsxBtn(ActionEvent event)
	{
		HadInterface hi = new HADxlsx();
		try
		{
			hi.write();
		}
		catch (IOException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("File Error");
			alert.setContentText("Write file error \n You must allow HAD to write a file ");

			alert.showAndWait();
			e.printStackTrace();
		}
		catch (JAXBException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("File Error");
			alert.setContentText("Write file error \n You must allow HAD to write a file ");

			alert.showAndWait();
			e.printStackTrace();
		}
	}
	
	@FXML
	void backToArtificialBtn(ActionEvent event)
	{
		AnchorPane root = null;
		try
		{
			root = FXMLLoader.load(HomeController.class.getResource("Home1.fxml"));
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
		closeScene(backToArtificial);
	}
	
	@FXML
	void loadBtn(ActionEvent event)
	{
		Stage stage = (Stage) Load.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Data File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Property File", "*.had"));
		File file = fileChooser.showOpenDialog(stage);
		
		try
		{
			
			JAXBContext jaxbContext = JAXBContext.newInstance(SetXML.class);
			
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			SetXML s = (SetXML) jaxbUnmarshaller.unmarshal(file);
			
			rowNum.setNumber(bigD(s.getGoalNumber()));
			whitePer.setNumber(bigD(s.getWhitePer()));
			blackPer.setNumber(bigD(s.getBlackPer()));
			asianPer.setNumber(bigD(s.getAsianPer()));
			latinoPer.setNumber(bigD(s.getLatinoPer()));
			nativePer.setNumber(bigD(s.getNativePer()));
			nativeIslandPer.setNumber(bigD(s.getNativeIslandPer()));
			otherPer.setNumber(bigD(s.getOtherPer()));
			male.setNumber(bigD(s.getGenderMale()));
			female.setNumber(bigD(s.getGenderFemale()));
			ageMin.setNumber(bigD(s.getAgeMin()));
			ageMax.setNumber(bigD(s.getAgeMax()));
			femaleWeightMin.setNumber(bigD(s.getFemale_Weight_min()));
			femaleWeightMax.setNumber(bigD(s.getFemale_Weight_max()));
			maleWeightMin.setNumber(bigD(s.getMale_Weight_min()));
			maleWeightMax.setNumber(bigD(s.getMale_Weight_max()));
			femaleHeightMin.setNumber(bigD(s.getFemale_Height_min()));
			femaleHeightMax.setNumber(bigD(s.getFemale_Height_max()));
			maleHeightMin.setNumber(bigD(s.getMale_Height_min()));
			maleHeightMax.setNumber(bigD(s.getMale_Height_max()));
			topBloodMin.setNumber(bigD(s.getTopBloodMin()));
			topBloodMax.setNumber(bigD(s.getTopBloodMax()));
			bottomBloodMin.setNumber(bigD(s.getBotBloodMin()));
			bottomBloodMax.setNumber(bigD(s.getBotBloodMax()));
			drugPer.setNumber(bigD(s.getDrugs()));
			
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
	
	public void loadDefault()
	{
		try
		{
			File file = new File("default.had");
			JAXBContext jaxbContext = JAXBContext.newInstance(SetXML.class);
			
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			SetXML s = (SetXML) jaxbUnmarshaller.unmarshal(file);
			rowNum.setNumber(bigD(s.getGoalNumber()));
			whitePer.setNumber(bigD(s.getWhitePer()));
			blackPer.setNumber(bigD(s.getBlackPer()));
			asianPer.setNumber(bigD(s.getAsianPer()));
			latinoPer.setNumber(bigD(s.getLatinoPer()));
			nativePer.setNumber(bigD(s.getNativePer()));
			nativeIslandPer.setNumber(bigD(s.getNativeIslandPer()));
			otherPer.setNumber(bigD(s.getOtherPer()));
			male.setNumber(bigD(s.getGenderMale()));
			female.setNumber(bigD(s.getGenderFemale()));
			ageMin.setNumber(bigD(s.getAgeMin()));
			ageMax.setNumber(bigD(s.getAgeMax()));
			femaleWeightMin.setNumber(bigD(s.getFemale_Weight_min()));
			femaleWeightMax.setNumber(bigD(s.getFemale_Weight_max()));
			maleWeightMin.setNumber(bigD(s.getMale_Weight_min()));
			maleWeightMax.setNumber(bigD(s.getMale_Weight_max()));
			femaleHeightMin.setNumber(bigD(s.getFemale_Height_min()));
			femaleHeightMax.setNumber(bigD(s.getFemale_Height_max()));
			maleHeightMin.setNumber(bigD(s.getMale_Height_min()));
			maleHeightMax.setNumber(bigD(s.getMale_Height_max()));
			topBloodMin.setNumber(bigD(s.getTopBloodMin()));
			topBloodMax.setNumber(bigD(s.getTopBloodMax()));
			bottomBloodMin.setNumber(bigD(s.getBotBloodMin()));
			bottomBloodMax.setNumber(bigD(s.getBotBloodMax()));
			drugPer.setNumber(bigD(s.getDrugs()));
		}
		catch (JAXBException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("File Error");
			alert.setContentText("You must use default.had \n wrong file");

			alert.showAndWait();
			e.printStackTrace();
		}
		
	}
	
	@FXML
	void saveBtn(ActionEvent event)
	{
		Stage stage = (Stage) save.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save file");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Property File", "*.had"));
		File file = fileChooser.showSaveDialog(stage);
		
		SetXML s = new SetXML();
		s.setGoalNumber(rowNum.getNumber().intValue());
		s.setWhitePer(whitePer.getNumber().doubleValue());
		s.setBlackPer(blackPer.getNumber().doubleValue());
		s.setAsianPer(asianPer.getNumber().doubleValue());
		s.setNativePer(nativePer.getNumber().doubleValue());
		s.setNativeIslandPer(nativeIslandPer.getNumber().doubleValue());
		s.setOtherPer(otherPer.getNumber().doubleValue());
		s.setGenderMale(male.getNumber().doubleValue());
		s.setGenderFemale(female.getNumber().doubleValue());
		s.setAgeMin(ageMin.getNumber().intValue());
		s.setAgeMax(ageMax.getNumber().intValue());
		s.setFemale_Weight_max(femaleWeightMax.getNumber().intValue());
		s.setFemale_Weight_min(femaleWeightMin.getNumber().intValue());
		s.setMale_Weight_max(maleWeightMax.getNumber().intValue());
		s.setMale_Weight_min(maleWeightMin.getNumber().intValue());
		s.setFemale_Height_max(femaleHeightMax.getNumber().intValue());
		s.setFemale_Height_min(femaleHeightMin.getNumber().intValue());
		s.setMale_Height_max(maleHeightMax.getNumber().intValue());
		s.setMale_Height_min(maleHeightMin.getNumber().intValue());
		s.setTopBloodMax(topBloodMax.getNumber().intValue());
		s.setTopBloodMin(topBloodMin.getNumber().intValue());
		s.setBotBloodMax(bottomBloodMax.getNumber().intValue());
		s.setBotBloodMin(bottomBloodMin.getNumber().intValue());
		s.setDrugs(drugPer.getNumber().intValue());
		// s.setAvgIncome(avgIncome.getNumber().intValue());
		// s.setAvgPayment(avgPayment.getNumber().intValue());
		
		try
		{
			
			JAXBContext jaxbContext = JAXBContext.newInstance(SetXML.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			jaxbMarshaller.marshal(s, file);
			
		}
		catch (JAXBException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("File Error");
			alert.setContentText("Write file error \n You must allow HAD to write a file ");

			alert.showAndWait();
			e.printStackTrace();
		}
	}
}
