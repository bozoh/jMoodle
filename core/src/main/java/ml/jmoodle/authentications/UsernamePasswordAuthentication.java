package ml.jmoodle.authentications;

import java.io.UnsupportedEncodingException;

import ml.jmoodle.tools.MoodleParamMap;

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

	private MoodleParamMap mpm;
	
	public UsernamePasswordAuthentication(String username, String password) {
		this.mpm = new MoodleParamMap();
		mpm.put("wsusername", username);
		mpm.put("wspassword", password);
	}

	@Override
	public String getAuthentication() throws UnsupportedEncodingException {
		return mpm.toParamString();
	}
	
	
}
