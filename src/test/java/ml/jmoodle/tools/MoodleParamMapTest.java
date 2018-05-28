package ml.jmoodle.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

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
	public void testMoodleParamMap() throws UnsupportedEncodingException {
		MoodleParamMap mpm = new MoodleParamMap();
		mpm.put("aá[]ç&","?ãb");
		mpm.put("teste", "teste");
		//See more http://meyerweb.com/eric/tools/dencoder/
		assertEquals("teste=teste&a%C3%A1%5B%5D%C3%A7%26=%3F%C3%A3b", mpm.toParamString());
		

	}

}
