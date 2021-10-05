package generalTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import general.Food;
import general.supermarkets.Lidl;
import managers.DoubleManager;

public class SupermarktTest {
	
	private Lidl lidl; 
	
	@Before
	public void initSupermarkt() {
		lidl = new Lidl();
		
		for(int i = 0; i < 100; i++) {
			lidl.addProdukt(i, new Food("Food", 2.99 + i * Math.random() * 10, "Rewe", null, 1, 1, 1, 1, "Obst"));
		}
	}

	@Test
	public void initAngeboteTest() {
		
		double high = 0.3;
		double low = 0.15;
		int angeboteAnzahl = 0;
		
		for(int i = 0; i < 100; i ++) { 
			//lidl.initAngebote(); 
			//System.out.println((lidl.getRabatt() * 100));
			//System.out.println((int)(Math.random() * (100 - 2) + 2));
			//angeboteAnzahl = (int) (100* (Math.random() * (high - low) + low));
			//System.out.println(angeboteAnzahl);
		}
		
		System.out.println(DoubleManager.round(100 - (100 * 0.28), 2));
		

		
	}
}
