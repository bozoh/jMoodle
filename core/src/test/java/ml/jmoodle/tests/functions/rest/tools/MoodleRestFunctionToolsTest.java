package ml.jmoodle.tests.functions.rest.tools;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ml.jmoodle.commons.Criteria;
import ml.jmoodle.commons.OptionParameter;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;

/**
 * 
 * Rest functions tools
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 **/
public class MoodleRestFunctionToolsTest {
	@Test
	public void serialize_option_parameters_test() throws UnsupportedEncodingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// options[0][name]= string
		// options[0][value]= string	
		List<OptionParameter> options = new ArrayList<>();
		options.add(new OptionParameter("teste1key", "teste1value"));
		options.add(new OptionParameter("teste2key", "teste2value"));
		options.add(new OptionParameter("teste3key", "teste3value"));

		String retVal = URLDecoder.decode(
			MoodleRestFunctionTools.serializeOptionParameters(options), MoodleConfig.DEFAULT_ENCODING
		);

		assertThat(retVal).contains("options[0][name]="+options.get(0).getName());
		assertThat(retVal).contains("options[0][value]="+options.get(0).getValue());
		assertThat(retVal).contains("options[1][name]="+options.get(1).getName());
		assertThat(retVal).contains("options[1][value]="+options.get(1).getValue());
		assertThat(retVal).contains("options[2][name]="+options.get(2).getName());
		assertThat(retVal).contains("options[2][value]="+options.get(2).getValue());
		assertThat(retVal).contains("&");
	}



	@Test
	public void serialize_criterias_teste() throws UnsupportedEncodingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// criteria[0][key]= string
		// criteria[0][value]= string		
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(new Criteria("teste1key", "teste1value"));
		criterias.add(new Criteria("teste2key", "teste2value"));
		criterias.add(new Criteria("teste3key", "teste3value"));

		String retVal = URLDecoder.decode(
			MoodleRestFunctionTools.serializeCriterias(criterias), MoodleConfig.DEFAULT_ENCODING
		);

		assertTrue(retVal.contains("criteria[0][key]="+criterias.get(0).getKey()));
		assertTrue(retVal.contains("criteria[0][value]="+criterias.get(0).getValue()));
		assertTrue(retVal.contains("criteria[1][key]="+criterias.get(1).getKey()));
		assertTrue(retVal.contains("criteria[1][value]="+criterias.get(1).getValue()));
		assertTrue(retVal.contains("criteria[2][key]="+criterias.get(2).getKey()));
		assertTrue(retVal.contains("criteria[2][value]="+criterias.get(2).getValue()));
		assertTrue(retVal.contains("&"));
	}

	@Test
	public void serialize_entity_ids_teste() throws UnsupportedEncodingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		List<Long> ids = new ArrayList<>();
		ids.add(4l);
		ids.add(5l);
		ids.add(14l);
		ids.add(-4l);

		String retVal = URLDecoder.decode(
			MoodleRestFunctionTools.serializeEntityId("teste", ids), MoodleConfig.DEFAULT_ENCODING
		);
		assertThat(retVal).contains("teste[0]=4");
		assertThat(retVal).contains("teste[1]=5");
		assertThat(retVal).contains("teste[2]=14");
		assertThat(retVal).contains("teste[3]=-4");

	}
	

}
