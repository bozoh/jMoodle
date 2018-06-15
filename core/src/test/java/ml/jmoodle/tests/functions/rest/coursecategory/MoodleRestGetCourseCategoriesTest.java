package ml.jmoodle.tests.functions.rest.coursecategory;

import static org.junit.Assert.assertEquals;
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
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import ml.jmoodle.commons.Criteria;
import ml.jmoodle.commons.MoodleCourseCategory;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.coursecategory.MoodleRestGetCourseCategories;
import ml.jmoodle.functions.rest.coursecategory.exceptions.MoodleRestGetCourseCategoriesException;

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
public class MoodleRestGetCourseCategoriesTest  {
	
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
		when(mc.getVersion()).thenReturn(new String ("2.3.0"));
	}

	@Test
	public void get_instance_without_errors_test() {
		try {
			MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_COURSE_GET_CATEGORIES, mc
			);
		} catch (MoodleWSFucntionException e) {
			fail("An exception was throwed"+e.getMessage());
		}
	}

	@Test
	public void exception_if_moodle_version_is_lower_than_2_3_0_test () {
		when(mc.getVersion()).thenReturn(new String ("2.2.9"));
		try {
			MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_COURSE_GET_CATEGORIES, mc
			);
		} catch (MoodleWSFucntionException e) {
			assertNotNull(e);
			return;
		}
		fail("No exception was throwed");
	}

	

	@Test
<<<<<<< HEAD
	public void verify_function_name_test () throws MoodleWSFucntionException {
=======
	public void verify_function_name_if_version_is_bigger_or_equal_2_2_0_test () throws MoodleWSFucntionException {
>>>>>>> daed983baaf3f04d7ebde5d2c37518bdadaf6264
		
		MoodleRestGetCourseCategories cc = (MoodleRestGetCourseCategories) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_COURSE_GET_CATEGORIES, mc
		);
		assertEquals("core_course_get_categories", cc.getFunctionName());
	}
	
	@Test
	public void verify_since_version_test() throws MoodleWSFucntionException {
		MoodleRestGetCourseCategories cc = (MoodleRestGetCourseCategories) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_COURSE_GET_CATEGORIES, mc
		);
		assertEquals("2.3.0", cc.getSinceVersion());
	}

	@Test
	public void exception_if_no_criteria_is_set_test() {
		try {
			MoodleRestGetCourseCategories cc = (MoodleRestGetCourseCategories) MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_COURSE_GET_CATEGORIES, mc
			);
			cc.getFunctionData();
		} catch (MoodleWSFucntionException e) {
			assertNotNull(e);
			return;
		}
		fail("No exception was throwed");
	}

	@Test
	public void verify_function_data_test() throws UnsupportedEncodingException, MoodleWSFucntionException  {
	
		MoodleRestGetCourseCategories cc = (MoodleRestGetCourseCategories) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_COURSE_GET_CATEGORIES, mc
		);
		Set<Criteria> mcs = new HashSet<>(Fixture.from(Criteria.class).gimme(5, "valid"));
		cc.setCriterias(mcs);
		String dataString = URLDecoder.decode(cc.getFunctionData(), MoodleConfig.DEFAULT_ENCODING);
		
		
		assertTrue(dataString.contains("wsfunction="+cc.getFunctionName()));
		doVerifyFunctionDataAssertions(mcs, dataString);
		
	}


	private void doVerifyFunctionDataAssertions(Set<Criteria> mcs, String dataString) {
		int i = 0;
		for (Criteria c : mcs) {
			assertTrue(dataString.contains("criteria["+ i +"][key]="+c.getKey()));
			assertTrue(dataString.contains("criteria["+ i++ +"][value]="+c.getValue()));
		}
		assertTrue(dataString.contains("&"));
	}

	@Test
	public void verify_process_respose_test() throws MoodleWSFucntionException, MoodleConfigException, SAXException, IOException, ParserConfigurationException {
	
		TestMoodleFunctionWarpClass cc = new TestMoodleFunctionWarpClass(mc);
		Set<MoodleCourseCategory> entities = new HashSet<>(Fixture.from(MoodleCourseCategory.class).gimme(5, "valid"));
		
		
		Document response = MoodleCourseCategoryFixtureTemplate.getValidResponseOnRetrive(entities);
		Set<MoodleCourseCategory> resp = cc.processResponse(response);
		assertEquals(entities.size(), resp.size());
		entities.stream().forEach(e -> {
			assertTrue(resp.contains(e));
		});
	}
	class TestMoodleFunctionWarpClass extends MoodleRestGetCourseCategories {
	
		public TestMoodleFunctionWarpClass(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
			super(moodleConfig);
		}

		public Set<MoodleCourseCategory> processResponse(Document response) throws MoodleRestGetCourseCategoriesException  {
			return super.processResponse(response);
		}
	
	
	}
}


