package ml.jmoodle.config;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * 
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
public class MoodleConfigTest {

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	public static void setUpBeforeClass() throws Exception {
		// MockitoAnnotations.initMocks(MoodleConfigTest.class);
	}

	public static void tearDownAfterClass() throws Exception {
	}

	public void setUp() throws Exception {
	}

	public void tearDown() throws Exception {
	}

	@Test
	public final void MoodleConfigGetURLTest() throws MalformedURLException {
		URL testUrl = new URL("http://test.com");
		
		MoodleConfig config2=new MoodleConfig("http://test.com", "3.1.0");
		assertEquals(testUrl.toString()+"/webservice/rest/server.php", config2.getMoodleURL().toString());
		
		MoodleConfig config3=new MoodleConfig("test.com", "3.1.0");
		assertEquals(testUrl.toString()+"/webservice/rest/server.php", config3.getMoodleURL().toString());


//	    Will never throw MalformedURLException because the proctocol is always secified.
//		exception.expect(java.net.MalformedURLException.class);
//		new URL("http");
////		new MoodleConfig("<invaid-           %Wccc fez URL>");
		
		
	}
	
	@Test
	public final void MoodleConfigGetVersionTest() throws MalformedURLException {
		MoodleConfig config=new MoodleConfig("http://test.com", "3.1.4");
		assertEquals(3, config.getMajorVersion());
		assertEquals(1, config.getMinorVersion());
		assertEquals(4, config.getPatchVersion());
		assertEquals(3.14, config.getVersion(),0);
	}

}
