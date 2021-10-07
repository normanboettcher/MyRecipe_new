package generalTest;

import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;

import general.Adresse;
import general.Einkaufsliste;
import general.User;
import general.Food;
import managers.DatumsManagers;
import managers.PasswortManager;

public class UserTest {
	
	private User u;
	private Einkaufsliste list;
	Food f;
	
	@Before
	public void setUp() throws Exception {
		u = new User(22, "Max", "Test", "eine.mail@emailhaus.de", 
				new Adresse("Am Fluss", "22","99999" , "Hannokoeln"), 
				PasswortManager.generateHash("nichtverraten"));
		list = new Einkaufsliste(11, DatumsManagers.aktuellesDatum());
		
		for(int i = 0; i < 60; i++) {
			f = new Food("Apfel", 1.99, "Rewe", "", 1, 1,1, 1, "Obst");
			f.setArtikelNr(i + 1);
			list.addProduktZuListe(f, 2);
		}
		u.setEinkaufsliste(list);
		u.addEinkaufslisteZuHistorie(list);
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

}
