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
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.core.user.MoodleRestCreateUsers;
import ml.jmoodle.functions.rest.core.user.exceptions.MoodleRestCreateUsersException;


/**
 * Create Course Categories(s) Function
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MoodleRestCreateUsersTest  {
	
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
	public void get_instance_without_errors_test() {
		try {
			MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_USER_CREATE_USERS, mc
			);
		} catch (MoodleWSFucntionException e) {
			fail("An exception was throwed"+e.getMessage());
		}
	}

	@Test
	public void exception_if_moodle_version_is_lower_than_2_0_0_test () {
		when(mc.getVersion()).thenReturn(new String ("1.9.9"));
		try {
			MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_USER_CREATE_USERS, mc
			);
		} catch (MoodleWSFucntionException e) {
			assertThat(e).isNotNull();
			return;
		}
		fail("No exception was throwed");
	}
	

	@Test
	public void verify_function_name_above_or_equal_2_2_0_test () throws MoodleWSFucntionException {
		
		MoodleRestCreateUsers cc = (MoodleRestCreateUsers) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_USER_CREATE_USERS, mc
		);
		assertThat(cc.getFunctionName()).isEqualTo("core_user_create_users");
	}

	@Test
	public void verify_function_name_below_2_2_0_test () throws MoodleWSFucntionException {
		when(mc.getVersion()).thenReturn(new String ("2.1.9"));
		MoodleRestCreateUsers cc = (MoodleRestCreateUsers) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_USER_CREATE_USERS, mc
		);
		assertThat(cc.getFunctionName()).isEqualTo("moodle_user_create_users");
	}
	
	@Test
	public void verify_since_version_test() throws MoodleWSFucntionException {
		MoodleRestCreateUsers cc = (MoodleRestCreateUsers) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_USER_CREATE_USERS, mc
		);
		assertThat(cc.getSinceVersion()).isEqualTo("2.0.0");
	}

	@Test
	public void exception_if_no_category_is_set_test() {
		try {
			MoodleRestCreateUsers cc = (MoodleRestCreateUsers) MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_USER_CREATE_USERS, mc
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
	
		MoodleRestCreateUsers cc = (MoodleRestCreateUsers) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_USER_CREATE_USERS, mc
		);
		Set<MoodleUser> entities = new HashSet<>(Fixture.from(MoodleUser.class).gimme(5, "MoodleRestCreateUserFunctionUser"));
		cc.setUsers(entities);
		String dataString = URLDecoder.decode(cc.getFunctionData(), MoodleConfig.DEFAULT_ENCODING);
		
		assertThat(dataString).contains("wsfunction="+cc.getFunctionName());
		
	}

	@Test
	public void verify_process_respose_test() throws MoodleWSFucntionException, MoodleConfigException, SAXException, IOException, ParserConfigurationException {
		int testSize = 5;
		TestMoodleFunctionWarpClass function = new TestMoodleFunctionWarpClass(mc);
		Set<MoodleUser> entities = new HashSet<>(Fixture.from(MoodleUser.class).gimme(testSize, "MoodleRestCreateUserFunctionUser"));
		function.setUsers(entities);
		Document response = MoodleUserFixtureTemplate.getAddUsersRespone(entities);
		Set<MoodleUser> resp = function.processResponse(response);
		assertThat(entities).hasSize(resp.size());
		
		entities.stream().forEach(e -> {
			assertThat(e.getId()).isLessThanOrEqualTo(12l);
			assertThat(resp).contains(e);
		});
	}
	class TestMoodleFunctionWarpClass extends MoodleRestCreateUsers {
	
		public TestMoodleFunctionWarpClass(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
			super(moodleConfig);
		}

		public Set<MoodleUser> processResponse(Document response) throws MoodleRestCreateUsersException  {
			return super.processResponse(response);
		}
	
	
	}
}


