package ml.jmoodle.functions.rest.fixtures;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.function.impl.AssociationFunctionImpl;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import ml.jmoodle.commons.MoodleUser;

public class UsersFixture implements TemplateLoader{

	@Override
	public void load() {
		Fixture.of(MoodleUser.class).addTemplate("MoodleRestUserFunctionsToolsTestUser1", new Rule() {{
			add("id", random(Long.class, range(1L, 200L)));
            add("firstname", random("John", "Paul", "Foo", "Bar", "Carlos", "Alexandre"));
            add("lastname", random("John", "Paul", "Foo", "Bar", "Carlos", "Alexandre"));
            add("username", "${firstname}-${lastname}");
            add("email", "${username}@email.test");
            add("password", AssociationFunctionImpl 
            		
            		//instant("18 years ago"));
            add("address", one(Address.class, "valid"));
		}});
		
	}

}
