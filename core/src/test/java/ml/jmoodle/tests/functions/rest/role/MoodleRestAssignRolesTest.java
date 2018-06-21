package ml.jmoodle.tests.functions.rest.role;

import static org.assertj.core.api.Assertions.assertThat;
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
import ml.jmoodle.commons.MoodleUserRoleContext;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.role.MoodleRestAssignRoles;


/**
 * Manual Role Assignments Function
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MoodleRestAssignRolesTest  {
	
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
		MoodleRestAssignRoles cc = (MoodleRestAssignRoles) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_ROLE_ASSIGN_ROLES, mc
		);
		assertThat(cc.getSinceVersion()).isEqualTo("2.0.0");
	}

	@Test
	public void get_instance_without_errors_test() {
		try {
			MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_ROLE_ASSIGN_ROLES, mc
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
				MoodleWSFunctions.CORE_ROLE_ASSIGN_ROLES, mc
			);
		} catch (MoodleWSFucntionException e) {
			assertThat(e).isNotNull();
			return;
		}
		fail("No exception was throwed");
	}
	

	@Test
	public void verify_function_name_above_or_equal_2_2_0_test () throws MoodleWSFucntionException {
		
		MoodleRestAssignRoles cc = (MoodleRestAssignRoles) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_ROLE_ASSIGN_ROLES, mc
		);
		assertThat(cc.getFunctionName()).isEqualTo("core_role_assign_roles");
	}

	@Test
	public void verify_function_name_below_2_2_0_test () throws MoodleWSFucntionException {
		when(mc.getVersion()).thenReturn(new String ("2.1.9"));
		MoodleRestAssignRoles cc = (MoodleRestAssignRoles) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_ROLE_ASSIGN_ROLES, mc
		);
		assertThat(cc.getFunctionName()).isEqualTo("moodle_role_assign");
	}
	

	@Test
	public void exception_if_no_role_is_set_test() {
		try {
			MoodleRestAssignRoles cc = (MoodleRestAssignRoles) MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_ROLE_ASSIGN_ROLES, mc
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
	
		MoodleRestAssignRoles cc = (MoodleRestAssignRoles) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_ROLE_ASSIGN_ROLES, mc
		);
		Set<MoodleUserRoleContext> entities = new HashSet<>(Fixture.from(MoodleUserRoleContext.class).gimme(22, "valid"));
		cc.setRoles(entities);
		String dataString = URLDecoder.decode(cc.getFunctionData(), MoodleConfig.DEFAULT_ENCODING);
		
		assertThat(dataString).contains("wsfunction="+cc.getFunctionName());


		
	}

	
}


