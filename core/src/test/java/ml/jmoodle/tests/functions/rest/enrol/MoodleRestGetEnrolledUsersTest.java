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
import java.util.ArrayList;
import java.util.HashSet;
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
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.commons.OptionParameter;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.core.enrol.MoodleRestGetEnrolledUsers;
import ml.jmoodle.functions.rest.core.enrol.exceptions.MoodleRestGetEnrolledUsersException;

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
public class MoodleRestGetEnrolledUsersTest  {
	
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
		when(mc.getVersion()).thenReturn(new String ("2.2.0"));
	}
	
	@Test
	public void verify_since_version_test() throws MoodleWSFucntionException {
		MoodleRestGetEnrolledUsers cc = (MoodleRestGetEnrolledUsers) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_ENROL_GET_ENROLLED_USERS, mc
		);
		assertThat(cc.getSinceVersion()).isEqualTo("2.1.0");
	}

	@Test
	public void get_instance_without_errors_test() {
		try {
			assertThat(MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_ENROL_GET_ENROLLED_USERS, mc
			)).isInstanceOf(MoodleRestGetEnrolledUsers.class);
		} catch (MoodleWSFucntionException e) {
			fail("An exception was throwed"+e.getMessage());
		}
	}

	@Test
	public void exception_if_moodle_version_is_lower_than_2_0_0_test () {
		when(mc.getVersion()).thenReturn(new String ("1.9.9"));
		try {
			MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_ENROL_GET_ENROLLED_USERS, mc
			);
		} catch (MoodleWSFucntionException e) {
			assertNotNull(e);
			return;
		}
		fail("No exception was throwed");
	}

	@Test
	public void verify_function_name_if_version_is_lower_then_2_2_0_test () throws MoodleWSFucntionException {
		when(mc.getVersion()).thenReturn(new String ("2.1.9"));
		
		MoodleRestGetEnrolledUsers cc = (MoodleRestGetEnrolledUsers) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_ENROL_GET_ENROLLED_USERS, mc
		);
		assertThat(cc.getFunctionName()).isEqualTo("moodle_user_get_users_by_courseid");
	}

	@Test
	public void verify_function_name_if_version_is_bigger_or_equal_2_2_0_test () throws MoodleWSFucntionException {
		
		MoodleRestGetEnrolledUsers cc = (MoodleRestGetEnrolledUsers) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_ENROL_GET_ENROLLED_USERS, mc
		);
		assertThat(cc.getFunctionName()).isEqualTo("core_enrol_get_enrolled_users");
	}

	@Test
	public void verify_function_data_test() throws UnsupportedEncodingException, MoodleWSFucntionException {
	
		MoodleRestGetEnrolledUsers cc = (MoodleRestGetEnrolledUsers) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_ENROL_GET_ENROLLED_USERS, mc
		);
		MoodleCourse entity = Fixture.from(MoodleCourse.class).gimme("valid");
		cc.setCourse(entity);
		Set<OptionParameter> opts = new HashSet<>(Fixture.from(OptionParameter.class).gimme(5, "valid"));
		cc.setOptions(opts);
		String dataString = URLDecoder.decode(cc.getFunctionData(), MoodleConfig.DEFAULT_ENCODING);
		
		assertTrue(dataString.contains("wsfunction="+cc.getFunctionName()));
		assertTrue(dataString.contains("[courseid]="+entity.getId()));
		doOptionsAssertions(dataString, opts);
		
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
		Set<MoodleUser> mcs = new HashSet<>(Fixture.from(MoodleUser.class).gimme(2, "getEnrolledUsers"));
		
		Document response = EnrolFunctionsFixtureTemplate.getEnrolledUsersResponse(mcs);
		List<MoodleUser> resp = new ArrayList<>(cc.processResponse(response));
		doProcessResponseAssertions(mcs, resp);
	}
	
	
	private void doProcessResponseAssertions(Set<MoodleUser> expected, List<MoodleUser> actual) {
		assertThat(actual).hasSize(expected.size());
		for (MoodleUser entiy : expected) {
			assertThat(actual).contains(entiy);
		}
		
	}


	class TestMoodleFunctionWarpClass extends MoodleRestGetEnrolledUsers {
		
		public TestMoodleFunctionWarpClass(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
				super(moodleConfig);
			}
		
		public Set<MoodleUser> processResponse(Document response) throws MoodleRestGetEnrolledUsersException  {
			return super.processResponse(response);
		}
		
			
	}
			
}