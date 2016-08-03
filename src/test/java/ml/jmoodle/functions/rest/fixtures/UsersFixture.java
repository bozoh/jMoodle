package ml.jmoodle.functions.rest.fixtures;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
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
		    add("type",  MoodleUser.CustomFieldType.TEXT.getValue());
		    add("value", random("10101010","100100010", "533122112"));
		}});
		
		//Seriailized user	
		//users%5B0%5D%5Bid%5D=355&users%5B0%5D%5Busername%5D=Rona-Auer&users%5B0%5D%5Bpassword%5D=awe2&users%5B0%5D%5Bfirstname%5D=Rona&users%5B0%5D%5Blastname%5D=Auer&users%5B0%5D%5Bemail%5D=Rona-Auer%40email.test&users%5B0%5D%5Bauth%5D=manual&users%5B0%5D%5Bidnumber%5D=024886573360022&users%5B0%5D%5Blang%5D=en_us&users%5B0%5D%5Bcalendartype%5D=gregorian&users%5B0%5D%5Btheme%5D=aasas&users%5B0%5D%5Btimezone%5D=Sao_Paulo&users%5B0%5D%5Bmailformat%5D=0&users%5B0%5D%5Bdescription%5D=foo+bar&users%5B0%5D%5Bcity%5D=Neil+Koch&users%5B0%5D%5Bcountry%5D=%24lang&users%5B0%5D%5Bfirstnamephonetic%5D=%2Fqwwsss%2F&users%5B0%5D%5Blastnamephonetic%5D=%2Fqwwsss%2F&users%5B0%5D%5Bmiddlename%5D=Yost&users%5B0%5D%5Balternatename%5D=Alva+Bins&users%5B0%5D%5Bcustomfields%5D%5B0%5D%5Btype%5D=brithday&users%5B0%5D%5Bcustomfields%5D%5B0%5D%5Bvalue%5D=533122112&users%5B0%5D%5Bcustomfields%5D%5B1%5D%5Btype%5D=anivers%C3%A1rio&users%5B0%5D%5Bcustomfields%5D%5B1%5D%5Bvalue%5D=100100010&users%5B0%5D%5Bpreferences%5D%5B0%5D%5Btype%5D=maildigest&users%5B0%5D%5Bpreferences%5D%5B0%5D%5Bvalue%5D=1&users%5B0%5D%5Bpreferences%5D%5B1%5D%5Btype%5D=editorformat&users%5B0%5D%5Bpreferences%5D%5B1%5D%5Bvalue%5D=4
		
	}

}
