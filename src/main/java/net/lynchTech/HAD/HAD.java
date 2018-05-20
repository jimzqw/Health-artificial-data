package net.lynchTech.HAD;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HAD implements HadInterface
{
	
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
			e.printStackTrace();
		}
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private static boolean chance(int i)
	{
		Random r = new Random();
		if (r.nextInt(100) < i) return true;
		else
			return false;
	}
	
	private static File saveFile()
	{
		JFileChooser fc = new JFileChooser(new File("C:\\"));
		fc.addChoosableFileFilter(new FileFilter()
		{
			
			public String getDescription()
			{
				return "Excel Documents (*.csv)";
			}
			
			public boolean accept(File f)
			{
				if (f.isDirectory())
				{
					return true;
				}
				else
				{
					return f.getName().toLowerCase().endsWith(".csv");
				}
			}
		});
		fc.setDialogTitle("Save File");
		fc.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
		fc.showSaveDialog(null);
		
		File file = fc.getSelectedFile();
		if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("csv"))
		{
			// filename is OK as-is
		}
		else
		{
			file = new File(file.toString() + ".csv"); // append .xml if
			                                           // "foo.jpg.xml" is OK
			file = new File(file.getParentFile(),
			        FilenameUtils.getBaseName(file.getName()) + ".csv");
			// ALTERNATIVELY: remove the extension (if any) and replace it with
			// ".xml"
		}
		
		return file;
	}
	
	public void write() throws IOException, JAXBException
	{
		
		System.out.println(getMac());
		String fileName = "temp.had"; // #############
		JAXB jaxb = new JAXB();
		jaxb.read(fileName);
		int goalNumber = jaxb.goalNum;
		int ageMax = jaxb.ageMax;
		int ageMin = jaxb.ageMin;
		
		// ---------------------------------window save
		// file-----------------------//
		File file = saveFile();
		
		// ---------------------Simple UI for process
		// count--------------------------//
		JFrame j = new JFrame("HAD");
		j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		j.setSize(400, 100);
		JLabel l = new JLabel("");
		l.setPreferredSize(new Dimension(300, 50));
		l.setText("Target row number= " + goalNumber + "   Reading......");
		j.getContentPane().add(l, BorderLayout.CENTER);
		j.pack();
		j.setVisible(true);
		
		// -------------------------------------------------// pull the file
		FileInputStream lastName_fileInput = new FileInputStream("LastNames.xlsx");
		
		Workbook lastName_wb = new XSSFWorkbook(lastName_fileInput);
		Sheet DocLast_Sheet = lastName_wb.getSheet("DocLast");
		
		FileInputStream firstName_fileInput = new FileInputStream("FirstNames.xlsx");
		Workbook firstName_wb = new XSSFWorkbook(firstName_fileInput);
		Sheet DocFirst_Sheet = firstName_wb.getSheet("DocFirst");
		
		FileInputStream address_fileInput = new FileInputStream("Address.xlsx");
		Workbook address_In = new XSSFWorkbook(address_fileInput);
		Sheet address_Sheet = address_In.getSheet("Address");
		
		FileInputStream states_fileInput = new FileInputStream("States.xlsx");
		Workbook states_wb = new XSSFWorkbook(states_fileInput);
		Sheet Mixed_Sheet = states_wb.getSheet("Mixed");
		Sheet WA_Sheet = states_wb.getSheet("WA");
		
		FileInputStream ICD_fileInput = new FileInputStream("ICD.xlsx");
		Workbook ICD_wb = new XSSFWorkbook(ICD_fileInput);
		Sheet male_Sheet = ICD_wb.getSheet("Male");
		Sheet female_Sheet = ICD_wb.getSheet("Female");
		Sheet overweight_Sheet = ICD_wb.getSheet("Overweight");
		Sheet age_Sheet = ICD_wb.getSheet("Age");
		Sheet preg_Sheet = ICD_wb.getSheet("Pregnancy");
		Sheet bp_Sheet = ICD_wb.getSheet("Hypertension");
		Sheet common_Sheet = ICD_wb.getSheet("Common");
		Sheet drug_Sheet = ICD_wb.getSheet("Drug");
		Sheet rare_Sheet = ICD_wb.getSheet("Rare");
		
		FileWriter writer = new FileWriter(file);
		
		List<String> line = new ArrayList<String>();
		String[] firstLine = new String[] { "PatientID", "First Name", "Middle", "Last Name",
		        "Gender", "Race", "DOB", "Age", "SSN", "Income", "Insurance", "Address", "City",
		        "State", "Zip Code", "Doctor_First", "Doctor_Last", "Height(in)", "Weight(lb)",
		        "BMI", "Type", "Blood Type", "Blood_Pressure", "Pressure_Type", "Drug_Abuse",
		        "Visit_Time", "Payment", "ICD10_Code", "ICD10_Description" };
		line.addAll(Arrays.asList(firstLine));
		
		CSVUtils.writeLine(writer, line);
		line.clear();
		
		Doctor d = new Doctor(DocFirst_Sheet, DocLast_Sheet);
		Patient p = new Patient(firstName_wb, lastName_wb, address_Sheet, WA_Sheet, fileName);
		ICD icd = new ICD(fileName);
		
		// ----------------------Create Values for each
		// row---------------------//
		for (int row = 1; row <= goalNumber; row++)
		{
			
			String rowNum = row + "";
			String gender = p.getGender();
			String race = p.getRace();
			String middle = p.getMiddle();
			String address = p.getAddressNumber(address_Sheet) + " "
			        + p.getAddressStreet(address_Sheet);
			String SSN = p.getSSN();
			String patient_First = p.getFirstName(p.FirstName_Type(gender, race, firstName_wb));
			String patient_Last = p.getLastName(p.LastName_Type(race, lastName_wb));
			int age = p.DOB(ageMin, ageMax);
			String dob = p.DOB;
			
			int income = p.getIncome(race, gender, age);
			NumberFormat f = NumberFormat.getCurrencyInstance();
			String income_s = f.format(income);
			
			String city = p.getCity(Mixed_Sheet);
			int select = p.randomAddress;
			String state = p.getState(Mixed_Sheet, select);
			String zip = p.getZip(Mixed_Sheet, select);
			
			line.add(rowNum);
			
			line.add(patient_First);
			
			line.add(middle);
			
			line.add(patient_Last);
			
			line.add(gender);
			
			line.add(race);
			
			line.add(dob);
			
			line.add(age + "");
			
			line.add(SSN);
			
			line.add(income_s);
			
			line.add(p.getInsurance());
			
			line.add(address);
			
			line.add(city);
			
			line.add(state);
			
			line.add(zip);
			
			// -------------------------Patient
			// Info------------------------------------//
			
			String doc_First = d.getFirstname();
			String doc_Last = d.getLastname();
			String[] dates = d.getDates();
			
			int height = icd.getHeight(gender);
			int weight = icd.getWeight(gender);
			double bmi = icd.BMI(height, weight);
			String bmi_type = icd.BMI_Type(bmi);
			String bType = icd.bloodType(race);
			int blood_top = icd.blood_Top();
			int blood_bot = icd.blood_Bot();
			String blood_Pressure = blood_top + "/" + blood_bot;
			String bp_type = icd.blood_Pressure(blood_top, blood_bot);
			boolean ifDrug = icd.getDrug(gender, race);
			String drug_Abuse = "No";
			if (ifDrug) drug_Abuse = "Yes";
			
			line.add(doc_First);
			line.add(doc_Last);
			line.add(height + "");
			line.add(weight + "");
			line.add(String.format("%.1f", bmi));
			line.add(bmi_type);
			line.add(bType);
			line.add(blood_Pressure);
			line.add(bp_type);
			line.add(drug_Abuse);
			
			for (int i = 0; i < dates.length; i++)
			{
				
				line.add(dates[i]);
				line.add(icd.payment(income));
				if (chance(60)) icd.ICD_Common(common_Sheet, rare_Sheet, line);
				else
				{
					icd.ICD_Gender(gender, male_Sheet, female_Sheet, line);
				icd.ICD_Overweight(bmi, overweight_Sheet, line);
				icd.ICD_Age(age, age_Sheet, line);
				icd.ICD_Preg(age, gender, preg_Sheet, line);
				icd.ICD_BP(bp_type, bp_Sheet, line);
				icd.ICD_Drug(ifDrug, drug_Sheet, line);
				}
				
			}
			
			// --------------------ICD----------------------------------------//
			
			CSVUtils.writeLine(writer, line, ',', '"');
			line.clear();
			
			l.setText("Target row number= " + goalNumber + "   " + row + " Completed!");
			
		}
		l.setText("Waiting......");
		
		writer.flush();
		writer.close();
		address_In.close();
		states_wb.close();
		ICD_wb.close();
		l.setText("Finish!");
		
	}
	
}
