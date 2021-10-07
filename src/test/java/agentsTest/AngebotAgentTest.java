package agentsTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import agents.AngeboteAgent;
import general.Einkaufsliste;
import general.Food;
import general.supermarkets.Lidl;
import managers.DatumsManager;

public class AngebotAgentTest {
	
	private AngeboteAgent a_agent;
	private HashMap<Integer, Food> sortiment;
	private HashMap<Integer, Food> angebote;
	private Food f;
	private Food f_angebot;
	private Einkaufsliste liste;
	
	
	@Before
	public void setUp() throws Exception {
		a_agent = new AngeboteAgent();
		sortiment = new HashMap<Integer, Food>();
		angebote = new HashMap<Integer, Food>();
		liste = new Einkaufsliste(1, DatumsManager.aktuellesDatum());
		
		for(int i = 0; i < 5; i++) {
			f = new Food("Apfel", 1.99, "Rewe", "", 1, 1,1, 1, "Obst");
			f.setArtikelNr(i);
			sortiment.put(f.getArtikelNr(), f);
			liste.addProduktZuListe(f, 2);
			//System.out.println(liste.getProduktliste().get(f.getArtikelNr()).getBezeichnung());
		}
		
		f_angebot = new Food("Apfel", 0.99, "Rewe", "", 1, 1,1, 1, "Obst");
		f_angebot.setArtikelNr(2);
		
		angebote.put(f_angebot.getArtikelNr(), f_angebot);

	}

	@Test
	public void produktImAngebotTest() {
		assertTrue(a_agent.produktImAngebot(sortiment, angebote, 2) == true);
		assertFalse(a_agent.produktImAngebot(sortiment, angebote, 3) == true);
	}
	
	@Test
	public void tauscheEinkaufslisteProduktMitAngebotProduktTest() {
		
		assertTrue(liste.getProduktliste().get(2).getPreis() == 1.99);
		
		if (a_agent.produktImAngebot(sortiment, angebote, 2)) {
			a_agent.tauscheEinkaufslisteProduktMitAngebotProdukt(liste);
		}
		
		assertTrue(liste.getProduktliste().get(2).getPreis() == 0.99);
	}
	
	@Test
	public void getGespartTest() {
		
		if (a_agent.produktImAngebot(sortiment, angebote, 2)) {
			a_agent.tauscheEinkaufslisteProduktMitAngebotProdukt(liste);
		}
		a_agent.berechneErsparung(liste);
		
		System.out.println(a_agent.getSortimentPreisZwischenspeicher());
		System.out.println(a_agent.getPreisEndergebnis());
	}
	
	@Test
	public void getErsparungInProzentTest() {

		if (a_agent.produktImAngebot(sortiment, angebote, 2)) {
			a_agent.tauscheEinkaufslisteProduktMitAngebotProdukt(liste);
		}
		a_agent.berechneErsparung(liste);
		a_agent.berechneErsparungInProzent();
		
		System.out.println("[ " + a_agent.getErsparungInProzent() + "] Prozent gespart");
		assertTrue((int) a_agent.getErsparungInProzent() == 11);
	}
}
