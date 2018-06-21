package ml.jmoodle.tests.functions.rest.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collection;
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
import ml.jmoodle.commons.Field;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.core.user.MoodleRestGetUsersByField;
import ml.jmoodle.functions.rest.core.user.exceptions.MoodleRestGetUsersByFieldException;


/**
 * Get User by id Function
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MoodleRestGetUsersByFieldTest  {
	
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
		MoodleRestGetUsersByField cc = (MoodleRestGetUsersByField) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_USER_GET_USERS_BY_FIELD, mc
		);
		assertThat(cc.getSinceVersion()).isEqualTo("2.5.0");
	}

	@Test
	public void get_instance_without_errors_test() {
		try {
			MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_USER_GET_USERS_BY_FIELD, mc
			);
		} catch (MoodleWSFucntionException e) {
			fail("An exception was throwed"+e.getMessage());
		}
	}

	@Test
	public void verify_function_name_test () throws MoodleWSFucntionException {
		
		MoodleRestGetUsersByField cc = (MoodleRestGetUsersByField) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_USER_GET_USERS_BY_FIELD, mc
		);
		assertThat(cc.getFunctionName()).isEqualTo("core_user_get_users_by_field");
	}
		

	@Test
	public void exception_if_no_filed_is_set_test() {
		try {
			MoodleRestGetUsersByField cc = (MoodleRestGetUsersByField) MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_USER_GET_USERS_BY_FIELD, mc
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
	
		MoodleRestGetUsersByField cc = (MoodleRestGetUsersByField) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_USER_GET_USERS_BY_FIELD, mc
		);
		//Field field = Fixture.from(Field.class).gimme("MoodleRestGetUsersByFieldField");
		Field field = Field.EMAIL;
		Set<String> values = new HashSet<>();
		values.add("teste1@email.com");
		values.add("teste2@email.com");
		values.add("teste3@email.com");
		values.add("teste4@email.com");
		values.add("teste5@email.com");

		cc.setFiledValues(Field.EMAIL, new HashSet<>(values));
		String dataString = URLDecoder.decode(cc.getFunctionData(), MoodleConfig.DEFAULT_ENCODING);
		
		assertThat(dataString).contains("wsfunction="+cc.getFunctionName());
		doFuncationDataAssertions(dataString, values, field);
	}

	private void doFuncationDataAssertions(String dataString, Collection<String> entities, Field field) {
		int i = 0;
		assertThat(dataString).contains("field="+field.getValue());
		for (String entity : entities) {
			assertThat(dataString).contains("values[" + i + "]=");
			assertThat(dataString).contains(entity);
			i++;
		}
	}

	@Test
	public void verify_process_respose_test() throws MoodleWSFucntionException, MoodleConfigException, SAXException, IOException, ParserConfigurationException {
		int testSize = 42;
		TestMoodleFunctionWarpClass function = new TestMoodleFunctionWarpClass(mc);
		
		Set<MoodleUser> entities = new HashSet<>(Fixture.from(MoodleUser.class).gimme(testSize, "MoodleRestGetUserByIdFunctionResponseEntities"));
		Field field = Field.EMAIL;
		Set<String> values = new HashSet<>();
		values.add("teste1@email.com");
		values.add("teste2@email.com");
		values.add("teste3@email.com");
		values.add("teste4@email.com");
		values.add("teste5@email.com");
		function.setFiledValues(field, values);
		
		Document response = MoodleUserFixtureTemplate.getGetUsersByFieldsRespone(entities);
		Set<MoodleUser> resp = function.processResponse(response);
		assertThat(resp).hasSize(entities.size());
		
		entities.stream().forEach(e -> {
			assertThat(resp).contains(e);
		});
	}


	class TestMoodleFunctionWarpClass extends MoodleRestGetUsersByField {
	
		public TestMoodleFunctionWarpClass(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
			super(moodleConfig);
		}

		
		public Set<MoodleUser> processResponse(Document response) throws MoodleRestGetUsersByFieldException   {
			return super.processResponse(response);
		}
	
	
	}
}


