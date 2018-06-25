package ml.jmoodle.tests.tools;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import ml.jmoodle.commons.OptionParameter;


public class OptionParameterTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(OptionParameter.class).addTemplate("valid", new Rule(){{
			add("name", regex("[A-Za-z0-9_ ]{3,10}"));
			add("value", regex("[A-Za-z0-9_ ]{3,10}"));
		}});
	}

}