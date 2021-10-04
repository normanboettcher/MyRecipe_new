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
			lidl.addProdukt(i, new Food("Food", 2.99 + i * Math.random() * 10, "Rewe", null, 1, 1, 1, 1, "Obst"));
		}
	}

	@Test
	public void initAngeboteTest() {
		
		for(int i = 0; i < 1000; i ++) { 
			lidl.initAngebote(); 
		}
	}
}
