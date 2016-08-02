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
			add("id", regex("\\d{3,5}"));
            add("firstname", firstName());
            add("lastname", lastName());
            add("username", "${firstname}-${lastname}");
            add("email", "${username}@email.test");
            add("password", random("asasa","awe2","2332"));
            add("idnumber", regex("\\d{15}"));
            add("lang", random("pt_br", "en_us","ch"));
			add("theme", random("aasas","errr","asa"));
			add("timezone", random("-3","-2","Sao_Paulo"));
			add("description", random("lorem ipsum", "ipsum lorem", "foo bar"));
			add("city", name());
			add("country", "$lang");
			add("firstnamephonetic",random("/asiu/","/AskII/","/qwwsss/"));
			add("lastnamephonetic", random("/asiu/","/AskII/","/qwwsss/"));
			add("middlename", lastName());
			add("alternatename", name());
			add("preferences", has(2).of(MoodleUser.Preference.class,"MoodleRestUserFunctionsToolsTestUser1Preferences"));
			add("customfields", has(2).of(MoodleUser.CustomField.class,"MoodleRestUserFunctionsToolsTestUser1CustomFields"));
            		
		}});
		
		Fixture.of(MoodleUser.Preference.class).addTemplate("MoodleRestUserFunctionsToolsTestUser1Preferences", new Rule(){{
			add("name", random("maildigest", "editorformat", "datefrmt"));
			add("value", random("1","4", "5"));
		}});
		
		Fixture.of(MoodleUser.CustomField.class).addTemplate("MoodleRestUserFunctionsToolsTestUser1CustomFields", new Rule(){{
		    add("name", random("brithday", "borndate", "aniversário"));
		    add("shortname", random("bd", "brn", "niver"));
		    add("type", "string");
		    add("value", random("10101010","100100010", "533122112"));
		}});
		
	}

}
