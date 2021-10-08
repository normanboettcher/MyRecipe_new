package generalTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import general.Food;
import general.supermarkets.Lidl;

public class SupermarktTest {
	
	private Lidl lidl; 
	
	@Before
	public void initSupermarkt() {
		lidl = new Lidl();
		
		for(int i = 0; i < 100; i++) {
			lidl.addProduktToSortiment(
					i, new Food("Food", 2.99 + i * Math.random() * 10, "Rewe", null, 1, 1, 1, 1, "Obst"));
		}
		
		for(int i = 0; i < 50; i++) {
			lidl.addAngebot(i,  new Food("Food", 1.99 + i * Math.random() * 10, "Rewe", null, 1, 1, 1, 1, "Obst"));
		}
	}

	@Test
	public void getSortimentTest() {
		assertTrue(lidl.getSortiment() != null);
		assertTrue(lidl.getSortiment().size() == 100);
	}
	
	@Test
	public void getSortimentByKeyTest() {
		assertTrue(lidl.getSortimentByKey(2) != null);
		assertTrue(lidl.getSortimentByKey(2) instanceof Food);
		assertTrue(lidl.getSortimentByKey(2).getArtikelNr() == 2);
	}
	
	@Test
	public void addAngebotTest() {
		lidl.addAngebot(100, new Food("Food", 0.99, "Rewe", null, 1, 1, 1, 1, "Obst"));
		
		assertTrue(lidl.getAngebote().get(100).getPreis() == 0.99);
		assertTrue(lidl.getAngebote().size() == 51);
	}
	
	@Test
	public void getAngeboteTest() {
		assertTrue(lidl.getAngebote() != null);
		assertTrue(lidl.getAngebote().size() == 50);
		assertTrue(lidl.getAngebote().get(2) instanceof Food);
	}
	
	@Test
	public void getAngebotByKeyTest() {
		
		lidl.addAngebot(198, new Food("Essen", 0.99  * Math.random() * 10, "Rewe", null, 1, 1, 1, 1, "Obst"));
		
		assertTrue(lidl.getAngebotByKey(198).getBezeichnung().equals("Essen"));
		assertTrue(lidl.getAngebotByKey(2) != null);
		assertTrue(lidl.getAngebotByKey(2) instanceof Food);
		assertTrue(lidl.getAngebotByKey(2).getArtikelNr() == 2);
	}
	
	@Test
	public void removeSortimentByKeyTest() {
		lidl.removeSortimentByKey(4);
		assertTrue(lidl.getSortimentByKey(4) == null);
		assertTrue(lidl.getSortiment().size() == 99);
	}
	
	@Test
	public void removeAngebotByKeyTest() {
		lidl.removeAngebotByKey(4);
		assertTrue(lidl.getAngebotByKey(4) == null);
		assertTrue(lidl.getAngebote().size() == 49);
	}
}
