package ml.jmoodle.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorCompletionService;

import static org.mockito.Mockito.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

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
	public final void MoodleConfigConstructorTest() throws MalformedURLException {
		URL testUrl = new URL("http://test.com");
		MoodleConfig config=new MoodleConfig(testUrl);
		assertEquals(testUrl.toString()+"/webservice/rest/server.php", config.getMoodleURL().toString());
		
		
		MoodleConfig config2=new MoodleConfig("http://test.com");
		assertEquals(testUrl.toString()+"/webservice/rest/server.php", config2.getMoodleURL().toString());
		
		MoodleConfig config3=new MoodleConfig("test.com");
		assertEquals(testUrl.toString()+"/webservice/rest/server.php", config3.getMoodleURL().toString());


//	    Will never throw MalformedURLException because the proctocol is always secified.
//		exception.expect(java.net.MalformedURLException.class);
//		new URL("http");
////		new MoodleConfig("<invaid-           %Wccc fez URL>");
		
		
	}

}
