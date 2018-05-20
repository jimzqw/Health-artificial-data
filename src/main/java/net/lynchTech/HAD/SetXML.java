package net.lynchTech.HAD;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class SetXML
{
	private int goalNumber = 100;
	private double genderMale = 50; // *********************
	private double genderFemale = 50;
	private double whitePer = 72.4;// *********************
	private double blackPer = 12.6;// *********************
	private double asianPer = 4.8;// *********************
	private double latinoPer = 6.2;
	private double nativePer = 0.9;
	private double nativeIslandPer = 0.2;
	private double otherPer = 2.9;
	private int ageMin = 5;
	private int ageMax = 80;
	private int female_Height_min = 58;
	private int female_Height_max = 72;
	
	private int male_Height_min = 60;
	private int male_Height_max = 76;
	
	private int female_Weight_min = 102;
	private int female_Weight_max = 179;
	
	private int male_Weight_min = 128;
	private int male_Weight_max = 307;
	
	private int topBloodMax = 180;
	private int topBloodMin = 100;
	private int botBloodMax = 120;
	private int botBloodMin = 60;
	private int drugs = 10;
	private int avgIncome = 60000;
	private int avgPayment = 80;
	private String username = "joe";
	private String password = "123";
	private String MAC;
	
	public int getGoalNumber()
	{
		return goalNumber;
	}
	
	@XmlElement
	public void setGoalNumber(int goalNumber)
	{
		this.goalNumber = goalNumber;
	}
	
	public double getGenderMale()
	{
		return genderMale;
	}
	
	@XmlElement
	public void setGenderMale(double d)
	{
		this.genderMale = d;
	}
	
	public double getGenderFemale()
	{
		return genderFemale;
	}
	
	@XmlElement
	public void setGenderFemale(double genderFemale)
	{
		this.genderFemale = genderFemale;
	}
	
	public double getWhitePer()
	{
		return whitePer;
	}
	
	@XmlElement
	public void setWhitePer(double whitePer)
	{
		this.whitePer = whitePer;
	}
	
	public double getBlackPer()
	{
		return blackPer;
	}
	
	@XmlElement
	public void setBlackPer(double blackPer)
	{
		this.blackPer = blackPer;
	}
	
	public double getAsianPer()
	{
		return asianPer;
	}
	
	@XmlElement
	public void setAsianPer(double asianPer)
	{
		this.asianPer = asianPer;
	}
	
	public double getLatinoPer()
	{
		return latinoPer;
	}
	
	@XmlElement
	public void setLatinoPer(double latinoPer)
	{
		this.latinoPer = latinoPer;
	}
	
	public double getNativePer()
	{
		return nativePer;
	}
	
	@XmlElement
	public void setNativePer(double nativePer)
	{
		this.nativePer = nativePer;
	}
	
	public double getNativeIslandPer()
	{
		return nativeIslandPer;
	}
	
	@XmlElement
	public void setNativeIslandPer(double nativeIslandPer)
	{
		this.nativeIslandPer = nativeIslandPer;
	}
	
	public double getOtherPer()
	{
		return otherPer;
	}
	
	@XmlElement
	public void setOtherPer(double otherPer)
	{
		this.otherPer = otherPer;
	}
	
	public int getAgeMin()
	{
		return ageMin;
	}
	
	@XmlElement
	public void setAgeMin(int ageMin)
	{
		this.ageMin = ageMin;
	}
	
	public int getAgeMax()
	{
		return ageMax;
	}
	
	@XmlElement
	public void setAgeMax(int ageMax)
	{
		this.ageMax = ageMax;
	}
	
	public int getDrugs()
	{
		return drugs;
	}
	
	@XmlElement
	public void setDrugs(int drugs)
	{
		this.drugs = drugs;
	}
	
	public int getAvgIncome()
	{
		return avgIncome;
	}
	
	@XmlElement
	public void setAvgIncome(int avgIncome)
	{
		this.avgIncome = avgIncome;
	}
	
	public int getAvgPayment()
	{
		return avgPayment;
	}
	
	@XmlElement
	public void setAvgPayment(int avgPayment)
	{
		this.avgPayment = avgPayment;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	@XmlElement
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	@XmlElement
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public int getTopBloodMax()
	{
		return topBloodMax;
	}
	
	public void setTopBloodMax(int topBloodMax)
	{
		this.topBloodMax = topBloodMax;
	}
	
	public int getTopBloodMin()
	{
		return topBloodMin;
	}
	
	public void setTopBloodMin(int topBloodMin)
	{
		this.topBloodMin = topBloodMin;
	}
	
	public int getBotBloodMax()
	{
		return botBloodMax;
	}
	
	public void setBotBloodMax(int botBloodMax)
	{
		this.botBloodMax = botBloodMax;
	}
	
	public int getBotBloodMin()
	{
		return botBloodMin;
	}
	
	public void setBotBloodMin(int botBloodMin)
	{
		this.botBloodMin = botBloodMin;
	}
	
	public int getFemale_Height_min()
	{
		return female_Height_min;
	}
	
	public void setFemale_Height_min(int female_Height_min)
	{
		this.female_Height_min = female_Height_min;
	}
	
	public int getFemale_Height_max()
	{
		return female_Height_max;
	}
	
	public void setFemale_Height_max(int female_Height_max)
	{
		this.female_Height_max = female_Height_max;
	}
	
	public int getMale_Height_min()
	{
		return male_Height_min;
	}
	
	public void setMale_Height_min(int male_Height_min)
	{
		this.male_Height_min = male_Height_min;
	}
	
	public int getMale_Height_max()
	{
		return male_Height_max;
	}
	
	public void setMale_Height_max(int male_Height_max)
	{
		this.male_Height_max = male_Height_max;
	}
	
	public int getFemale_Weight_min()
	{
		return female_Weight_min;
	}
	
	public void setFemale_Weight_min(int female_Weight_min)
	{
		this.female_Weight_min = female_Weight_min;
	}
	
	public int getFemale_Weight_max()
	{
		return female_Weight_max;
	}
	
	public void setFemale_Weight_max(int female_Weight_max)
	{
		this.female_Weight_max = female_Weight_max;
	}
	
	public int getMale_Weight_max()
	{
		return male_Weight_max;
	}
	
	public void setMale_Weight_max(int male_Weight_max)
	{
		this.male_Weight_max = male_Weight_max;
	}
	
	public int getMale_Weight_min()
	{
		return male_Weight_min;
	}
	
	public void setMale_Weight_min(int male_Weight_min)
	{
		this.male_Weight_min = male_Weight_min;
	}

	public String getMAC() {
		return MAC;
	}

	public void setMAC(String mAC) {
		MAC = mAC;
	}
	
}
