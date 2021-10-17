package agentsTest;

import java.util.HashMap;

import org.junit.Before;

import agents.AngeboteAgent;
import general.Einkaufsliste;
import general.Food;
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

}
