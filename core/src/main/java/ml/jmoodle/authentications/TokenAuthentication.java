package ml.jmoodle.authentications;

import java.io.UnsupportedEncodingException;

import ml.jmoodle.tools.MoodleParamMap;


/**
 * Moodle Token authetication for WS services
 * @see https://docs.moodle.org/dev/Web_services
 *  
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
public class TokenAuthentication implements MoodleAuthentication {

	private MoodleParamMap mpm;

	
	public TokenAuthentication(String token) {
		this.mpm = new MoodleParamMap();
		mpm.put("wstoken", token);
	}

	@Override
	public String getAuthentication() throws UnsupportedEncodingException{
		return this.mpm.toParamString();

	}

}
