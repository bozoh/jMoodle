/**
 * 
 */
package ml.jmoodle.authentications;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Test;


/**
 * 
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
public class TokenAuthenticationTests {
	
	
	String token = "12345";

	@Test
	public void getAthenticationtest() throws UnsupportedEncodingException {
		TokenAuthentication tokenAuthentication = new TokenAuthentication(token);
		assertEquals("wstoken="+token, tokenAuthentication.getAuthentication());
	}

}
