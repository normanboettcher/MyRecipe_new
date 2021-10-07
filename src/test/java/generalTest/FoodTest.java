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
	}
	
	@Test
	public void getBezeichnungTest() {
		assertTrue(f.getBezeichnung() == "Apfel");
	}
}
