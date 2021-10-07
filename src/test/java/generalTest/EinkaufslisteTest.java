package generalTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import general.Adresse;
import general.Einkaufsliste;
import general.Food;
import general.User;
import managers.DatumsManagers;

public class EinkaufslisteTest {
	
	private Einkaufsliste liste;
	private Food food;
	User usr;
	
	@Before
	public void setUp() throws Exception {
		usr = new User(1, "Max", "Mustermann", "email@web.de", new Adresse("", "", "", ""), "pw");
		liste = new Einkaufsliste(1, DatumsManagers.aktuellesDatum());
		food = new Food("Banane", 2, "Biofarm", "", 1, 1, 0, 1, "Obst");
		food.setArtikelNr(1);
		liste.addProduktZuListe(food, 2);
		liste.berechneGesamtpreis(liste.getProduktliste());
		
		usr.setEinkaufsliste(liste);
	}

	@Test
	public void berechneGesamtPreisTest() {
		
		assertTrue(liste.getGesamtPreis() == 4);
		assertFalse(liste.getGesamtPreis() == 2);
	}
	
	@Test
	public void getEinkaufslisteIDTest() {
		assertTrue(liste.getEinkaufslisteID() == 1);
	}
	
	@Test
	public void getEinkaufslisteDateTest() {
		System.out.println(liste.getEinkaufslisteDate());
	}
	
	@Test
	public void getUserTest() {
		assertFalse(liste.getUser() == null);
		assertTrue(liste.getUser().getVorname() == "Max");
		
	}
	
	@Test
	public void getProduktlisteTest() {
		assertTrue(liste.getProduktliste() != null);
	}
	
	@Test
	public void getProdukteMitMengeTest() {
		assertTrue(liste.getProdukteMitMenge() != null);
	}

}
