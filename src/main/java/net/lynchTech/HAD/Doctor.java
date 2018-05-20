package net.lynchTech.HAD;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class Doctor
{
	//hello Jim mmmm
	private String Doc_Firstname;
	private String Doc_Lastname;
	private Sheet first_Sheet;
	private Sheet last_Sheet;
	private static int visit_Start_Year = 2010; // ******************
	private static int visit_End_Year = 2016; // *******************
	
	public static int randBetween(int start, int end)
	{
		return start + (int) Math.round(Math.random() * (end - start));
	}
	
	public static String randomDate()
	{
		GregorianCalendar gc = new GregorianCalendar();
		
		int year = randBetween(visit_Start_Year, visit_End_Year);
		
		gc.set(Calendar.YEAR, year);
		
		int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
		
		gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
		
		String date = gc.get(Calendar.YEAR) + "-"
		        + String.format("%1$02d", (gc.get(Calendar.MONTH) + 1)) + "-"
		        + String.format("%1$02d", gc.get(Calendar.DAY_OF_MONTH));
		
		return date;
	}
	
	public String[] getDates() // Could specify how many times default = 5
	{
		String[] dates;
		Random r = new Random();
		int times = r.nextInt(5) + 1;
		dates = new String[times];
		for (int i = 0; i < times; i++)
		{
			String date = randomDate();
			dates[i] = date;
		}
		Arrays.sort(dates);
		return dates;
	}
	
	public Doctor(Sheet first, Sheet last)
	{
		this.first_Sheet = first;
		this.last_Sheet = last;
	}
	
	public String getFirstname()
	{
		int row_num = this.first_Sheet.getLastRowNum();
		Random random = new Random();
		int select = random.nextInt(row_num) + 1;
		Row DocFirst_Row = this.first_Sheet.getRow(select);
		Cell DocFirst_Cell = DocFirst_Row.getCell(0);
		Doc_Firstname = DocFirst_Cell.toString();
		return Doc_Firstname;
		
	}
	
	public String getLastname()
	{
		int row_num = this.last_Sheet.getLastRowNum();
		Random random = new Random();
		int select = random.nextInt(row_num) + 1;
		Row DocLast_Row = this.last_Sheet.getRow(select);
		Cell DocLast_Cell = DocLast_Row.getCell(0);
		Doc_Lastname = DocLast_Cell.toString();
		return Doc_Lastname;
		
	}
	
}
