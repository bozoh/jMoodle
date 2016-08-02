package ml.jmoodle.configs;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.hamcrest.CoreMatchers.*;

import java.net.URL;
import java.util.regex.Matcher;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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
@RunWith(PowerMockRunner.class)
@PrepareForTest({ MoodleConfig.class })
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

	@Before
	public void setUp() throws Exception {
		Matcher matcherMck = PowerMockito.mock(Matcher.class);
		PowerMockito.spy(MoodleConfig.class);
		PowerMockito.doReturn(matcherMck).when(MoodleConfig.class, "verifyVersion", anyString());

		
	}

	public void tearDown() throws Exception {
	}

	@Test
	public final void MoodleConfigGetURLTest() throws Exception {
		URL testUrl = new URL("http://test.com");

		MoodleConfig config2 = new MoodleConfig("http://test.com", "3.1.0");
		assertEquals(testUrl.toString() + "/webservice/rest/server.php", config2.getMoodleURL().toString());

		MoodleConfig config3 = new MoodleConfig("test.com", "3.1.0");
		assertEquals(testUrl.toString() + "/webservice/rest/server.php", config3.getMoodleURL().toString());
	}

	@Test
	public final void MoodleConfigGetVersionTest() throws Exception {
		MoodleConfig config = new MoodleConfig("http://test.com", "3.1.4");
		assertEquals("3.1.4", config.getVersion());
	}

	@Test
	public final void MoodleConfigVerifyVersionIsCalledTest() throws Exception {
		MoodleConfig config = new MoodleConfig("http://test.com", "3.1.1");
		PowerMockito.verifyStatic();
		MoodleConfig.verifyVersion(eq("3.1.1"));
	}

	@Test(expected = MoodleConfigException.class)
	public final void MoodleConfigVerifyVersionInvalidMoodleVersionTest() throws MoodleConfigException {
		PowerMockito.when(MoodleConfig.verifyVersion(anyString())).thenCallRealMethod();
		MoodleConfig.verifyVersion("3.1-RC");
	}

	public final void MoodleConfigVerifyVersionTest() throws MoodleConfigException {
		PowerMockito.when(MoodleConfig.verifyVersion(anyString())).thenCallRealMethod();
		Assert.assertThat(MoodleConfig.verifyVersion("3.1.1"), isA(Matcher.class));
	}

}
