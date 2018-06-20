package ml.jmoodle.tests.functions.rest.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
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
import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.course.MoodleRestCreateCourses;
import ml.jmoodle.functions.rest.course.exceptions.MoodleRestCreateCoursesException;
import ml.jmoodle.tools.MoodleTools;

/**
 * Create Course(s) Function
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MoodleRestCreateCoursesTest  {
	
	private static final String MOODLE_TEST_URL = "http://teste.com";
	@Mock
	MoodleConfig mc;


	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("ml.jmoodle.tests.functions.rest.course");
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
				MoodleWSFunctions.CORE_COURSE_CREATE_COURSES, mc
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
				MoodleWSFunctions.CORE_COURSE_CREATE_COURSES, mc
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
		
		MoodleRestCreateCourses cc = (MoodleRestCreateCourses) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_COURSE_CREATE_COURSES, mc
		);
		assertEquals("moodle_course_create_courses", cc.getFunctionName());
	}

	@Test
	public void verify_function_name_if_version_is_bigger_or_equal_2_2_0_test () throws MoodleWSFucntionException {
		
		MoodleRestCreateCourses cc = (MoodleRestCreateCourses) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_COURSE_CREATE_COURSES, mc
		);
		assertEquals("core_course_create_courses", cc.getFunctionName());
	}
	
	@Test
	public void verify_since_version_test() throws MoodleWSFucntionException {
		MoodleRestCreateCourses cc = (MoodleRestCreateCourses) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_COURSE_CREATE_COURSES, mc
		);
		assertEquals("2.0.0", cc.getSinceVersion());
	}

	@Test
	public void exception_if_no_course_is_set_test() {
		try {
			MoodleRestCreateCourses cc = (MoodleRestCreateCourses) MoodleWSFunctionFactory.getFunction(
				MoodleWSFunctions.CORE_COURSE_CREATE_COURSES, mc
			);
			cc.getFunctionData();
		} catch (MoodleWSFucntionException e) {
			assertNotNull(e);
			return;
		}
		fail("No exception was throwed");
	}

	@Test
	public void verify_function_data_test() throws UnsupportedEncodingException, MoodleWSFucntionException {
	
		MoodleRestCreateCourses cc = (MoodleRestCreateCourses) MoodleWSFunctionFactory.getFunction(
			MoodleWSFunctions.CORE_COURSE_CREATE_COURSES, mc
		);
		Set<MoodleCourse> mcs = new HashSet<>(Fixture.from(MoodleCourse.class).gimme(2, "valid"));
		cc.setCourses(mcs);
		String dataString = cc.getFunctionData();
		
		assertTrue(dataString.contains("wsfunction="+cc.getFunctionName()));
		assertTrue(dataString.contains(MoodleTools.encode("courses[0]")));
		assertTrue(dataString.contains(MoodleTools.encode("courses[1]")));
		
	}


	@Test
	public void verify_process_respose_test() throws MoodleWSFucntionException, MoodleConfigException, SAXException, IOException, ParserConfigurationException {
	
		TestMoodleFunctionWarpClass cc = new TestMoodleFunctionWarpClass(mc);
		Set<MoodleCourse> mcs = new HashSet<>(Fixture.from(MoodleCourse.class).gimme(2, "valid"));
		mcs.stream().forEach(mc -> {
			assertTrue("id is 0", mc.getId().longValue() != 0l);
			assertTrue("id is 1", mc.getId().longValue() != 1l);	
		});
		cc.setCourses(mcs);
		Document response = MoodleCourseFixtureTemplate.getValidResponseOnCreate(mcs);
		Set<MoodleCourse> resp = cc.processResponse(response);
		assertEquals(mcs.size(), resp.size());
		resp.stream().forEach(r -> {
			assertTrue("id is not 0 or 1", r.getId().longValue() == 0l ||
				r.getId().longValue() == 1l);
		});
	}
	class TestMoodleFunctionWarpClass extends MoodleRestCreateCourses {
	
		public TestMoodleFunctionWarpClass(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
			super(moodleConfig);
		}

		public Set<MoodleCourse> processResponse(Document response) throws MoodleRestCreateCoursesException  {
			return super.processResponse(response);
		}
	
	
	}
}


