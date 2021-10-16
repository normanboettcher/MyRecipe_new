package generalTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import general.Adresse;

public class AdresseTest {
	
	private Adresse adrs;
	
	@Before
	public void setUpBeforeClass() throws Exception {
		adrs = new Adresse("Strasse", "12a", "12345", "Bonn");
	}

	@Test
	public void getStrasseTest() {
		assertTrue(adrs.getStreet().equals("Strasse"));
	}
	
	@Test
	public void getNumberTest() {
		assertTrue(adrs.getNumber().equals("12a"));
	}
	
	@Test
	public void getPLZTest() {
		assertTrue(adrs.getPLZ().equals("12345"));
	}
	
	@Test
	public void getCityTest() {
		assertTrue(adrs.getCity().equals("Bonn"));
	}

}
