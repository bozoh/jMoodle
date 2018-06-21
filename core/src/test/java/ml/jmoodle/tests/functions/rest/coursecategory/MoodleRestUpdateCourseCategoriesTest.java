package ml.jmoodle.tests.functions.rest.coursecategory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import ml.jmoodle.commons.MoodleCourseCategory;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.core.coursecategory.MoodleRestUpdateCourseCategories;

/**
 * Update Course Categories(s) Function
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MoodleRestUpdateCourseCategoriesTest  {
	
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
				MoodleWSFunctions.CORE_COURSE_UPDATE_CATEGORIES, mc
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
				MoodleWSFunctions.CORE_COURSE_UPDATE_CATEGORIES, mc
			);
		} catch (MoodleWSFucntionException e) {
			assertNotNull(e);
			return;
		}
		fail("No exception was throwed");
	}

	

	@Test
	public void verify_function_name_test () throws MoodleWSFucntionException {
		
		MoodleRestUpdateCourseCategories cc = (MoodleRestUpdateCourseCategories) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_COURSE_UPDATE_CATEGORIES, mc
		);
		assertEquals("core_course_update_categories", cc.getFunctionName());
	}
	
	@Test
	public void verify_since_version_test() throws MoodleWSFucntionException {
		MoodleRestUpdateCourseCategories cc = (MoodleRestUpdateCourseCategories) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_COURSE_UPDATE_CATEGORIES, mc
		);
		assertEquals("2.3.0", cc.getSinceVersion());
	}

	@Test
	public void exception_if_no_category_is_set_test() {
		try {
			MoodleRestUpdateCourseCategories cc = (MoodleRestUpdateCourseCategories) MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_COURSE_UPDATE_CATEGORIES, mc
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
	
		MoodleRestUpdateCourseCategories cc = (MoodleRestUpdateCourseCategories) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_COURSE_UPDATE_CATEGORIES, mc
		);
		Set<MoodleCourseCategory> entities = new HashSet<>(Fixture.from(MoodleCourseCategory.class).gimme(15, "valid"));
		cc.setCategories(entities);
		String dataString = URLDecoder.decode(cc.getFunctionData(), MoodleConfig.DEFAULT_ENCODING);
		
		assertThat(dataString).contains("wsfunction="+cc.getFunctionName());
		assertThat(dataString).contains("[id]=");
		
		
	}


	

	// @Test
	// public void verify_process_respose_test() throws MoodleWSFucntionException, MoodleConfigException, SAXException, IOException, ParserConfigurationException {
	// 	int testSize = 12;
	// 	TestMoodleFunctionWarpClass function = new TestMoodleFunctionWarpClass(mc);
	// 	Set<MoodleCourseCategory> entities = new HashSet<>(Fixture.from(MoodleCourseCategory.class).gimme(testSize, "valid"));
	// 	function.setCategories(entities);
		
	// 	Document response = MoodleCourseCategoryFixtureTemplate.getValidResponseOnUpdate(entities);
	// 	Set<MoodleCourseCategory> resp = function.processResponse(response);
	// 	assertEquals(entities.size(), resp.size());
	// 	entities.stream().forEach(e -> {
	// 		assertTrue(e.getId() <= testSize);
	// 		assertTrue(resp.contains(e));
	// 	});
	// }
	// class TestMoodleFunctionWarpClass extends MoodleRestUpdateCourseCategories {
	
	// 	public TestMoodleFunctionWarpClass(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
	// 		super(moodleConfig);
	// 	}

	// 	public Set<MoodleCourseCategory> processResponse(Document response) throws MoodleRestUpdateCourseCategoriesException  {
	// 		return super.processResponse(response);
	// 	}
	
	
	// }
}


