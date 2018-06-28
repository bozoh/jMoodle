package ml.jmoodle.tests.functions.rest.enrol;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.commons.MoodleWarning;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.enrol.self.MoodleRestSelfEnrolUser;

/**
 * enrol_self_enrol_user
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MoodleRestSelfEnrolUserTest  {
	
	private static final String MOODLE_TEST_URL = "http://teste.com";
	@Mock
	MoodleConfig mc;
	
	
	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("ml.jmoodle.tests");
	}
	
	@Before
	public void beforeEach() throws MalformedURLException {
		URL testUrl = new URL(MOODLE_TEST_URL);
		when(mc.getMoodleURL()).thenReturn(testUrl);
		when(mc.getVersion()).thenReturn(new String ("3.0.0"));
	}
	
	@Test
	public void verify_since_version_test() throws MoodleWSFucntionException {
		MoodleRestSelfEnrolUser cc = (MoodleRestSelfEnrolUser) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.ENROL_SELF_ENROL_USER, mc
		);
		assertThat(cc.getSinceVersion()).isEqualTo("3.0.0");
	}

	@Test
	public void get_instance_without_errors_test() {
		try {
			assertThat(MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.ENROL_SELF_ENROL_USER, mc
			)).isInstanceOf(MoodleRestSelfEnrolUser.class);
		} catch (MoodleWSFucntionException e) {
			fail("An exception was throwed"+e.getMessage());
		}
	}

	@Test
	public void exception_if_moodle_version_is_lower_than_3_0_0_test () {
		when(mc.getVersion()).thenReturn(new String ("2.9.9"));
		try {
			MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.ENROL_SELF_ENROL_USER, mc
			);
		} catch (MoodleWSFucntionException e) {
			assertNotNull(e);
			return;
		}
		fail("No exception was throwed");
	}

	
	@Test
	public void verify_function_name() throws MoodleWSFucntionException{
		
		MoodleRestSelfEnrolUser cc = (MoodleRestSelfEnrolUser) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.ENROL_SELF_ENROL_USER, mc
		);
		assertThat(cc.getFunctionName()).isEqualTo("enrol_self_enrol_user");
	}

	@Test
	public void exception_if_course_is_not_set_test() throws MoodleWSFucntionException {
		try {
			MoodleRestSelfEnrolUser cc = (MoodleRestSelfEnrolUser) MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.ENROL_SELF_ENROL_USER, mc
			);
			cc.doCall();
		} catch (MoodleWSFucntionException e) {
			assertNotNull(e);
			return;
		}
		fail("No exception was throwed");
		
	}

	@Test
	public void verify_function_data_test() throws MoodleWSFucntionException, UnsupportedEncodingException {
	
		MoodleRestSelfEnrolUser cc = (MoodleRestSelfEnrolUser) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.ENROL_SELF_ENROL_USER, mc
		);
		String pass = "XpT0";
		Long instanceId = 99l;

		MoodleCourse course = Fixture.from(MoodleCourse.class).gimme("valid");
		cc.setCourse(course);
		String dataString = URLDecoder.decode(cc.getFunctionData(), MoodleConfig.DEFAULT_ENCODING);
		
		assertTrue(dataString.contains("wsfunction="+cc.getFunctionName()));
		assertThat(dataString).contains("courseid="+course.getId());
		assertThat(dataString).doesNotContain("password=");
		assertThat(dataString).doesNotContain("instanceid=");

		cc.setPassword(pass);
		cc.setInstanceId(instanceId);
		
		dataString = URLDecoder.decode(cc.getFunctionData(), MoodleConfig.DEFAULT_ENCODING);
		
		assertTrue(dataString.contains("wsfunction="+cc.getFunctionName()));

		assertThat(dataString).contains("courseid="+course.getId());
		assertThat(dataString).contains("password="+pass);
		assertThat(dataString).contains("instanceid="+instanceId);

	}

	@Test
	public void verify_process_respose_test() throws MoodleWSFucntionException, MoodleConfigException, ParserConfigurationException, SAXException, IOException  {
	
		TestMoodleFunctionWarpClass cc = new TestMoodleFunctionWarpClass(mc);
		MoodleCourse course = Fixture.from(MoodleCourse.class).gimme("valid");
		List<MoodleWarning> warns = Fixture.from(MoodleWarning.class).gimme(7, "valid");
		cc.setCourse(course);
		
		Document response = EnrolFunctionsFixtureTemplate.getEnrolSelfUserResponse(warns);
		assertThat(cc.processResponse(response)).isTrue();
		assertThat(cc.hasWarnings()).isTrue();
		doProcessResponseAssertions(warns, cc.getWarnings());
	}
	
	
	private void doProcessResponseAssertions(Collection<MoodleWarning> expected, Set<MoodleWarning> actual) {
		assertThat(actual).hasSize(expected.size());
		for (MoodleWarning entiy : expected) {
			assertThat(actual).contains(entiy);
		}
		
	}


	class TestMoodleFunctionWarpClass extends MoodleRestSelfEnrolUser {
		
		public TestMoodleFunctionWarpClass(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
				super(moodleConfig);
			}
		

		public Boolean processResponse(Document response)  {
			return super.processResponse(response);
		}
		
			
	}
			
}