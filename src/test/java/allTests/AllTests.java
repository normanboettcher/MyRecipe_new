package allTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({agentsTest.AllTests.class, generalTest.AllTests.class,
	managersTest.AllTests.class})
public class AllTests {

}
