package generalTest;

import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import general.Adresse;
import general.Einkaufsliste;
import general.User;
import general.Food;
import managers.DatumsManager;
import managers.PasswortManager;

public class UserTest {
	
	private User u;
	private User u_2;
	private Einkaufsliste list;
	private Einkaufsliste list_2;
	private Einkaufsliste list_3;
	private Food f;
	private Food f_1;
	private Food f_2;
	
	@Before
	public void setUp() throws Exception {
		
		u = new User(22, "Max", "Test", "eine.mail@emailhaus.de", 
				new Adresse("Am Fluss", "22","99999" , "Hannokoeln"), 
				PasswortManager.generateHash("nichtverraten"));
		list = new Einkaufsliste(11, DatumsManager.aktuellesDatum());
		
		for(int i = 0; i < 60; i++) {
			f = new Food("Apfel", 1.99, "Rewe", "", 1, 1,1, 1, "Obst");
			f.setArtikelNr(i + 1);
			list.addProduktZuListe(f, 2);
		}
		u.setEinkaufsliste(list);
		
		u_2 = new User(22, "Max", "Test", "eine.mail@emailhaus.de", 
				new Adresse("Am Fluss", "22","99999" , "Hannokoeln"), 
				PasswortManager.generateHash("nichtverraten"));
		
		for(int i = 0; i < 15; i++) {
			
			int year = 2000 + i;
			int month = 2;
			int day = 3;
			
			list_2 = new Einkaufsliste(i, new Date(year, month, day));
			f_1 = new Food("Apfel", 1.99, "Rewe", "", 1, 1,1, 1, "Obst");
			f_1.setArtikelNr(i + 1);
			list_2.addProduktZuListe(f_1, 2);
			u_2.setEinkaufsliste(list_2);
		}
		
		list_3 = new Einkaufsliste(100, new Date(2002, 2, 3));
		f_2 = new Food("Apfel", 1.99, "Rewe", "", 1, 1,1, 1, "Obst");
		f_2.setArtikelNr(1);
		list_3.addProduktZuListe(f_2, 2);
		u_2.setEinkaufsliste(list_3);
		
	}

	@Test
	public void getFullNameTest() {
		assertTrue(u.getFullName().equals("Max Test"));
		assertTrue(u.getFullName().equals(u.getVorname() + " " + u.getNachname()));
	}
	
	@Test
	public void getAdresseTest() {
		assertTrue(u.getAdresse() != null);
		assertTrue(u.getAdresse().getCity() == "Hannokoeln");
		assertTrue(u.getAdresse().getStreet() == "Am Fluss");
		assertTrue(u.getAdresse().getNumber() == "22");
		assertTrue(u.getAdresse().getPLZ() == "99999");
	}
	
	@Test
	public void getPasswortTest() throws NoSuchAlgorithmException {
		String pw = "nichtverraten";
		String pw_hash = PasswortManager.generateHash(pw);
		
		assertTrue(u.getPasswort() != null);
		assertTrue(pw_hash.equals(u.getPasswort()));
	}
	
	@Test
	public void getEinkaufslisteTest() {
		assertTrue(u.getEinkaufsliste()!= null);
		assertTrue(u.getEinkaufsliste().getProduktliste().size() == 60);
		
		for(int i : u.getEinkaufsliste().getProdukteMitMenge().keySet()) {
			assertTrue(u.getEinkaufsliste().getProdukteMitMenge().get(i) == 2); // Weil alle Produkte Menge zwei haben.
		}
	}
	
	@Test
	public void getEinkaufslisteHistorieTest() {
		assertTrue(u_2.getEinkaufslisteHistorie() != null);
		assertTrue(u_2.getEinkaufslisteHistorie().size() == 16);
	}

}
