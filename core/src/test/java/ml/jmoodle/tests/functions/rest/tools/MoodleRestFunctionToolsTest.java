package ml.jmoodle.tests.functions.rest.tools;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ml.jmoodle.commons.Criteria;
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
	public void serialize_criterias_teste() throws UnsupportedEncodingException {
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

}
