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
import java.util.HashSet;
import java.util.Map;
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
import ml.jmoodle.commons.Capability;
import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.commons.OptionParameter;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.core.enrol.MoodleRestGetEnrolledUsersWithCapability;
import ml.jmoodle.functions.rest.core.enrol.exceptions.MoodleRestGetEnrolledUsersWithCapabilityException;

/**
 * Get list of course ids that a user is enrolled in (if allowed to)
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MoodleRestGetEnrolledUsersWithCapabilityTest  {
	
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
		when(mc.getVersion()).thenReturn(new String ("2.4.0"));
	}
	
	@Test
	public void verify_since_version_test() throws MoodleWSFucntionException {
		MoodleRestGetEnrolledUsersWithCapability cc = (MoodleRestGetEnrolledUsersWithCapability) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_ENROL_GET_ENROLLED_USERS_WITH_CAPABILITY, mc
		);
		assertThat(cc.getSinceVersion()).isEqualTo("2.4.0");
	}

	@Test
	public void get_instance_without_errors_test() {
		try {
			assertThat(MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_ENROL_GET_ENROLLED_USERS_WITH_CAPABILITY, mc
			)).isInstanceOf(MoodleRestGetEnrolledUsersWithCapability.class);
		} catch (MoodleWSFucntionException e) {
			fail("An exception was throwed"+e.getMessage());
		}
	}

	@Test
	public void exception_if_moodle_version_is_lower_than_2_4_0_test () {
		when(mc.getVersion()).thenReturn(new String ("2.3.9"));
		try {
			MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_ENROL_GET_ENROLLED_USERS_WITH_CAPABILITY, mc
			);
		} catch (MoodleWSFucntionException e) {
			assertNotNull(e);
			return;
		}
		fail("No exception was throwed");
	}

	@Test
	public void verify_function_name_test () throws MoodleWSFucntionException {
		
		MoodleRestGetEnrolledUsersWithCapability cc = (MoodleRestGetEnrolledUsersWithCapability) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_ENROL_GET_ENROLLED_USERS_WITH_CAPABILITY, mc
		);
		assertThat(cc.getFunctionName()).isEqualTo("core_enrol_get_enrolled_users_with_capability");
	}

	@Test
	public void exception_if_course_capabilities_is_not_set() {
		try {
			MoodleRestGetEnrolledUsersWithCapability func = (MoodleRestGetEnrolledUsersWithCapability) MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_ENROL_GET_ENROLLED_USERS_WITH_CAPABILITY, mc
			);
			func.getFunctionData();

		} catch (MoodleWSFucntionException e) {
			assertNotNull(e);
			return;
		}
		fail("No exception was throwed");
		
	}

	@Test
	public void verify_function_data_test() throws UnsupportedEncodingException, MoodleWSFucntionException {
	
		MoodleRestGetEnrolledUsersWithCapability cc = (MoodleRestGetEnrolledUsersWithCapability) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_ENROL_GET_ENROLLED_USERS_WITH_CAPABILITY, mc
		);
		MoodleCourse course1 = Fixture.from(MoodleCourse.class).gimme("valid");
		Set<Capability> capabilities1 = new HashSet<>();
		capabilities1.add(Capability.REPOSITORY_WIKIMEDIA_VIEW);
		capabilities1.add(Capability.ENROL_AUTHORIZE_CONFIG);
		capabilities1.add(Capability.BLOCK_ONLINE_USERS_ADDINSTANCE);
		capabilities1.add(Capability.MOD_ASSIGN_ADDINSTANCE);
		cc.addCourseCapabilities(course1, capabilities1);
		
		MoodleCourse course2 = Fixture.from(MoodleCourse.class).gimme("valid");
		Set<Capability> capabilities2 = new HashSet<>();
		capabilities2.add(Capability.MOD_ASSIGN_GRADE);
		capabilities2.add(Capability.GRADEEXPORT_ODS_PUBLISH);
		capabilities2.add(Capability.MOD_SURVEY_PARTICIPATE);
		
		cc.addCourseCapabilities(course2, capabilities2);

		Set<OptionParameter> opts = new HashSet<>(Fixture.from(OptionParameter.class).gimme(5, "valid"));
		cc.setOptions(opts);

		String dataString = URLDecoder.decode(cc.getFunctionData(), MoodleConfig.DEFAULT_ENCODING);
		
		assertTrue(dataString.contains("wsfunction="+cc.getFunctionName()));
		doCapabilitiesAssertions(dataString, course1, 0, capabilities1);
		doCapabilitiesAssertions(dataString, course2, 1, capabilities2);

		doOptionsAssertions(dataString, opts);
		
	}
	
	private void doCapabilitiesAssertions(String dataString, MoodleCourse course, int courseCount, Set<Capability> capabilities) {
		// coursecapabilities[0][courseid]= int
		// coursecapabilities[0][capabilities][0]= string
		int i = 0;
		assertThat(dataString).contains("coursecapabilities[" + 
				courseCount + "][courseid]=");
		assertThat(dataString).contains(course.getId().toString());
		for (Capability cap : capabilities) {
			assertThat(dataString).contains("coursecapabilities[","][capabilities]["+i+"]=");
			assertThat(dataString).contains(cap.getValue());
			i++;
		}
	}

	private void doOptionsAssertions(String dataString, Set<OptionParameter> opts) {
		int i = 0;
		for (OptionParameter op : opts) {
			assertThat(dataString).contains("options["+i+"][name]=");
			assertThat(dataString).contains(op.getName());
			assertThat(dataString).contains("options["+i+"][value]=");
			assertThat(dataString).contains(op.getValue());
			i++;
		}
	}

	@Test
	public void verify_process_respose_test() throws MoodleWSFucntionException, MoodleConfigException, SAXException, IOException, ParserConfigurationException {
		TestMoodleFunctionWarpClass cc = new TestMoodleFunctionWarpClass(mc);
		
		MoodleCourse course1 = Fixture.from(MoodleCourse.class).gimme("valid");
		Set<Capability> capabilities1 = new HashSet<>();
		capabilities1.add(Capability.REPOSITORY_WIKIMEDIA_VIEW);
		capabilities1.add(Capability.ENROL_AUTHORIZE_CONFIG);
		capabilities1.add(Capability.BLOCK_ONLINE_USERS_ADDINSTANCE);
		capabilities1.add(Capability.MOD_ASSIGN_ADDINSTANCE);
		cc.addCourseCapabilities(course1, capabilities1);
		Set<MoodleUser> users1 = new HashSet<>(Fixture.from(MoodleUser.class).gimme(2, "getEnrolledUsers"));
		
		MoodleCourse course2 = Fixture.from(MoodleCourse.class).gimme("valid");
		Set<Capability> capabilities2 = new HashSet<>();
		capabilities2.add(Capability.MOD_ASSIGN_GRADE);
		capabilities2.add(Capability.GRADEEXPORT_ODS_PUBLISH);
		capabilities2.add(Capability.MOD_SURVEY_PARTICIPATE);
		cc.addCourseCapabilities(course2, capabilities2);
		Set<MoodleUser> users2 = new HashSet<>(Fixture.from(MoodleUser.class).gimme(5, "getEnrolledUsers"));
				
		Document response = EnrolFunctionsFixtureTemplate
			.getEnrolledUsersWithCapabilityResponse(course1, capabilities1, users1,
				course2, capabilities2, users2);
		Map<Long, Map<Capability, Set<MoodleUser>>> resp = cc.processResponse(response);
		capabilities1.addAll(capabilities2);
		users1.addAll(users2);
		Set<Long> courses = new HashSet<>();
		courses.add(course1.getId());
		courses.add(course2.getId());
		doProcessResponseAssertions(courses, capabilities1, users1, resp);
	}
	
	
	private void doProcessResponseAssertions(Set<Long>  courses, Set<Capability> capabilities,
			Set<MoodleUser> users, Map<Long, Map<Capability, Set<MoodleUser>>> resp) {
		
			for (Long courseId : resp.keySet()) {
			assertThat(courseId).isIn(courses);
			Map<Capability, Set<MoodleUser>> capUsers = resp.get(courseId);
			for (Capability cap : capUsers.keySet()) {
				assertThat(cap).isIn(capabilities);
				Set<MoodleUser> usrs = capUsers.get(cap);
				for (MoodleUser u : usrs) {
					assertThat(u).isIn(users);
				}
			}
		}
	}



	class TestMoodleFunctionWarpClass extends MoodleRestGetEnrolledUsersWithCapability {
		
		public TestMoodleFunctionWarpClass(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
				super(moodleConfig);
			}
		
		public Map<Long, Map<Capability, Set<MoodleUser>>> processResponse(Document response) throws MoodleRestGetEnrolledUsersWithCapabilityException  {
			return super.processResponse(response);
		}
		
			
	}
			
}