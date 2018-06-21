package ml.jmoodle.tests.functions.rest.role;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import ml.jmoodle.commons.MoodleUserRoleContext;
import ml.jmoodle.commons.MoodleUserRoleContext.ContextLevel;

public class MoodleUserRoleContextFixtureTemplate implements TemplateLoader {
	@Override
	public void load() {
		Fixture.of(MoodleUserRoleContext.class).addTemplate("valid", new Rule(){
			{
				add("roleid", random(Long.class, range(54, 1111)));
				add("userid", random(Long.class, range(3, 1111)));
				add("contextid", random(Long.class, range(64, 11111)));
				add("contextlevel", random(ContextLevel.class, 
					ContextLevel.SYSTEM, ContextLevel.COURSE_CATEGORY,
					ContextLevel.COURSE, ContextLevel.USER,
					ContextLevel.MODULE, ContextLevel.BLOCK
				));
				add("instanceid", random(Long.class, range(4, 1111)));
			}
		});		
	}
}