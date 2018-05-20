package net.lynchTech.HAD;

import java.util.Random;

import javax.xml.bind.JAXBException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ICDxlsx 
{
	Sheet ICD;
	int height;
	int weight;
	private static int topBloodMax;
	private static int topBloodMin;
	private static int botBloodMax;
	private static int botBloodMin;
	
	private static int female_Height_min = 58;
	private static int female_Height_max = 72;

	private static int male_Height_min = 60;
	private static int male_Height_max = 76;

	private static int female_Weight_min = 102;
	private static int female_Weight_max=300;

	private static int male_Weight_min;
	private static int male_Weight_max;

	// -------------Blood Pressure Normal, Prehypertension, State1,
	
	
	// State2----------------//
	private int normal_Blood_Top = 120;
	private int normal_Blood_Bot = 80;

	private int pre_Blood_Top = 139;
	private int pre_Blood_Bot = 89;

	private int s1_Blood_Top = 159;
	private int s1_Blood_Bot = 99;

	public ICDxlsx(String fileName) throws JAXBException
	{
		JAXB jaxb = new JAXB();
		jaxb.read(fileName);
		topBloodMax=jaxb.topBloodMax;
		topBloodMin=jaxb.topBloodMin;
		botBloodMax=jaxb.botBloodMax;
		botBloodMin=jaxb.botBloodMin;
		female_Height_min=jaxb.female_Height_min;
		female_Height_max=jaxb.female_Height_max;
		male_Height_min=jaxb.male_Height_min;
		male_Height_max=jaxb.male_Height_max;
		female_Weight_min=jaxb.female_Weight_min;
		female_Weight_max=jaxb.female_Weight_max;
		male_Weight_min=jaxb.male_Weight_min;
		male_Weight_max=jaxb.male_Weight_max;
		
	}
	
	private static boolean chance(int i)
	{
		Random r = new Random();
		if( r.nextInt(100)<i) return true;
		else return false;
	}
	
	
	public int getHeight(String gender) {
		Random r = new Random();
		int female_Height = r.nextInt(female_Height_max - female_Height_min) + female_Height_min + 1;
		int male_Height = r.nextInt(male_Height_max - male_Height_min) + male_Height_min + 1;
		if (gender == "Female")
			height = female_Height;
		else
			height = male_Height;

		return height;

	}

	public int getWeight(String gender) {
		Random r = new Random();
		int female_Weight = r.nextInt(female_Weight_max - female_Weight_min + 1) + female_Weight_min;
		int male_Weight = r.nextInt(male_Weight_max - male_Weight_min + 1) + male_Weight_min;
		if (gender == "Female")
			height = female_Weight;
		else
			height = male_Weight;

		return height;

	}

	public double BMI(int height, int weight) {
		double BMI = weight / Math.pow(height, 2) * 703;

		return BMI;
	}

	public String BMI_Type(double BMI) {
		String type;
		if (BMI < 18.5)
			type = "Underweight";
		else if (BMI >= 18.5 & BMI < 24.9)
			type = "Normal Weight";
		else if (BMI >= 24.9 & BMI < 29.9)
			type = "Overweight";
		else
			type = "Obese";
		return type;
	}

	public int blood_Top()
	{
		Random r = new Random();
		int top = r.nextInt(topBloodMin+1) + topBloodMax-topBloodMin;
		return top;
		
	}
	
	public int blood_Bot()
	{
		Random r = new Random();
		int bot = r.nextInt(botBloodMin) + botBloodMax-botBloodMin;
		return bot;
	}

	public String blood_Pressure(int top, int bot) {
		String bp;
		if (top <= normal_Blood_Top & bot <= normal_Blood_Bot)
			bp = "Normal";
		else if (top <= pre_Blood_Top & bot <= pre_Blood_Bot)
			bp = "Pre";
		else if (top <= s1_Blood_Top & bot <= s1_Blood_Bot)
			bp = "S1";
		else
			bp = "S2";
		return bp;
	}
	
	public void ICD_Common(Sheet common, Sheet rare, Row row)
	{
		Random r = new Random();
		int common_rowNum = common.getLastRowNum()+1;
		int rare_rowNum = rare.getLastRowNum()+1;
		int randomCodes = r.nextInt(1)+1;
		for(int i=0;i<randomCodes;i++)
		{
			if(chance(70))
		{
			Row common_r = common.getRow(r.nextInt(common_rowNum));
			String common_c0 = common_r.getCell(0).toString();
			String common_c1 = common_r.getCell(1).toString();
			int last_c = row.getLastCellNum();
			row.createCell(last_c).setCellValue(common_c0);
			row.createCell(last_c+1).setCellValue(common_c1);
		}
		else
		{
			Row rare_r = rare.getRow(r.nextInt(rare_rowNum));
			String rare_c0 = rare_r.getCell(0).toString();
			String rare_c1 = rare_r.getCell(1).toString();
			int last_c = row.getLastCellNum();
			row.createCell(last_c).setCellValue(rare_c0);
			row.createCell(last_c+1).setCellValue(rare_c1);
		}
		}
		
	}

	public void ICD_Gender(String gender, Sheet male, Sheet female, Row row) {
		Random r = new Random();
		int male_rowNum = male.getLastRowNum() +1;
		int female_rowNum = female.getLastRowNum() +1;
		int randomCodes = r.nextInt(1) + 1; // How many ICD codes to generate
		
		if (gender == "Male") 
		{
			for (int i = 0; i < randomCodes; i++) {
				Row male_row = male.getRow(r.nextInt(male_rowNum));
				Cell male_cell0 = male_row.getCell(0);
				Cell male_cell1 = male_row.getCell(1);
				String male_ICD = male_cell0.toString();
				String male_description = male_cell1.toString();
				int last_c = row.getLastCellNum();
				row.createCell(last_c).setCellValue(male_ICD);
				row.createCell(last_c + 1).setCellValue(male_description);
			}
		} else {
			for (int j = 0; j < randomCodes; j++) {
				Row female_row = female.getRow(r.nextInt(female_rowNum));
				Cell female_cell0 = female_row.getCell(0);
				Cell female_cell1 = female_row.getCell(1);
				String female_ICD = female_cell0.toString();
				String female_description = female_cell1.toString();
				int last_c = row.getLastCellNum();
				row.createCell(last_c).setCellValue(female_ICD);
				row.createCell(last_c + 1).setCellValue(female_description);
			}
		}

	}
	
	
	public void ICD_Overweight(double BMI, Sheet overweight, Row row) {
		Random r = new Random();
		if (BMI >= 28 & BMI <= 38) {
			if (chance(60)) {
				Row over_r = overweight.getRow(r.nextInt(5));
				String over_c0 = over_r.getCell(0).toString();
				String over_c1 = over_r.getCell(1).toString();
				int last_c = row.getLastCellNum();
				row.createCell(last_c).setCellValue(over_c0);
				row.createCell(last_c+1).setCellValue(over_c1);
			}
		}
		else if(BMI>38){
			if (chance(80)) {
				Row over_r = overweight.getRow(r.nextInt(1)+5);
				String over_c0 = over_r.getCell(0).toString();
				String over_c1 = over_r.getCell(1).toString();
				int last_c = row.getLastCellNum();
				row.createCell(last_c).setCellValue(over_c0);
				row.createCell(last_c+1).setCellValue(over_c1);
			}
		}
	}
	
	public void ICD_Age(int a, Sheet age, Row row)
	{
		Random r = new Random();
		int age_last = age.getLastRowNum()+1;
		if(a>70)
		{
			if(chance(60))
			{
				Row age_r = age.getRow(r.nextInt(age_last));
				String age_c0 = age_r.getCell(0).toString();
				String age_c1 = age_r.getCell(1).toString();
				int last_c = row.getLastCellNum();
				row.createCell(last_c).setCellValue(age_c0);
				row.createCell(last_c+1).setCellValue(age_c1);
			}
		}
	}
	
	public void ICD_Preg(int age, String gender, Sheet preg, Row row)
	{
		Random r = new Random();
		int preg_last = preg.getLastRowNum()+1;
		
		if(gender=="Female" & age>=18 & age<=45)
		{
			if(chance(25))
			{
				for(int i=0;i<r.nextInt(2)+1;i++)
				{
					Row preg_r = preg.getRow(r.nextInt(preg_last));
					String preg_c0 = preg_r.getCell(0).toString();
					String preg_c1 = preg_r.getCell(1).toString();
					int last_c = row.getLastCellNum();
					row.createCell(last_c).setCellValue(preg_c0);
					row.createCell(last_c+1).setCellValue(preg_c1);
				}
			}
		}
	}
	
	public void ICD_BP(String bp, Sheet BP, Row row)
	{
		Random r = new Random();
		int bp_last = BP.getLastRowNum()+1;
		if(bp == "S1" || bp=="S2")
		{
			if(chance(80))
			{
				for(int i=0; i<r.nextInt(1)+1;i++)
				{
					Row bp_r = BP.getRow(r.nextInt(bp_last));
					String bp_c0 = bp_r.getCell(0).toString();
					String bp_c1 = bp_r.getCell(1).toString();
					int last_c = row.getLastCellNum();
					row.createCell(last_c).setCellValue(bp_c0);
					row.createCell(last_c+1).setCellValue(bp_c1);
				}
			}
		}
	}
	
	public boolean getDrug(String gender, String race)
	{
		
		
		boolean drug_Abuse = false;
		if(gender=="Male")
		{
			if(race=="White" & chance(9)) drug_Abuse = true;
			else if(race=="Black" & chance(11)) drug_Abuse = true;
			else if(race=="Asian" & chance(3)) drug_Abuse = true;
			else if(race=="Latino" & chance(6)) drug_Abuse = true;
			else if(race=="Other" & chance(5)) drug_Abuse = true;
		}
		if(gender=="Female")
		{
			if(race=="White" & chance(4)) drug_Abuse = true;
			else if(race=="Black" & chance(6)) drug_Abuse = true;
			else if(race=="Asian" & chance(1)) drug_Abuse = true;
			else if(race=="Latino" & chance(3)) drug_Abuse = true;
			else if(race=="Other" & chance(2)) drug_Abuse = true;
		}
		return drug_Abuse;
		
		
		
	}
	
	public void ICD_Drug(boolean drug_Abuse, Sheet drug, Row row)
	{
		Random r = new Random();
		int drug_last = drug.getLastRowNum()+1;
		if(drug_Abuse)
		{
			row.createCell(11).setCellValue("Yes");
			for(int i=0; i<r.nextInt(1)+1;i++)
			{
				Row drug_r = drug.getRow(r.nextInt(drug_last));
				String drug_c0 = drug_r.getCell(0).toString();
				String drug_c1 = drug_r.getCell(1).toString();
				int last_c = row.getLastCellNum();
				row.createCell(last_c).setCellValue(drug_c0);
				row.createCell(last_c+1).setCellValue(drug_c1);
			}
		}
	}
	
	
	public String payment(int income)
	{
		Random r = new Random();
		int random = r.nextInt(100);
		int p=0;
		
		if(income<50000)
		{
			if(random<30)  p=r.nextInt(8)+6;	
			else if(random<80 & random>=30) p=r.nextInt(6)+14;
			else p=20;
		}
		else
		{
			if(random<10)  p=r.nextInt(8)+5;	
			else if(random<40 & random>=10) p=r.nextInt(6)+14;
			else p=20;
		}
		
		
		return p*5 + "%";
	}
	
	public String bloodType(String race) //http://www.bloodbook.com/world-abo.html
	{
		Random r = new Random();
		int i = r.nextInt(100);
		if(race=="White") 
		{			
			if(i<45) return "O";
			else if(i<85) return "A";
			else if(i<96) return "B";
			else return "AB";
		}
		else if(race=="Black")
		{	
			if(i<49) return "O";
			else if(i<76) return "A";
			else if(i<96) return "B";
			else return "AB";
		}
		else if(race=="Asian")
		{
			if(i<40) return "O";
			else if(i<68) return "A";
			else if(i<95) return "B";
			else return "AB";
		}
		else if(race=="Latino")
		{
			if(i<38) return "O";
			else if(i<85) return "A";
			else if(i<95) return "B";
			else return "AB";
		}
		else
		{
			if(i<38) return "O";
			else if(i<82) return "A";
			else if(i<95) return "B";
			else return "AB";
		}
	}

}
