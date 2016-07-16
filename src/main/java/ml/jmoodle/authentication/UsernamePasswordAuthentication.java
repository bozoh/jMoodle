package ml.jmoodle.authentication;

import java.io.UnsupportedEncodingException;

import ml.jmoodle.tools.MoodleTools;

/**
 * Moodle Username and password authetication for WS services
 * @see https://docs.moodle.org/dev/Web_services
 * 
 * 
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
public class UsernamePasswordAuthentication implements MoodleAuthentication {

	private StringBuilder authStr;
	
	public UsernamePasswordAuthentication(String username, String password) throws UnsupportedEncodingException {
		this.authStr = new StringBuilder("?");
		authStr.append(MoodleTools.encode("wsusername")).append("=")
				.append(MoodleTools.encode(username))
				.append("&")
				.append(MoodleTools.encode("wspassword")).append("=")
				.append(MoodleTools.encode(password));
	}

	@Override
	public String getAuthentication()  {
		return authStr.toString();
	}
	
	
}
