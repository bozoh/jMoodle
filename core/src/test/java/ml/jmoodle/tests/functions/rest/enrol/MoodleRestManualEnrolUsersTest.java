package ml.jmoodle.tests.functions.rest.enrol;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import ml.jmoodle.commons.MoodleManualEnrolment;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.enrol.manual.MoodleRestManualEnrolUsers;

/**
 * enrol_manual_enrol_users
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MoodleRestManualEnrolUsersTest  {
	
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
		MoodleRestManualEnrolUsers cc = (MoodleRestManualEnrolUsers) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.ENROL_MANUAL_ENROL_USERS, mc
		);
		assertThat(cc.getSinceVersion()).isEqualTo("2.0.0");
	}

	@Test
	public void get_instance_without_errors_test() {
		try {
			assertThat(MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.ENROL_MANUAL_ENROL_USERS, mc
			)).isInstanceOf(MoodleRestManualEnrolUsers.class);
		} catch (MoodleWSFucntionException e) {
			fail("An exception was throwed"+e.getMessage());
		}
	}

	@Test
	public void exception_if_moodle_version_is_lower_than_2_0_0_test () {
		when(mc.getVersion()).thenReturn(new String ("1.9.9"));
		try {
			MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.ENROL_MANUAL_ENROL_USERS, mc
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
		
		MoodleRestManualEnrolUsers cc = (MoodleRestManualEnrolUsers) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.ENROL_MANUAL_ENROL_USERS, mc
		);
		assertThat(cc.getFunctionName()).isEqualTo("moodle_enrol_manual_enrol_users");
	}

	@Test
	public void verify_function_name_if_version_is_bigger_or_equal_2_2_0_test () throws MoodleWSFucntionException {
		
		MoodleRestManualEnrolUsers cc = (MoodleRestManualEnrolUsers) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.ENROL_MANUAL_ENROL_USERS, mc
		);
		assertThat(cc.getFunctionName()).isEqualTo("enrol_manual_enrol_users");
	}

	@Test
	public void exception_if_enrolments_is_not_set_test() throws MoodleWSFucntionException {
		try {
			MoodleRestManualEnrolUsers cc = (MoodleRestManualEnrolUsers) MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.ENROL_MANUAL_ENROL_USERS, mc
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
	
		MoodleRestManualEnrolUsers cc = (MoodleRestManualEnrolUsers) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.ENROL_MANUAL_ENROL_USERS, mc
		);
		Set<MoodleManualEnrolment> enrolments = new HashSet<>(Fixture.from(MoodleManualEnrolment.class).gimme(4, "valid"));
		cc.setEnrolments(enrolments);
		
		String dataString = URLDecoder.decode(cc.getFunctionData(), MoodleConfig.DEFAULT_ENCODING);
		
		assertTrue(dataString.contains("wsfunction="+cc.getFunctionName()));
		
	}
			
}