package ml.jmoodle.functions.rest.course.tools;

import static com.google.common.truth.Truth.*;
// import static com.google.common.truth.Truth8.assertThat;
// import static com.google.testing.compile.CompilationSubject.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import javax.tools.JavaFileObject;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import ml.jmoodle.annotations.processors.ConvertersAnnotationProcessor;
import ml.jmoodle.commons.DescriptionFormat;
import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.commons.OptionParameter;
import ml.jmoodle.functions.rest.course.exceptions.MoodleRestCreateCoursesException;
import ml.jmoodle.tools.MoodleParamMap;
import ml.jmoodle.tools.MoodleTools;

public class MoodleCourseMarshallerTest {
	MoodleCourse mc;
	Set<MoodleCourse> mcs;

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
	}
	@Test
	public void test_marshal() throws MoodleRestCreateCoursesException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException {
		// System.out.println(mc);
		// MoodleCourseMarshaller marshaller2 = new MoodleCourseMarshaller(mcs);
		// String marshedString = marshaller2.marshal();
		// System.out.println(marshedString);
		// assertMoodleCourse(marshedString, mcs);

		MoodleParamMap m = this.serializeMethodValue(mc, "courses[0]");
		System.err.println("------------------------------------------");
		System.err.println("------------------------------------------");
		System.err.println(URLDecoder.decode(m.toParamString(), "UTF-8"));
		System.err.println("------------------------------------------");
		System.err.println("------------------------------------------");

	}

	private void assertMoodleCourse(String marshedString, Set<MoodleCourse> mcs) {
		List<MoodleCourse> courses = new LinkedList<>(mcs);
		IntStream.range(0, mcs.size()).forEach(i ->{
			MoodleCourse course = courses.get(i);
			try {
				assertTrue(marshedString.contains("courses%5B" + i +"%5D"));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bformat%5D="+MoodleTools.encode(course.getFormat())));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bshortname%5D="+MoodleTools.encode(course.getShortname())));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bcategoryid%5D="+course.getCategoryId()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bcategorysortorder%5D="+course.getCategorySortOrder()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bfullname%5D="+MoodleTools.encode(course.getFullname())));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bsummaryformat%5D="+course.getSummaryFormat()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bsummarydescriptionformat%5D="+course.getSummaryDescriptionFormat()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bsummary%5D="+MoodleTools.encode(course.getSummary())));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bidnumber%5D="+MoodleTools.encode(course.getIdNumber())));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bshowgrades%5D="+course.getShowGrades()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bnewsitems%5D="+course.getNewsItems()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bstartdate%5D="+course.getStartDate()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bnumsections%5D="+course.getNumSections()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bmaxbytes%5D="+course.getMaxBytes()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bshowreports%5D="+course.getShowReports()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bvisible%5D="+course.getVisible()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bhiddensections%5D="+course.getHiddenSections()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bgroupmode%5D="+course.getGroupMode()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bgroupmodeforce%5D="+course.getGroupModeForce()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bdefaultgroupingid%5D="+course.getDefaultGroupingId()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Btimecreated%5D="+course.getTimeCreated()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Btimemodified%5D="+course.getTimeModified()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Benablecompletion%5D="+course.getEnableCompletion()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bcompletionstartonenrol%5D="+course.getCompletionStartOnEnrol()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bcompletionnotify%5D="+course.getCompletionNotify()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bid%5D="+course.getId()));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Blang%5D="+MoodleTools.encode(course.getLang())));
				assertTrue(marshedString.contains("courses%5B"+i+"%5D%5Bforcetheme%5D="+MoodleTools.encode(course.getForceTheme())));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		});
	}

	private MoodleParamMap serializeMethodValue(Object entity, String parentName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MoodleParamMap map = new MoodleParamMap();
		
		Method[] methods = entity.getClass().getDeclaredMethods();
		for(Method m: methods) {
			if (!m.getName().startsWith("get")) {
				continue;
			}
			
			Object o = m.invoke(entity);
			if (o == null) {
				continue;
			}
			
			String methodName = parentName.toLowerCase() +
				"[" + m.getName().toLowerCase().substring(3)+"]";

			if (m.getReturnType().isArray()) {
				Object[] nestedEntities =  (Object[]) o;
				for (int i=0; i < nestedEntities.length; i++) {
					String nestedMethodName = methodName+"["+i+"]";
					map.put(nestedMethodName, 
						serializeMethodValue(nestedEntities[i], ""));
				}
				continue;
			}
			map.put(methodName, o);
		}

		return map;
	}


	@Test
	public void annotation_test() {
		JavaFileObject test = JavaFileObjects.forResource("ml.jmoodle.commons.MoodleCourse.java");
 		Compilation compilation = com.google.testing.compile.Compiler.javac()
          .withProcessors(new ConvertersAnnotationProcessor())
		  .compile(test);
		
		assert_()
		.about(JavaSourcesSubjectFactory.javaSources())
		.that(Arrays.asList(test))
		.processedWith(new ConvertersAnnotationProcessor())
		.compilesWithoutError()
		.and()
		.generatesSources(JavaFileObjects.forSourceString("com.example.A_Logger",""));

		
		  
		
	}
	@Ignore
	@Test
	public void teste() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Map<String, Object> m = new HashMap<>();
		m.put("id", 1);
		m.put("shortname", "shortname");
		m.put("categoryid", 1);
		m.put("categorysortorder", 1);
		m.put("fullname", "full name");

		Map<String, Object> n1 = new HashMap<>();
		n1.put("name", "name");
		n1.put("value", "value-name");

		Map<String, Object> n2 = new HashMap<>();
		n2.put("name", "name1");
		n2.put("value", "value-1name");

		Set<Map<String, Object>> set = new HashSet<>();
		set.add(n1);
		set.add(n2);

		m.put("courseformatoptions", set);

		MoodleCourse mc = new MoodleCourse();
		BeanUtils.populate(mc, m);
		System.err.println("------------------------------------------");
		System.err.println(mc);
		System.err.println("------------------------------------------");

	}

	private Object test(Map<String, Object> valuesMap, Class clazz) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Object instance = clazz.newInstance();
		Method[] methods = clazz.getDeclaredMethods();
		for(String key: valuesMap.keySet()) {
			// Method getMethod = Arrays.stream(methods)
			// 	.filter(m -> m.getName().toLowerCase().equals("get"+key))
			// 	.findFirst().get();
			if (valuesMap.get(key) instanceof Set) {
				Method addMethod = Arrays.stream(methods)
					.filter(m -> m.getName().toLowerCase().equals("add"+key))
					.findFirst().get();
				Set<Map<String, Object>> nestedEntity = (Set<Map<String, Object>>) valuesMap.get(key);
				for(Map<String, Object> nestedMap: nestedEntity) {
					addMethod.invoke(instance, test(nestedMap, addMethod.getParameterTypes()[0]));
				}
			} else {
				Method setMethod = Arrays.stream(methods)
					.filter(m -> m.getName().toLowerCase().equals("set"+key))
					.findFirst().get();

				setMethod.invoke(instance, valuesMap.get(key));
			}
		}
		return instance;
	}
}
