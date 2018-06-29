package ml.jmoodle.tests.configs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ml.jmoodle.authentications.MoodleAuthentication;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;

/**
 * 
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
// @RunWith(PowerMockRunner.class)
// @PrepareForTest({ MoodleConfig.class })
@RunWith(MockitoJUnitRunner.class)
public class MoodleConfigTest {

	@Mock
	MoodleAuthentication mdlAuthMock;

	@Before
	public void setUp() throws Exception {
		when(mdlAuthMock.getAuthentication()).thenReturn("wstoken=MYTOKEN");
	}

	@Test
	public final void MoodleConfigGetURLTest() throws Exception {
		URL testUrl = new URL("http://test.com");

		MoodleConfig config2 = new MoodleConfig("http://test.com", mdlAuthMock, "3.1.0");
		assertEquals(testUrl.toString() + "/webservice/rest/server.php?wstoken=MYTOKEN", 
			config2.getMoodleURL().toString());

		MoodleConfig config3 = new MoodleConfig("test.com", mdlAuthMock,"3.1.0");
		assertEquals(testUrl.toString() + "/webservice/rest/server.php?wstoken=MYTOKEN",
			config3.getMoodleURL().toString());
	}

	@Test
	public final void MoodleConfigGetVersionTest() throws Exception {
		MoodleConfig config = new MoodleConfig("http://test.com", mdlAuthMock, "3.1.4");
		assertEquals("3.1.4", config.getVersion());
	}

	

	@Test(expected = MoodleConfigException.class)
	public final void MoodleConfigVerifyVersionInvalidMoodleVersionTest() throws MoodleConfigException {
		new MoodleConfig("http://test.com", mdlAuthMock, "3.1-RC"); 
	}

	
}
