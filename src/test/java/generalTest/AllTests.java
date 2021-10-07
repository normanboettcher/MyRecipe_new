package generalTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import generalTest.*;

@RunWith(Suite.class)
@SuiteClasses({ EinkaufslisteTest.class, SupermarktTest.class, UserTest.class })
public class AllTests {

}
