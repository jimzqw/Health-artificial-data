package net.lynchTech.HAD;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.lynchTech.HAD.SetXML;;

public class JAXB
{
	
	public int goalNum;
	public double genderMale;
	public double white;
	public double black;
	public double asian;
	public double latino;
	public double Native;
	public double NativeIsland;
	public double other;
	public int topBloodMax;
	public int topBloodMin;
	public int botBloodMax;
	public int botBloodMin;
	public int ageMax;
	public int ageMin;
	public int female_Height_min;
	public int female_Height_max;
	
	public int male_Height_min;
	public int male_Height_max;
	
	public int female_Weight_min;
	public int female_Weight_max;
	
	public int male_Weight_min;
	public int male_Weight_max;
	
	public String username;
	public String password;
	public String MAC;
	
	void write(String fileName, SetXML s) throws JAXBException
	{

			File file = new File(fileName);
			JAXBContext jaxbContext = JAXBContext.newInstance(SetXML.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			jaxbMarshaller.marshal(s, file);

	}
	
	void read(String fileName) throws JAXBException
	{

			File file = new File(fileName);
			JAXBContext jaxbContext = JAXBContext.newInstance(SetXML.class);
			
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			SetXML s = (SetXML) jaxbUnmarshaller.unmarshal(file);
			goalNum = s.getGoalNumber();
			genderMale = s.getGenderMale();
			white = s.getWhitePer();
			black = s.getBlackPer();
			asian = s.getAsianPer();
			latino=s.getLatinoPer();
			Native=s.getNativePer();
			NativeIsland=s.getNativeIslandPer();
			other = s.getOtherPer();
			topBloodMax=s.getTopBloodMax();
			topBloodMin=s.getTopBloodMin();
			botBloodMax=s.getBotBloodMax();
			botBloodMin=s.getBotBloodMin();
			ageMax = s.getAgeMax();
			ageMin = s.getAgeMin();
			female_Height_min=s.getFemale_Height_min();
			female_Height_max=s.getFemale_Height_max();
			male_Height_min=s.getMale_Height_min();
			male_Height_max=s.getMale_Height_max();
			female_Weight_min=s.getFemale_Weight_min();
			female_Weight_max=s.getFemale_Weight_max();
			male_Weight_min=s.getMale_Weight_min();
			male_Weight_max=s.getMale_Weight_max();
			
			username=s.getUsername();
			password=s.getPassword();
			MAC=s.getMAC();

	}
	
	boolean testRace()
	{
		double total = white+black+asian+latino+Native+NativeIsland+other;
		if(total==100.0) return true;
		else return false;
		
	}
	
}
