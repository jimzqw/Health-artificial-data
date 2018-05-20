package net.lynchTech.HAD;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.xml.bind.JAXBException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Years;

public class Patient
{
	
	// the percentage of gender and race
	private double genderMale;
	private static double white;
	private static double black;
	private static double asian;
	private static double latino;
	private static double Native;
	private static double NativeIsland;
	private static double other;
	private static int ageMax;
	private static int ageMin;
	
	public String DOB;
	public int randomAddress;
	
	private String[] insurance = { "Kaiser Foundation", "Humana", "AvMed", "Cigna", "Humana",
	        "Blue Cross Blue Shield", "Health Alliance", "HealthPartners", "SelectHealth", "Anthem",
	        "UnitedHealthcare", "Aetna" };
	
	public Patient(Workbook firstNames, Workbook lastNames, Sheet Address, Sheet zip_City,
	        String fileName) throws JAXBException
	{
		JAXB jaxb = new JAXB();
		jaxb.read(fileName);
		genderMale = jaxb.genderMale;
		white = jaxb.white;
		black = jaxb.black;
		asian = jaxb.asian;
		latino = jaxb.latino;
		Native = jaxb.Native;
		NativeIsland = jaxb.NativeIsland;
		other = jaxb.other;
		ageMax = jaxb.ageMax;
		ageMin = jaxb.ageMin;
		
	}
	
	private static boolean chance(int i)
	{
		Random r = new Random();
		if (r.nextInt(100) < i) return true;
		else
			return false;
	}
	
	public String getMiddle()
	{
		String middle_Value = null;
		Random random = new Random();
		String[] middle = { "A.", "B.", "C.", "D.", "E.", "F.", "G.", "H.", "I.", "J.", "K.", "L.",
		        "M.", "N.", "O.", "P.", "Q.", "R.", "S.", "T.", "U.", "V.", "W.", "X.", "Y.",
		        "Z." };
		int select = random.nextInt(middle.length);
		int percentage = random.nextInt(100);
		if (percentage < 90) middle_Value = middle[select];
		else
			middle_Value = "";
		
		return middle_Value;
	}
	
	public Sheet FirstName_Type(String gender, String race, Workbook FirstNames) 
	// identify which first name sheet to pick based on race and gender
	{
		
		if (gender == "Male")
		{
			if (race == "white") return FirstNames.getSheet("WhiteBoy");
			else if (race == "Black") return FirstNames.getSheet("BlackBoy");
			else if (race == "Asian") return FirstNames.getSheet("AsianBoy");
			else if (race == "Latino") return FirstNames.getSheet("LatinoBoy");
			else
				return FirstNames.getSheet("OtherBoy");
		}
		else
		{
			if (race == "white") return FirstNames.getSheet("WhiteGirl");
			else if (race == "Black") return FirstNames.getSheet("BlackGirl");
			else if (race == "Asian") return FirstNames.getSheet("AsianGirl");
			else if (race == "Latino") return FirstNames.getSheet("LatinoGirl");
			else
				return FirstNames.getSheet("OtherGirl");
		}
		
	}
	
	public Sheet LastName_Type(String race, Workbook lastNames)
	// which last name sheet to pick based on race***no need to consider gender
	{
		
		if (race == "white") return lastNames.getSheet("WhiteLast");
		else if (race == "Black") return lastNames.getSheet("BlackLast");
		else if (race == "Asian") return lastNames.getSheet("AsianLast");
		else if (race == "Latino") return lastNames.getSheet("LatinoLast");
		else
			return lastNames.getSheet("OtherLast");
	}
	
	public String getFirstName(Sheet firstName)
	{
		int firstNameRowNumber = firstName.getLastRowNum();
		Random random = new Random();
		int firstName_Random = random.nextInt(firstNameRowNumber) + 1;
		Row firstName_Row = firstName.getRow(firstName_Random);
		Cell firstName_Cell = firstName_Row.getCell(0); // get the first cell
		String firstName_Value = firstName_Cell.toString();
		return firstName_Value;
	}
	
	public String getLastName(Sheet lastName)
	{
		int lastNameRowNumber = lastName.getLastRowNum();
		Random random = new Random();
		int lastName_Random = random.nextInt(lastNameRowNumber) + 1;
		Row lastName_Row = lastName.getRow(lastName_Random); // get random row
		Cell lastName_Cell = lastName_Row.getCell(0); // get the first cell
		String lastName_Value = lastName_Cell.toString();
		return lastName_Value;
	}
	
	public String getGender()// random gender //undecided
	{
		Random random = new Random();
		int select = random.nextInt(100);// percentage pool, if the white% is
		                                 // 60%, and random num is less than
		                                 // 60, than its white
		if (select < genderMale) return "Male";
		else
			return "Female";
	}
	
