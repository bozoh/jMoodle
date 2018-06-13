package ml.jmoodle.tests.configs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ml.jmoodle.authentications.MoodleAuthentication;
import ml.jmoodle.commons.MoodleCourse;
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

	@Test
	public final void MoodleConfigVerifyVersionInvalidTest() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		MoodleCourse mc = new MoodleCourse();
        mc.setId(1l);   
        mc.setFullname("fullname");
        mc.setShortname("shortname");
        mc.setStartDate(Calendar.getInstance().getTimeInMillis()/1000l);
		Method[] allMethods = mc.getClass().getDeclaredMethods();
        for (Method m : allMethods) {
            if (m.getName().startsWith("get")) {
				System.out.println(m.getName().toLowerCase().substring(3)+" = "+m.invoke(mc));
			}
		}

		Map<String, Object> map =  new HashMap<>();
		map.put("id", 1);
		map.put("fullname", "fullname");
		map.put("shortname", "shortname");
		map.put("startdate", String.valueOf(Calendar.getInstance().getTimeInMillis()/1000l));
		MoodleCourse mc1 = new MoodleCourse();
		
		System.out.println(mc1);

	}
}
