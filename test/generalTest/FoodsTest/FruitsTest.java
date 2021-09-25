package generalTest.FoodsTest;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import general.Food;

public class FruitsTest {
	
	private Food banana;
	
	@Before
	public void initFruitObjects() {
		banana = new Food("Banane", 1.99, "Chiquita", "", 1, 1, 1, 1);
	}
	
	@Test
	public void getVeggyTest() {
		assertTrue(banana.getVeggy() == true);
		assertFalse(banana.getVeggy() == false);
	}
	
	@Test
	public void getVeganTest() {
		assertTrue(banana.getVegan() == true);
		assertFalse(banana.getVegan() == false);
	}
}
