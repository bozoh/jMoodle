package ml.jmoodle.tests.functions.rest.course.tools;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.commons.OptionParameter;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.rest.core.course.exceptions.MoodleRestCreateCoursesException;
import ml.jmoodle.functions.rest.core.course.tools.MoodleCourseTools;

public class MoodleCourseToolsTest {
	MoodleCourse mc;
	Set<MoodleCourse> mcs;
	MoodleCourseTools mct;

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("ml.jmoodle.tests");
	}
	@Before
	public void beforeEach() {
		mc = Fixture.from(MoodleCourse.class).gimme("valid");
		mcs = new LinkedHashSet<>(Fixture.from(MoodleCourse.class).gimme(3, "valid"));
		mct =  new MoodleCourseTools();
	}
	@Test
	public void test_serialize() throws MoodleRestCreateCoursesException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException {
		assertMoodleCourse(
			URLDecoder.decode(mct.serialize(mcs), MoodleConfig.DEFAULT_ENCODING),
			mcs
		);
	}

	private void assertMoodleCourse(String serializedString, Set<MoodleCourse> mcs) throws UnsupportedEncodingException {
		List<MoodleCourse> courses = new LinkedList<>(mcs);
		for(int i=0; i< courses.size(); i++) {
			MoodleCourse course = courses.get(i);
		
			assertThat(serializedString).contains("courses[" + i +"]");
			assertThat(serializedString).contains("courses["+i+"][format]="+course.getFormat());
			assertThat(serializedString).contains("courses["+i+"][shortname]="+course.getShortname());
			assertThat(serializedString).contains("courses["+i+"][categoryid]="+course.getCategoryId());
			assertThat(serializedString).contains("courses["+i+"][categorysortorder]="+course.getCategorySortOrder());
			assertThat(serializedString).contains("courses["+i+"][fullname]="+course.getFullname());
			assertThat(serializedString).contains("courses["+i+"][summaryformat]="+course.getSummaryFormat().getValue());
			assertThat(serializedString).contains("courses["+i+"][summary]="+course.getSummary());
			assertThat(serializedString).contains("courses["+i+"][idnumber]="+course.getIdNumber());
			assertThat(serializedString).contains("courses["+i+"][showgrades]="+course.getShowGrades());
			assertThat(serializedString).contains("courses["+i+"][newsitems]="+course.getNewsItems());
			assertThat(serializedString).contains("courses["+i+"][startdate]="+course.getStartDate());
			assertThat(serializedString).contains("courses["+i+"][numsections]="+course.getNumSections());
			assertThat(serializedString).contains("courses["+i+"][maxbytes]="+course.getMaxBytes());
			assertThat(serializedString).contains("courses["+i+"][showreports]="+course.getShowReports());
			assertThat(serializedString).contains("courses["+i+"][visible]="+course.getVisible());
			assertThat(serializedString).contains("courses["+i+"][hiddensections]="+course.getHiddenSections());
			assertThat(serializedString).contains("courses["+i+"][groupmode]="+course.getGroupMode());
			assertThat(serializedString).contains("courses["+i+"][groupmodeforce]="+course.getGroupModeForce());
			assertThat(serializedString).contains("courses["+i+"][defaultgroupingid]="+course.getDefaultGroupingId());
			assertThat(serializedString).contains("courses["+i+"][timecreated]="+course.getTimeCreated());
			assertThat(serializedString).contains("courses["+i+"][timemodified]="+course.getTimeModified());
			assertThat(serializedString).contains("courses["+i+"][enablecompletion]="+course.getEnableCompletion());
			assertThat(serializedString).contains("courses["+i+"][completionstartonenrol]="+course.getCompletionStartOnEnrol());
			assertThat(serializedString).contains("courses["+i+"][completionnotify]="+course.getCompletionNotify());
			assertThat(serializedString).contains("courses["+i+"][id]="+course.getId());
			assertThat(serializedString).contains("courses["+i+"][lang]="+course.getLang());
			assertThat(serializedString).contains("courses["+i+"][forcetheme]="+course.getForceTheme());
		};
	}

		
	@Test
	public void test_to_entity() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Map<String, Object> m = new HashMap<>();
		m.put("id", "1");
		m.put("shortname", "shortname");
		m.put("categoryid", "1");
		m.put("categorysortorder", "1");
		m.put("fullname", "full name");

		OptionParameter op1 = new OptionParameter("name1", "value1");
		OptionParameter op2 = new OptionParameter("name2", "value2");
		Map<String, Object> n1 = new HashMap<>();
		n1.put("name", op1.getName());
		n1.put("value", op1.getValue());

		Map<String, Object> n2 = new HashMap<>();
		n2.put("name", op2.getName());
		n2.put("value", op2.getValue());


		Set<Map<String, Object>> set = new HashSet<>();
		set.add(n1);
		set.add(n2);

		m.put("courseformatoptions", set);

		MoodleCourse mcTest = mct.toEntity(m);
		assertEquals(Long.valueOf((String) m.get("id")), mcTest.getId());
		assertEquals(m.get("shortname"), mcTest.getShortname());
		assertEquals(m.get("fullname"), mcTest.getFullname());
		assertEquals(Long.valueOf((String)m.get("categoryid")), mcTest.getCategoryId());
		assertEquals(Integer.valueOf((String)m.get("categorysortorder")), mcTest.getCategorySortOrder());

		List<OptionParameter> lst = Arrays.asList(mcTest.getCourseformatoptions());
		assertEquals(lst.size(), 2);

		assertEquals(op2.getName(), lst.get(0).getName());
		assertEquals(op2.getValue(), lst.get(0).getValue());
		assertEquals(op1.getName(), lst.get(1).getName());
		assertEquals(op1.getValue(), lst.get(1).getValue());
		
		
	}

	@Test
	public void serialize_courses_id_test() throws UnsupportedEncodingException{
		Set<Long> coursesIds = new HashSet<>();
		coursesIds.add(1l);
		coursesIds.add(10l);
		coursesIds.add(-1l);
		coursesIds.add(-10l);
		
		assertCoursesIdsSerialization(coursesIds, 
			URLDecoder.decode(mct.serializeCoursesId("options[ids]", coursesIds), MoodleConfig.DEFAULT_ENCODING)
		);
	}
	
	private void assertCoursesIdsSerialization(Set<Long> coursesIds, String serializeCoursesId) throws UnsupportedEncodingException {
		List<Long> cids = new LinkedList<>(coursesIds);
		for(int i=0; i< cids.size(); i++) {
			Long courseId = cids.get(i);
			assertTrue(serializeCoursesId.contains("options[ids]"));
			assertTrue(serializeCoursesId.contains("options[ids][" + i + "]" +
				"=" + courseId));
			assertTrue(serializeCoursesId.contains("&"));
			
		}

	
	}

}
