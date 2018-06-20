package ml.jmoodle.tests.authentications;

import static org.junit.Assert.*;

import org.junit.Test;

import ml.jmoodle.authentications.UsernamePasswordAuthentication;

/**
 * 
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
public class UsernamePasswordAuthenticationTests {
	String username = "John";
	String password = "Doe";

	@Test
	public void getAuthenticationTest() throws Exception {
		UsernamePasswordAuthentication authentication = new UsernamePasswordAuthentication(username, password);
		assertEquals("wsusername=" + username + "&wspassword=" + password, authentication.getAuthentication());
	}

}
