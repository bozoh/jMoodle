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
import ml.jmoodle.commons.MoodleEnrolInstanceInfo;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.enrol.self.MoodleRestSelfGetInstanceInfo;
import ml.jmoodle.functions.rest.enrol.self.exceptions.MoodleRestSelfGetInstanceInfoException;


/**
 * enrol_self_get_instance_info
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MoodleRestSelfGetInstanceInfoTest  {
	
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
		MoodleRestSelfGetInstanceInfo cc = (MoodleRestSelfGetInstanceInfo) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.ENROL_SELF_GET_INSTANCE_INFO, mc
		);
		assertThat(cc.getSinceVersion()).isEqualTo("3.0.0");
	}

	@Test
	public void get_instance_without_errors_test() {
		try {
			assertThat(MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.ENROL_SELF_GET_INSTANCE_INFO, mc
			)).isInstanceOf(MoodleRestSelfGetInstanceInfo.class);
		} catch (MoodleWSFucntionException e) {
			fail("An exception was throwed"+e.getMessage());
		}
	}

	@Test
	public void exception_if_moodle_version_is_lower_than_3_0_0_test () {
		when(mc.getVersion()).thenReturn(new String ("2.9.9"));
		try {
			MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.ENROL_SELF_GET_INSTANCE_INFO, mc
			);
		} catch (MoodleWSFucntionException e) {
			assertNotNull(e);
			return;
		}
		fail("No exception was throwed");
	}
	
	@Test
	public void verify_function_name() throws MoodleWSFucntionException{
		
		MoodleRestSelfGetInstanceInfo cc = (MoodleRestSelfGetInstanceInfo) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.ENROL_SELF_GET_INSTANCE_INFO, mc
		);
		assertThat(cc.getFunctionName()).isEqualTo("enrol_self_get_instance_info");
	}

	@Test
	public void exception_if_instanceid_is_not_set_test() throws MoodleWSFucntionException {
		try {
			MoodleRestSelfGetInstanceInfo cc = (MoodleRestSelfGetInstanceInfo) MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.ENROL_SELF_GET_INSTANCE_INFO, mc
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
	
		MoodleRestSelfGetInstanceInfo cc = (MoodleRestSelfGetInstanceInfo) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.ENROL_SELF_GET_INSTANCE_INFO, mc
		);
		Long instanceId = 99l;
		cc.setInstanceId(instanceId);
		
		String dataString = URLDecoder.decode(cc.getFunctionData(), MoodleConfig.DEFAULT_ENCODING);
		
		assertTrue(dataString.contains("wsfunction="+cc.getFunctionName()));

		assertThat(dataString).contains("instanceid="+instanceId);

	}

	@Test
	public void verify_process_respose_test() throws ParserConfigurationException, SAXException, IOException, MoodleWSFucntionException, MoodleConfigException  {
	
		TestMoodleFunctionWarpClass cc = new TestMoodleFunctionWarpClass(mc);
		MoodleEnrolInstanceInfo instInfo = Fixture.from(MoodleEnrolInstanceInfo.class).gimme("valid");
		//This is not required here only in enrol_guest function
		instInfo.setPasswordRequired(null);
		
		Document response = EnrolFunctionsFixtureTemplate.getEnrolSelfGetInstanceInfoResponse(instInfo);
		assertThat(cc.processResponse(response)).isEqualTo(instInfo);
	}
	
	class TestMoodleFunctionWarpClass extends MoodleRestSelfGetInstanceInfo {
		
		public TestMoodleFunctionWarpClass(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
				super(moodleConfig);
			}
		

		public MoodleEnrolInstanceInfo processResponse(Document response) throws MoodleRestSelfGetInstanceInfoException  {
			return super.processResponse(response);
		}
	}
			
}