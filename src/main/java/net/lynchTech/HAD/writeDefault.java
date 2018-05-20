package net.lynchTech.HAD;

import javax.xml.bind.JAXBException;

public class writeDefault {

	public static void main(String[] args) {

		JAXB j = new JAXB();
		SetXML s = new SetXML();
		try
		{
			j.write("default.had", s);
		}
		catch (JAXBException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
