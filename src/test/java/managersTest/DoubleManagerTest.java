package managersTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import managers.DoubleManager;

public class DoubleManagerTest {

	@Test
	public void roundTest() {
		assertTrue(DoubleManager.round(12.3333333333, 2) == 12.33);
	}

}
