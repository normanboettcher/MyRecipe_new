package generalTest.FoodsTest;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import general.foods.Fruit;

public class FruitsTest {
	
	private Fruit banana;
	
	@Before
	public void initFruitObjects() {
		banana = new Fruit("Banane", 1.99, "Chiquita");
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
