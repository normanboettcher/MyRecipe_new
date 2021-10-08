package generalTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import general.Food;

public class FoodTest {
	
	Food f;
	
	@Before
	public void setUp() throws Exception {
		f = new Food("Apfel", 1.99, "Rewe", "", 1, 1,1, 1, "Obst");
		f.setArtikelNr(22);
		f.setRabatt(0.35);
		f.setOriginalPreis(1.99);
	}
	
	@Test
	public void getBezeichnungTest() {
		assertTrue(f.getBezeichnung() == "Apfel");
	}
	
	@Test
	public void getPreisTest() {
		assertTrue(f.getPreis() == 1.99);
	}
	
	 @Test
	 public void getArtikelNrTest() {
		 assertTrue(f.getArtikelNr() == 22);
	 }
	 
	 @Test
	 public void getHerstellerTest() {
		 assertTrue(f.getHersteller().equals("Rewe"));
	 }
	 
	 @Test
	 public void getVeganTest() {
		 assertTrue(f.getVegan() == 1);
	 }
	 
	 @Test
	 public void getBioTest() {
		 assertTrue(f.getBio() == 1);
	 }
	 
	 @Test
	 public void getVeggyTest() {
		 assertTrue(f.getVeggy() == 1);
	 }
	 
	 @Test
	 public void getLokalTest() {
		 assertTrue(f.getLokal() == 1);
	 }
	 
	 @Test
	 public void getImageTest() {
		 assertTrue(f.getImage() == "");
	 }
	 
	 @Test
	 public void getKategorieTest() {
		 assertTrue(f.getKategorie().equals("Obst"));
	 }
	 
	 @Test
	 public void getRabattTest() {
		 assertTrue(f.getRabatt() == 0.35);
	 }
	 @Test
	 public void getOriginPreisTest() {
		 assertTrue(f.getOriginPreis() == 1.99);
	 }
}
