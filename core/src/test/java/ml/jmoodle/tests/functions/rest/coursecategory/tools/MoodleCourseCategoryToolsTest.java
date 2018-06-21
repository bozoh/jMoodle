package ml.jmoodle.tests.functions.rest.coursecategory.tools;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.HashMap;
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
import ml.jmoodle.commons.MoodleCourseCategory;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.rest.core.coursecategory.tools.MoodleCourseCategoryTools;
import ml.jmoodle.tools.MoodleTools;

public class MoodleCourseCategoryToolsTest {
	MoodleCourseCategory mc;
	Set<MoodleCourseCategory> mcs;
	MoodleCourseCategoryTools tool;

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("ml.jmoodle.tests.functions.rest.coursecategory");
	}
	@Before
	public void beforeEach() {
		mc = Fixture.from(MoodleCourseCategory.class).gimme("valid");
		mcs = new LinkedHashSet<>(Fixture.from(MoodleCourseCategory.class).gimme(3, "valid"));
		tool =  new MoodleCourseCategoryTools();
	}
	@Test
	public void test_serialize() throws UnsupportedEncodingException{
		assertMoodleCourseCategory(
			URLDecoder.decode(tool.serialize(mcs), MoodleConfig.DEFAULT_ENCODING),
			mcs
		);
	}

	private void assertMoodleCourseCategory(String serializedString, Set<MoodleCourseCategory> mcs) throws UnsupportedEncodingException {
		List<MoodleCourseCategory> entities = new LinkedList<>(mcs);
		for(int i=0; i< entities.size(); i++) {
			MoodleCourseCategory entity = entities.get(i);
		
			assertThat(serializedString).contains("categories[" + i +"]");
			assertThat(serializedString).contains("categories[" + i +"][id]="+entity.getId());
			assertThat(serializedString).contains("categories["+i+"][name]="+entity.getName());
			assertThat(serializedString).contains("categories["+i+"][idnumber]="+entity.getIdNumber());
			assertThat(serializedString).contains("categories["+i+"][parent]="+entity.getParent());
			assertThat(serializedString).contains("categories["+i+"][sortorder]="+entity.getSortOrder());
			assertThat(serializedString).contains("categories["+i+"][description]="+entity.getDescription());
			assertThat(serializedString).contains("categories["+i+"][descriptionformat]="+entity.getDescriptionFormat().getValue());
			assertThat(serializedString).contains("categories["+i+"][theme]="+entity.getTheme());
		};
		assertThat(serializedString).contains("&");
	}

		
	@Test
	public void test_to_entity() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Map<String, Object> m = new HashMap<>();
		m.put("id", "1");
		m.put("name", "shortname");
		m.put("parent", "1");
		m.put("idnumber", "1");
		m.put("description", "full name");

		

		MoodleCourseCategory mcTest = tool.toEntity(m);
		assertEquals(Long.valueOf((String) m.get("id")), mcTest.getId());
		assertEquals(m.get("name"), mcTest.getName());
		assertEquals(m.get("idnumber"), mcTest.getIdNumber());
		assertEquals(Long.valueOf((String)m.get("parent")), mcTest.getParent());
		assertEquals(m.get("description"), mcTest.getDescription());
		
	}

	
	@Test
	public void serialize_to_delete_test() throws UnsupportedEncodingException{
		
		assertCourseCategoriesDeleteSerialization(mcs, 
			URLDecoder.decode(tool.serializeToDelete(mcs), MoodleConfig.DEFAULT_ENCODING)
		);
	}
	
	private void assertCourseCategoriesDeleteSerialization(Set<MoodleCourseCategory> coursesIds, String dataString) throws UnsupportedEncodingException {
		List<MoodleCourseCategory> cids = new LinkedList<>(coursesIds);
		
		for(int i=0; i< cids.size(); i++) {
			MoodleCourseCategory entity = cids.get(i);
			assertTrue(dataString.contains("categories["+ i +"][id]="));
			assertTrue(dataString.contains(entity.getId().toString()));
			if (!MoodleTools.isEmpty(entity.getNewParent())) {
				assertTrue(dataString.contains("categories["+ i +"][newparent]="));
				assertTrue(dataString.contains(entity.getNewParent().toString()));
			}

			if (entity.isRecursive()) {
				assertTrue(dataString.contains("categories["+ i +"][recursive]="));
				assertTrue(dataString.contains(entity.getRecursive().toString()));
			}
			i++;
		}
		assertTrue(dataString.contains("&"));
	}

}
