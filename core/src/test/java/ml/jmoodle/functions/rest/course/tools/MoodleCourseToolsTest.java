package ml.jmoodle.functions.rest.course.tools;

import static org.junit.Assert.assertEquals;
// import static com.google.common.truth.Truth8.assertThat;
// import static com.google.testing.compile.CompilationSubject.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
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
import br.com.six2six.fixturefactory.Rule;
import ml.jmoodle.commons.DescriptionFormat;
import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.commons.OptionParameter;
import ml.jmoodle.functions.rest.course.exceptions.MoodleRestCreateCoursesException;
import ml.jmoodle.tools.MoodleTools;

public class MoodleCourseToolsTest {
	MoodleCourse mc;
	Set<MoodleCourse> mcs;
	MoodleCourseTools mct;

	@BeforeClass
	public static void setUp() {
		Fixture.of(MoodleCourse.class).addTemplate("valid", new Rule(){{
			add("id", random(Long.class));
			add("shortname", regex("[A-Za-z0-9_ ]{3,10}"));
			add("categoryid", random(Long.class));
			add("categorysortorder", random(Integer.class));
			add("fullname", regex("[A-Za-z0-9_ ]{3,50}"));
			add("idnumber", regex("\\w{3,5}"));
			add("summary", regex("[A-Za-z0-9_ ]{3,150}"));
			add("summaryformat", random(DescriptionFormat.class, 
				DescriptionFormat.MOODLE, 
				DescriptionFormat.HTML, 
				DescriptionFormat.PLAIN, 
				DescriptionFormat.MARKDOWN 
			));
			add("format", random(String.class, 
				MoodleCourse.FORMAT_WEEKS, 
				MoodleCourse.FORMAT_TOPICS,
				MoodleCourse.FORMAT_SOCIAL,
				MoodleCourse.FORMAT_SCORM
			));
			add("showgrades", random(Integer.class, 
				MoodleCourse.SHOW_GRADES_YES,
				MoodleCourse.SHOW_GRADES_NO
			));
			add("newsitems", random(Integer.class));
			add("startdate", random(Long.class));
			add("numsections", random(Integer.class));
			add("maxbytes", random(Long.class));
			add("showreports", random(Integer.class, 
				MoodleCourse.SHOW_REPORTS_YES,
				MoodleCourse.SHOW_REPORTS_NO
			));
			add("visible", random(Integer.class, 
				MoodleCourse.COURSE_VISIBLE_TO_STUDENTS_YES,
				MoodleCourse.COURSE_VISIBLE_TO_STUDENTS_NO
			));
			add("hiddensections", random(Integer.class, 
				MoodleCourse.HIDDEN_SECTIONS_COLLAPSED,
				MoodleCourse.HIDDEN_SECTIONS_INVISIBLE
			));
			add("groupmode", random(Integer.class, 
				MoodleCourse.GROUP_MODE_NO_GROUPS,
				MoodleCourse.GROUP_MODE_SEPARATE_GROUPS,
				MoodleCourse.GROUP_MODE_VISIBLE_GROUPS
			));
			add("groupmodeforce", random(Integer.class, 
				MoodleCourse.GROUP_MODE_FORCE_YES,
				MoodleCourse.GROUP_MODE_FORCE_NO
			));
			add("defaultgroupingid", random(Long.class));
			add("timecreated", random(Long.class));
			add("timemodified", random(Long.class));
			add("enablecompletion", random(Integer.class, 
				MoodleCourse.COMPLETION_ENABLED,
				MoodleCourse.COMPLETION_DISABLED
			));
			add("completionnotify", random(Integer.class,
				MoodleCourse.COMPLETION_NOTIFY_ENABLED,
				MoodleCourse.COMPLETION_NOTIFY_DISABLED
			));               
			add("lang", regex("\\w{2,4}")); 
			add("forcetheme", regex("\\w{3,6}"));
			add("courseformatoptions", has(3).of(OptionParameter.class, "valid"));
	
		}});
		Fixture.of(OptionParameter.class).addTemplate("valid", new Rule(){{
			add("name", regex("\\w{3,6}"));
			add("value", regex("\\w{3,10}"));
		}});
	}
	@Before
	public void beforeEach() {
		mc = Fixture.from(MoodleCourse.class).gimme("valid");
		mcs = new LinkedHashSet<>(Fixture.from(MoodleCourse.class).gimme(3, "valid"));
		mct =  new MoodleCourseTools();
	}
	@Test
	public void test_serialize() throws MoodleRestCreateCoursesException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException {
		assertMoodleCourse(mct.serialize(mcs), mcs);
	}

	private void assertMoodleCourse(String serializedString, Set<MoodleCourse> mcs) throws UnsupportedEncodingException {
		List<MoodleCourse> courses = new LinkedList<>(mcs);
		for(int i=0; i< courses.size(); i++) {
			MoodleCourse course = courses.get(i);
		
			assertTrue(serializedString.contains("courses%5B" + i +"%5D"));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bformat%5D="+MoodleTools.encode(course.getFormat())));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bshortname%5D="+MoodleTools.encode(course.getShortname())));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bcategoryid%5D="+course.getCategoryId()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bcategorysortorder%5D="+course.getCategorySortOrder()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bfullname%5D="+MoodleTools.encode(course.getFullname())));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bsummaryformat%5D="+course.getSummaryFormat()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bsummarydescriptionformat%5D="+course.getSummaryDescriptionFormat()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bsummary%5D="+MoodleTools.encode(course.getSummary())));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bidnumber%5D="+MoodleTools.encode(course.getIdNumber())));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bshowgrades%5D="+course.getShowGrades()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bnewsitems%5D="+course.getNewsItems()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bstartdate%5D="+course.getStartDate()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bnumsections%5D="+course.getNumSections()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bmaxbytes%5D="+course.getMaxBytes()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bshowreports%5D="+course.getShowReports()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bvisible%5D="+course.getVisible()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bhiddensections%5D="+course.getHiddenSections()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bgroupmode%5D="+course.getGroupMode()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bgroupmodeforce%5D="+course.getGroupModeForce()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bdefaultgroupingid%5D="+course.getDefaultGroupingId()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Btimecreated%5D="+course.getTimeCreated()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Btimemodified%5D="+course.getTimeModified()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Benablecompletion%5D="+course.getEnableCompletion()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bcompletionstartonenrol%5D="+course.getCompletionStartOnEnrol()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bcompletionnotify%5D="+course.getCompletionNotify()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bid%5D="+course.getId()));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Blang%5D="+MoodleTools.encode(course.getLang())));
			assertTrue(serializedString.contains("courses%5B"+i+"%5D%5Bforcetheme%5D="+MoodleTools.encode(course.getForceTheme())));
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

}
