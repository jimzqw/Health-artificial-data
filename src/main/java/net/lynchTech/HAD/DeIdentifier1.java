package net.lynchTech.HAD;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DeIdentifier1
{
	public static int first_c;// the column number of first Name
	public static int middle_c;
	public static int last_c;
	public static int gender_c;
	public static int race_c;
	public static int DOB_c;
	public static int age_c;
	public static int SSN_c;
	public static int address_c;
	public static int city_c;

	public static void setColumnNums(Sheet input)
	{
		Row r = input.getRow(0);
		int i=0;
		int c = r.getLastCellNum();
		System.out.println(c);
		while(i<c)
		{
			String s = r.getCell(i).toString().toLowerCase().trim();
			if(s.equals("first name")) first_c =i;
			else if(s.equals("middle")) middle_c=i;
			else if(s.equals("last name")) last_c=i;
			else if(s.equals("gender")) gender_c=i;
			else if(s.equals("race")) race_c=i;
			else if(s.equals("dob")) DOB_c=i;
			else if(s.equals("age")) age_c=i;
			else if(s.equals("ssn")) SSN_c=i;
			else if(s.equals("address")) address_c=i;
			else if (s.equals("city")) city_c=i;
			i++;
		}
	}
	
	private static File getFile(String order)
	{
		JFileChooser fc = new JFileChooser(new File("C:\\"));
		fc.addChoosableFileFilter(new FileFilter() {
			 
		    public String getDescription() {
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
		fc.setDialogTitle(order);	
		fc.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
		fc.showSaveDialog(null);		
		
		
		File file = fc.getSelectedFile();
		if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xlsx")) 
		{
		    file = new File(file.toString() + ".xlsx");  // append .xml if "foo.jpg.xml" is OK
		    file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName())+".xlsx"); 
		    // ALTERNATIVELY: remove the extension (if any) and replace it with ".xml"
		} 
		
		return file;
	}
	
	private static String getDate()
	{
	
		DateFormat df = new SimpleDateFormat("MM-dd-yy");
		Calendar calobj = Calendar.getInstance();
		return df.format(calobj.getTime());
	}
		

	@SuppressWarnings("deprecation")
	public static void deidentify() throws IOException, JAXBException 
	{
//		File inFile = getFile("Choose File");
//		File outFile =getFile("Save De-Identified File");
		
		File inFile = DeIdentifierController.inFile;
		File outFile = DeIdentifierController.outFile;
		
		//-------------------------//
		SXSSFWorkbook workbookOut = new SXSSFWorkbook(100);
		Sheet output_Sheet = workbookOut.createSheet(getDate());
		
		
		FileInputStream input=new FileInputStream(inFile);
		Workbook input_wb = new XSSFWorkbook(input);
		Sheet input_Sheet = input_wb.getSheetAt(0);
		
		FileInputStream lastName_fileInput = new FileInputStream("LastNames.xlsx");// pull the file
		Workbook lastName_wb = new XSSFWorkbook(lastName_fileInput);
		

		FileInputStream firstName_fileInput = new FileInputStream("FirstNames.xlsx");
		Workbook firstName_wb = new XSSFWorkbook(firstName_fileInput);
	

		FileInputStream address_fileInput = new FileInputStream("Address.xlsx");
		Workbook address_In = new XSSFWorkbook(address_fileInput);
		Sheet address_Sheet = address_In.getSheet("Address");

		FileInputStream states_fileInput = new FileInputStream("States.xlsx");
		Workbook states_wb = new XSSFWorkbook(states_fileInput);
		Sheet Mixed_Sheet = states_wb.getSheet("Mixed");
		//Sheet WA_Sheet = states_wb.getSheet("WA");
		
		//------------Display--------------------//
		int goalNumber = input_Sheet.getLastRowNum();
		JFrame j = new JFrame("HAD");
		j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		j.setSize(400,100);
		JLabel l = new JLabel("");
		l.setPreferredSize(new Dimension(300, 50));
		l.setText("Target row number= " + goalNumber+"   Reading......");
		j.getContentPane().add(l, BorderLayout.CENTER);
		j.pack();
		j.setVisible(true);

		//-----------------------------//
		JAXB jaxb = new JAXB();
		String fileName = "default.xml";
		jaxb.read(fileName);
		Patient p = new Patient(firstName_wb, lastName_wb, address_Sheet, Mixed_Sheet, fileName);
		
		Row row0_in = input_Sheet.getRow(0);
		Row row0_out = output_Sheet.createRow(0);
		int cell0Helper = 0;
		
		while(cell0Helper<row0_in.getLastCellNum())//copy first Row
		{
			row0_in.getCell(cell0Helper).setCellType(Cell.CELL_TYPE_STRING);
			String cell0Value = row0_in.getCell(cell0Helper).getStringCellValue();
			row0_out.createCell(cell0Helper).setCellValue(cell0Value);
			cell0Helper++;
			
		}
		
		setColumnNums(input_Sheet);
		
		int rowHelper=1;
		System.out.println(input_Sheet.getLastRowNum());
		while(rowHelper<input_Sheet.getLastRowNum()+1)
		{
			Row row_in=input_Sheet.getRow(rowHelper);
			Row row_out=output_Sheet.createRow(rowHelper);
			int cellHelper=0;
			while(cellHelper<row_in.getLastCellNum())
			{
				row_in.getCell(cellHelper).setCellType(Cell.CELL_TYPE_STRING);
				String cellValue = row_in.getCell(cellHelper).getStringCellValue();
				row_out.createCell(cellHelper).setCellValue(cellValue);
				cellHelper++;
				String gender=row_in.getCell(gender_c).getStringCellValue();
				String race=row_in.getCell(race_c).getStringCellValue();
				String address = p.getAddressNumber(address_Sheet) + " " + p.getAddressStreet(address_Sheet);
				String SSN = p.getSSN();
				String patient_First = p.getFirstName(p.FirstName_Type(gender, race, firstName_wb));
				String patient_Last = p.getLastName(p.LastName_Type(race, lastName_wb));
				String middle = p.getMiddle();
				Random r = new Random();
				int randomDays=r.nextInt(900)+100;
				int age = p.DOBChangeDays(row_in.getCell(DOB_c).getStringCellValue(), randomDays );
				String DOB = p.DOB;
				row_out.createCell(first_c).setCellValue(patient_First);
				row_out.createCell(middle_c).setCellValue(middle);
				row_out.createCell(last_c).setCellValue(patient_Last);
				row_out.createCell(DOB_c).setCellValue(DOB);
				row_out.createCell(age_c).setCellValue(age);
				row_out.createCell(SSN_c).setCellValue(SSN);
				row_out.createCell(address_c).setCellValue(address);
				
			}
			rowHelper++;
		}
		
		
		
		
		
		
		l.setText("Waiting......");

		try {
			FileOutputStream output = new FileOutputStream(outFile);
			workbookOut.write(output);
			output.close();
			workbookOut.dispose();
			lastName_wb.close();
			firstName_wb.close();
			workbookOut.close();
			address_In.close();
			states_wb.close();
			input_wb.close();
			l.setText("Finish!");

			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	}


