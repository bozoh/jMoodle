package ml.jmoodle.tests.functions.rest.role.tools;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import ml.jmoodle.commons.MoodleUserRoleContext;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.rest.core.role.tools.MoodleUserRoleContextTools;

public class MoodleUserRoleContextToolsTest {
	MoodleUserRoleContext entity;
	Set<MoodleUserRoleContext> entities;
	MoodleUserRoleContextTools tool;

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("ml.jmoodle.tests");
	}
	@Before
	public void beforeEach() {
        entity = Fixture.from(MoodleUserRoleContext.class).gimme("valid");
		entities = new LinkedHashSet<>(Fixture.from(MoodleUserRoleContext.class).gimme(23, "valid"));
		tool =  new MoodleUserRoleContextTools();
	}
	@Test
	public void test_serialize() throws UnsupportedEncodingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException  {
		
		doSerializeAssertions(URLDecoder.decode(tool.serialize(entities), MoodleConfig.DEFAULT_ENCODING),
			entities);
	}

	private void doSerializeAssertions(String dataString, Set<MoodleUserRoleContext> entities) {
		int i = 0;
		
		for (MoodleUserRoleContext e : entities) {
			assertThat(dataString).contains("assignments["+i+"][userid]=");
			assertThat(dataString).contains(e.getUserId().toString());
			assertThat(dataString).contains("assignments["+i+"][roleid]=");
			assertThat(dataString).contains(e.getRoleId().toString());
			assertThat(dataString).contains("assignments["+i+"][contextid]=");
			assertThat(dataString).contains(e.getContextId().toString());
			assertThat(dataString).contains("assignments["+i+"][contextlevel]=");
			assertThat(dataString).contains(e.getContextLevel().getValue());
			assertThat(dataString).contains("assignments["+i+"][instanceid]=");
			assertThat(dataString).contains(e.getInstanceId().toString());
			i++;
		}
	}
}
