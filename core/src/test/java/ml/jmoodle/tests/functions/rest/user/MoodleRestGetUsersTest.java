package ml.jmoodle.tests.functions.rest.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntPredicate;

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
import ml.jmoodle.commons.Criteria;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.commons.MoodleWarning;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.user.MoodleRestGetUsers;
import ml.jmoodle.functions.rest.user.exceptions.MoodleRestGetUsersException;


/**
 * Get Course Categories(s) Function
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MoodleRestGetUsersTest  {
	
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
		when(mc.getVersion()).thenReturn(new String ("2.5.0"));
	}
	@Test
	public void verify_since_version_test() throws MoodleWSFucntionException {
		MoodleRestGetUsers cc = (MoodleRestGetUsers) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_USER_GET_USERS, mc
		);
		assertThat(cc.getSinceVersion()).isEqualTo("2.5.0");
	}

	@Test
	public void get_instance_without_errors_test() {
		try {
			MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_USER_GET_USERS, mc
			);
		} catch (MoodleWSFucntionException e) {
			fail("An exception was throwed"+e.getMessage());
		}
	}

	@Test
	public void exception_if_moodle_version_is_lower_than_2_5_0_test () {
		when(mc.getVersion()).thenReturn(new String ("2.4.9"));
		try {
			MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_USER_GET_USERS, mc
			);
		} catch (MoodleWSFucntionException e) {
			assertThat(e).isNotNull();
			return;
		}
		fail("No exception was throwed");
	}
	

	@Test
	public void verify_function_name_test () throws MoodleWSFucntionException {
		
		MoodleRestGetUsers cc = (MoodleRestGetUsers) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_USER_GET_USERS, mc
		);
		assertThat(cc.getFunctionName()).isEqualTo("core_user_get_users");
	}

		

	@Test
	public void exception_if_no_criteria_is_set_test() {
		try {
			MoodleRestGetUsers cc = (MoodleRestGetUsers) MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_USER_GET_USERS, mc
			);
			cc.getFunctionData();
		} catch (MoodleWSFucntionException e) {
			assertThat(e).isNotNull();
			return;
		}
		fail("No exception was throwed");
	}

	@Test
	public void verify_function_data_test() throws UnsupportedEncodingException, MoodleWSFucntionException  {
	
		MoodleRestGetUsers cc = (MoodleRestGetUsers) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_USER_GET_USERS, mc
		);
		Set<Criteria> criterias = new HashSet<>(Fixture.from(Criteria.class).gimme(15, "MoodleRestGetUsersCriteria"));
		cc.setCriterias(criterias);
		String dataString = URLDecoder.decode(cc.getFunctionData(), MoodleConfig.DEFAULT_ENCODING);
		
		assertThat(dataString).contains("wsfunction="+cc.getFunctionName());
		
	}

	@Test
	public void verify_process_respose_test() throws MoodleWSFucntionException, MoodleConfigException, SAXException, IOException, ParserConfigurationException {
		int testSize = 12;
		TestMoodleFunctionWarpClass function = new TestMoodleFunctionWarpClass(mc);
		Set<Criteria> criterias = new HashSet<>(Fixture.from(Criteria.class).gimme(testSize, "MoodleRestGetUsersCriteria"));
		function.setCriterias(criterias);
		
		List<MoodleUser> entities = Fixture.from(MoodleUser.class).gimme(testSize, "MoodleRestGetUserFunctionUser");
		Document response = MoodleUserFixtureTemplate.getGetUsersRespone(entities, null);
		Set<MoodleUser> resp = function.processResponse(response);
		assertThat(resp).hasSize(entities.size());
		
		entities.stream().forEach(e -> {
			assertThat(resp).contains(e);
		});
	}


	@Test
	public void get_warnings_test() throws MoodleWSFucntionException, MoodleConfigException, SAXException, IOException, ParserConfigurationException {
		int testSize = 22;
		TestMoodleFunctionWarpClass function = new TestMoodleFunctionWarpClass(mc);
		Set<Criteria> criterias = new HashSet<>(Fixture.from(Criteria.class).gimme(testSize, "MoodleRestGetUsersCriteria"));
		function.setCriterias(criterias);
		
		List<MoodleUser> entities = Fixture.from(MoodleUser.class).gimme(1, "MoodleRestGetUserFunctionUser");
		List<MoodleWarning> warnings = Fixture.from(MoodleWarning.class).gimme(testSize, "valid");
		Document response = MoodleUserFixtureTemplate.getGetUsersRespone(entities, warnings);
		function.processResponse(response);
		assertThat(function.hasWarnings()).isTrue();
		Set<MoodleWarning> respWarns = function.getWarnings();
		assertThat(respWarns).hasSize(testSize);
		assertThat(respWarns).containsAll(warnings);
		
		
	}
	class TestMoodleFunctionWarpClass extends MoodleRestGetUsers {
	
		public TestMoodleFunctionWarpClass(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
			super(moodleConfig);
		}

		
		public Set<MoodleUser> processResponse(Document response) throws MoodleRestGetUsersException  {
			return super.processResponse(response);
		}
	
	
	}
}