	public String getRace()// generate a race
	{
		Random random = new Random();
		int select = random.nextInt(100);
		if (select < white) return "White";
		else if (select < (white + black)) return "Black";
		else if (select < (white + black + asian)) return "Asian";
		else if (select < (white + black + asian + latino)) return "Latino";
		else if (select < (white + black + asian + latino + Native))
		    return "Native American and Alaska Natives";
		else if (select < (white + black + asian + latino + Native + NativeIsland))
		    return "Native Hawaiians and Other Pacific Islanders";
		else
			return "Two or moew races";
	}
	
	public int randBetween(int start, int end)
	{
		return start + (int) Math.round(Math.random() * (end - start));
	}
	
	public int DOB(int ageMin, int ageMax)
	{
		GregorianCalendar gc = new GregorianCalendar();
		LocalDate now = new LocalDate();
		int fromYear = now.getYear() - ageMax;
		int toYear = now.getYear() - ageMin;
		
		int year = randBetween(fromYear, toYear);
		
		gc.set(Calendar.YEAR, year);
		
		int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
		
		gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
		
		this.DOB = gc.get(Calendar.YEAR) + "-" + (gc.get(Calendar.MONTH) + 1) + "-"
		        + gc.get(Calendar.DAY_OF_MONTH);
		
		LocalDate bd = new LocalDate(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH) + 1,
		        gc.get(Calendar.DAY_OF_MONTH));
		
		Years age = Years.yearsBetween(bd, now);
		int a = age.getYears();
		return a;
	}
	
	public int DOBChangeDays(String date, int i)
	{
		
		DateTime d = DateTime.parse(date);
		
		if (chance(50)) d = d.plusDays(i);
		else
			d = d.minusDays(i);
		
		this.DOB = d.getYear() + "-" + d.getMonthOfYear() + "-" + d.getDayOfMonth();
		LocalDate bd = new LocalDate(d.getYear(), d.getMonthOfYear(), d.getDayOfMonth());
		LocalDate now = new LocalDate();
		Years age = Years.yearsBetween(bd, now);
		int a = age.getYears();
		return a;
		
	}
	
	public String getSSN()
	{
		Random random = new Random();
		int SSN_First = random.nextInt(900) + 100;
		int SSN_Middle = random.nextInt(99) + 1;
		int SSN_Last = random.nextInt(9999) + 1;
		String SSN = SSN_First + "-" + String.format("%1$02d", SSN_Middle) + "-"
		        + String.format("%1$04d", SSN_Last);
		return SSN;
	}
	
	@SuppressWarnings("deprecation")
	public String getAddressNumber(Sheet address)
	{
		int row_Number = address.getLastRowNum();
		Random random = new Random();
		int select = random.nextInt(row_Number) + 1;// starts from row 2
		Row addressNum_Row = address.getRow(select);
		Cell addressNum_Cell = addressNum_Row.getCell(0);
		addressNum_Cell.setCellType(Cell.CELL_TYPE_STRING);
		String address_Number = addressNum_Cell.toString();
		return address_Number;
		
	}
	
	public String getAddressStreet(Sheet address)
	{
		int row_Number = address.getLastRowNum();
		Random random = new Random();
		int select = random.nextInt(row_Number) + 1;// starts from row 2
		Row addressStreet_Row = address.getRow(select);
		Cell addressStreet_Cell = addressStreet_Row.getCell(1);
		String address_Street = addressStreet_Cell.toString();
		return address_Street;
		
	}
	
	public String getCity(Sheet address)
	{
		int row_Number = address.getLastRowNum();// starts from row 2
		Random random = new Random();
		int select = random.nextInt(row_Number) + 1;
		this.randomAddress = select;
		
		Row address_Row = address.getRow(select);
		String address_City = address_Row.getCell(0).toString();
		return address_City;
	}
	
	public String getState(Sheet address, int select)
	{
		Row address_Row = address.getRow(select);
		String address_State = address_Row.getCell(1).toString();
		return address_State;
	}
	
	@SuppressWarnings("deprecation")
	public String getZip(Sheet address, int select)
	{
		Row address_Row = address.getRow(select);
		address_Row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
		String address_Zip = address_Row.getCell(2).toString();
		return address_Zip;
	}
	
	public int getIncome(String race, String gender, int age)
	{
		Random r = new Random();
		int income = 0;
		if (age > 15 & age < 70)
		{
			if (gender == "Male")
			{
				if (race == "Asian") income = r.nextInt(50000) + 60000;
				else if (race == "White") income = r.nextInt(30000) + 50000;
				else if (race == "Black") income = r.nextInt(25000) + 30000;
				else if (race == "Latino") income = r.nextInt(20000) + 20000;
				else
					income = r.nextInt(25000) + 25000;
			}
			else
			{
				if (race == "Asian") income = r.nextInt(50000) + 50000;
				else if (race == "White") income = r.nextInt(30000) + 40000;
				else if (race == "Black") income = r.nextInt(25000) + 20000;
				else if (race == "Latino") income = r.nextInt(20000) + 15000;
				else
					income = r.nextInt(20000) + 20000;
			}
		}
		
		return income;
		
	}
	
	public String getInsurance()
	{
		Random r = new Random();
		if (chance(95)) return insurance[r.nextInt(insurance.length)];
		else
			return "";
		
	}
}
