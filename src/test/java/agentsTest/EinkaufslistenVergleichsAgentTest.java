package agentsTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import agents.EinkaufslistenVergleichsAgent;
import general.Adresse;
import general.Einkaufsliste;
import general.Food;
import general.User;
import managers.DatumsManager;
import managers.DoubleManager;

public class EinkaufslistenVergleichsAgentTest {
	
	private Einkaufsliste liste;
	private Food food;
	private User usr;
		/*
	@Before
	public void setUp() throws Exception {
		
		agent = new EinkaufslistenVergleichsAgent();
		usr = new User(1, "Max", "Mustermann", "email@web.de", new Adresse("", "", "", ""), "pw");
		
		for(int i = 0; i < 10; i++) {
			liste = new Einkaufsliste(i + 5, DatumsManager.aktuellesDatum());

			food = new Food("Banane", 2 * i + 1 + DoubleManager.round(Math.random() * 10, 2),
					"Biofarm", "", 1, 1, 0, 1, "Obst");
			
			food.setArtikelNr(i);
			liste.addProduktZuListe(food, 2);
			liste.berechneGesamtpreis();

			usr.setEinkaufsliste(liste);
		}
	}

	@Test
	public void sortiereNachBilligstenFuenfTest() {
		assertTrue(agent.getEinkaufslistenSortiertNachPreis().isEmpty());
		
		//Vergleich der Einkaufslisten wird durchgefuehrt.
		agent.vergleicheEinkaufslisten(usr.getEinkaufslisteHistorie());
		agent.getEinkaufslistenSortiertNachPreis();
		
		assertTrue(agent.getEinkaufslistenSortiertNachPreis().size() == 5);
	}*/
}
