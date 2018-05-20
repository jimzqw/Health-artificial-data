package net.lynchTech.HAD;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HADxlsx implements HadInterface
{
	private static String getMac() throws SocketException, UnknownHostException
	{
		InetAddress ip;
	
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
	
	public static void autoWidth(Sheet s)
	{
		for (int i = 0; i < 30; i++)
		{
			s.autoSizeColumn(i);
		}
		
	}
	
	private static boolean chance(int i)
	{
		Random r = new Random();
		if (r.nextInt(100) < i)
			return true;
		else
			return false;
	}
	
	public static void patient_FirstRow(Sheet sheetOut)
	{
		Row row0 = sheetOut.createRow(0);
		
		row0.createCell(0).setCellValue("PatientID");
		row0.createCell(1).setCellValue("First Name");
		row0.createCell(2).setCellValue("Middle");
		row0.createCell(3).setCellValue("Last Name");
		row0.createCell(4).setCellValue("Gender");
		row0.createCell(5).setCellValue("Race");
		row0.createCell(6).setCellValue("DOB");
		row0.createCell(7).setCellValue("Age");
		row0.createCell(8).setCellValue("SSN");
		row0.createCell(9).setCellValue("Income");
		row0.createCell(10).setCellValue("Insurance Company");
		row0.createCell(11).setCellValue("Address");
		row0.createCell(12).setCellValue("City");
		row0.createCell(13).setCellValue("State");
		row0.createCell(14).setCellValue("Zip Code");
		
	}
	
	public static void med_FirstRow(Sheet sheetOut)
	{
		Row row0 = sheetOut.createRow(0);
		
		row0.createCell(0).setCellValue("Patient ID");
		row0.createCell(1).setCellValue("Patient_First");
		row0.createCell(2).setCellValue("Patient_Last");
		row0.createCell(3).setCellValue("Doctor_First");
		row0.createCell(4).setCellValue("Doctor_Last");
		row0.createCell(5).setCellValue("Height(in)");
		row0.createCell(6).setCellValue("Weight(lb)");
		row0.createCell(7).setCellValue("BMI");
		row0.createCell(8).setCellValue("Type");
		row0.createCell(9).setCellValue("Blood Type");
		row0.createCell(10).setCellValue("Blood Pressure");
		row0.createCell(11).setCellValue("Type");
		row0.createCell(12).setCellValue("Drug Abuse");
		row0.createCell(13).setCellValue("Visit_Times");
		
	}
	
	public static void payment_FirstRow(Sheet sheetOut)
	{
		Row row0 = sheetOut.createRow(0);
		
		row0.createCell(0).setCellValue("Patient_ID");
		row0.createCell(1).setCellValue("Patient_First");
		row0.createCell(2).setCellValue("Patient_Last");
		row0.createCell(3).setCellValue("Visit Time");
		row0.createCell(4).setCellValue("Payment");
	}
	
	public static File getFile()
	{
		// ---------------------------------window save
				// file-----------------------//
				JFileChooser fc = new JFileChooser(new File("c:\\"));
				fc.addChoosableFileFilter(new FileFilter()
				{
					
					public String getDescription()
					{
						return "Excel Documents (*.xlsx)";
					}
					
					public boolean accept(File f)
					{
						if (f.isDirectory())
						{
							return true;
						}
						else
						{
							return f.getName().toLowerCase().endsWith(".xlsx");
						}
					}
				});
				fc.setDialogTitle("Save File");
				fc.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
				fc.showSaveDialog(null);
				
				File file = fc.getSelectedFile();
				if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xlsx"))
				{
					// filename is OK as-is
				}
				else
				{
					file = new File(file.toString() + ".xlsx"); // append .xml if
					                                            // "foo.jpg.xml" is OK
					file = new File(file.getParentFile(),
					        FilenameUtils.getBaseName(file.getName()) + ".xlsx"); // ALTERNATIVELY:
					                                                              // remove
					                                                              // the
					                                                              // extension
					                                                              // (if
					                                                              // any)
					                                                              // and
					                                                              // replace
					                                                              // it
					                                                              // with
					                                                              // ".xml"
				}
				return file;
	}
	
	
	public void write() throws IOException, JAXBException
	{
		
		System.out.println(getMac());
		File file = getFile();
		
		String fileName = "temp.had";
		JAXB jaxb = new JAXB();
		jaxb.read(fileName);
		int goalNumber = jaxb.goalNum;
		int ageMax=jaxb.ageMax;
		int ageMin=jaxb.ageMin;
		
		// ---------------------Simple UI for process
		// count--------------------------//
		JFrame j = new JFrame("HAD");
		j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		j.setSize(400, 100);
		JLabel l = new JLabel("");
		l.setPreferredSize(new Dimension(300, 50));
		l.setText("Target row number= " + goalNumber + "   Waiting......");
		j.getContentPane().add(l, BorderLayout.CENTER);
		j.pack();
		j.setVisible(true);
		
		// ------------------------------------------------------------------//
		FileInputStream lastName_fileInput = new FileInputStream("LastNames.xlsx");// pull
		                                                                           // the
		                                                                           // file
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
		//Sheet WA_Sheet = states_wb.getSheet("WA");
		
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
		
		SXSSFWorkbook workbookOut = new SXSSFWorkbook(100);// create new
		                                                   // workbook to
		// output the values
		
		Sheet patientInfo = workbookOut.createSheet("patientInfo");// create
		                                                           // output
		                                                           // sheet
		Sheet medHistory = workbookOut.createSheet("Medical History");
		Sheet payment = workbookOut.createSheet("Payment");
		
		Doctor d = new Doctor(DocFirst_Sheet, DocLast_Sheet);
		Patient p = new Patient(firstName_wb, lastName_wb, address_Sheet, Mixed_Sheet,fileName);
		ICDxlsx icd = new ICDxlsx(fileName);
		
		// -----------------Create first Rows--------------------------//
		patient_FirstRow(patientInfo);
		med_FirstRow(medHistory);
		payment_FirstRow(payment);
		
		// ----------------------Create Values for each
		// row---------------------//
		for (int row = 1; row <= goalNumber; row++)
		{
			Row r_PatientInfo = patientInfo.createRow(row);
			
			String gender = p.getGender();
			String race = p.getRace();
			String middle = p.getMiddle();
			String address = p.getAddressNumber(address_Sheet) + " "
			        + p.getAddressStreet(address_Sheet);
			String SSN = p.getSSN();
			String patient_First = p.getFirstName(p.FirstName_Type(gender, race, firstName_wb));
			String patient_Last = p.getLastName(p.LastName_Type(race, lastName_wb));
			int age = p.DOB(ageMin,ageMax);
			String dob = p.DOB;
			int income = p.getIncome(race, gender,age);
			String city = p.getCity(Mixed_Sheet);
			String state=p.getState(Mixed_Sheet, p.randomAddress);
			String zip = p.getZip(Mixed_Sheet, p.randomAddress);
			
			r_PatientInfo.createCell(0).setCellValue(row);
			
			r_PatientInfo.createCell(1).setCellValue(patient_First);
			
			r_PatientInfo.createCell(2).setCellValue(middle);
			
			r_PatientInfo.createCell(3).setCellValue(patient_Last);
			
			r_PatientInfo.createCell(4).setCellValue(gender);
			
			r_PatientInfo.createCell(5).setCellValue(race);
			
			r_PatientInfo.createCell(6).setCellValue(dob);
			
			r_PatientInfo.createCell(7).setCellValue(age);
			
			r_PatientInfo.createCell(8).setCellValue(SSN);
			
			NumberFormat f = NumberFormat.getCurrencyInstance();
			r_PatientInfo.createCell(9).setCellValue(f.format(income));
			
			r_PatientInfo.createCell(10).setCellValue(p.getInsurance());
			
			r_PatientInfo.createCell(11).setCellValue(address);
			
			r_PatientInfo.createCell(12).setCellValue(city);
			
			r_PatientInfo.createCell(13).setCellValue(state);
			
			r_PatientInfo.createCell(14).setCellValue(zip);
			
			
			
			// -------------------------Patient
			// Info------------------------------------//
			
			Row r_Med = medHistory.createRow(row);
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
			if (ifDrug)
				drug_Abuse = "Yes";
			
			r_Med.createCell(0).setCellValue(row);
			r_Med.createCell(1).setCellValue(patient_First);
			r_Med.createCell(2).setCellValue(patient_Last);
			r_Med.createCell(3).setCellValue(doc_First);
			r_Med.createCell(4).setCellValue(doc_Last);
			r_Med.createCell(5).setCellValue(height);
			r_Med.createCell(6).setCellValue(weight);
			r_Med.createCell(7).setCellValue(String.format("%.1f", bmi));
			r_Med.createCell(8).setCellValue(bmi_type);
			r_Med.createCell(9).setCellValue(bType);
			r_Med.createCell(10).setCellValue(blood_Pressure);
			r_Med.createCell(11).setCellValue(bp_type);
			r_Med.createCell(12).setCellValue(drug_Abuse);
			
			Row r_payment = payment.createRow(row);
			r_payment.createCell(0).setCellValue(row);
			r_payment.createCell(1).setCellValue(patient_First);
			r_payment.createCell(2).setCellValue(patient_Last);
			
			for (int i = 0; i < dates.length; i++)
			{
				int last_m = r_Med.getLastCellNum();
				int last_p = r_payment.getLastCellNum();
				r_Med.createCell(last_m).setCellValue(dates[i]);
				r_payment.createCell(last_p).setCellValue(dates[i]);
				
				if (chance(80))
					icd.ICD_Common(common_Sheet, rare_Sheet, r_Med);
				if (chance(50))
					icd.ICD_Gender(gender, male_Sheet, female_Sheet, r_Med);
				icd.ICD_Overweight(bmi, overweight_Sheet, r_Med);
				icd.ICD_Age(age, age_Sheet, r_Med);
				if (chance(50))
					icd.ICD_Preg(age, gender, preg_Sheet, r_Med);
				if (chance(90))
					icd.ICD_BP(bp_type, bp_Sheet, r_Med);
				icd.ICD_Drug(ifDrug, drug_Sheet, r_Med);
				
				r_payment.createCell(last_p + 1).setCellValue(icd.payment(income));
			}
			
			// --------------------ICD----------------------------------------//
			
			l.setText("Target row number= " + goalNumber + "   " + row + " Completed!");
			
			System.out.println(row);
			
		}
		l.setText("Waiting......");
		// autoWidth(patientInfo);
		// autoWidth(medHistory);
		// autoWidth(payment);
		
		try
		{
			FileOutputStream output = new FileOutputStream(file);
			workbookOut.write(output);
			output.close();
			workbookOut.dispose();
			lastName_wb.close();
			firstName_wb.close();
			workbookOut.close();
			address_In.close();
			states_wb.close();
			ICD_wb.close();
			l.setText("Finish!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
