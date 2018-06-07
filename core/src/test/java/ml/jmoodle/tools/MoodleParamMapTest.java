package ml.jmoodle.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.junit.Test;

/**
 * MoodleTools a set of commons tools
 * 
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
public class MoodleParamMapTest {

	@Test
	public void test_MoodleParamMap_simple_types() throws UnsupportedEncodingException {
		MoodleParamMap mpm = new MoodleParamMap();
		mpm.put("aá[]ç&","?ãb");
		mpm.put("teste", "teste");
		String result = URLDecoder.decode(mpm.toParamString(), "UTF-8");
		
		assertTrue(result.contains("teste=teste"));
		assertTrue(result.contains("aá[]ç&=?ãb"));
		
	}

	@Test
	public void test_MoodleParamMap_nested_MoodleParamMap() throws UnsupportedEncodingException {
		MoodleParamMap nested = new MoodleParamMap();
		nested.put("aá[]ç&","?ãb");
		nested.put("testeNest", "teste");

		MoodleParamMap parent = new MoodleParamMap();
		parent.put("awea","as22b");
		parent.put("teste", "teste");
		parent.put("teste[0]nest[0]", nested);
		
		String result = URLDecoder.decode(parent.toParamString(), "UTF-8");
		// System.err.println(result);
		assertTrue(result.contains("awea=as22b"));
		assertTrue(result.contains("teste=teste"));
		assertTrue(result.contains("teste[0]nest[0]aá[]ç&=?ãb"));
		assertTrue(result.contains("teste[0]nest[0]testeNest=teste"));
	}

}
