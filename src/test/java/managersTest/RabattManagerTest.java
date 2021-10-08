package managersTest;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import managers.RabattManager;

public class RabattManagerTest {

	@Test
	public void getRandomRabattTest() {
		for(int i = 0; i < 100; i++) {
			assertTrue(RabattManager.getRandomRabatt() <= 0.50);
			assertTrue(RabattManager.getRandomRabatt() < 0.51);
			assertTrue(RabattManager.getRandomRabatt() > 0.14);
			assertTrue(RabattManager.getRandomRabatt() >= 0.15);
			
			assertFalse(RabattManager.getRandomRabatt() > 0.50);
			assertFalse(RabattManager.getRandomRabatt() < 0.01);
			assertFalse(RabattManager.getRandomRabatt() > 0.75);
			assertFalse(RabattManager.getRandomRabatt() <= 0.06);
		}
	}
	
	@Test
	public void abzugRabattTest() {
		assertTrue(RabattManager.abzugRabatt(0.5, 100) == 50);
		assertTrue(RabattManager.abzugRabatt(0.75, 100) == 25);
		assertTrue(RabattManager.abzugRabatt(0.33, 100) == 67);
		assertFalse(RabattManager.abzugRabatt(0.5, 100) == 0.50);
		assertFalse(RabattManager.abzugRabatt(0.5, 100) > 50);
		assertFalse(RabattManager.abzugRabatt(0.5, 100) < 50);
		assertFalse(RabattManager.abzugRabatt(0.5, 100) == 50.001);
	}

}
