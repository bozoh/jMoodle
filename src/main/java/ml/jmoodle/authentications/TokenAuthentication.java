package ml.jmoodle.authentications;

import java.io.UnsupportedEncodingException;

import ml.jmoodle.tools.MoodleTools;


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

	private StringBuilder authStr;

	
	public TokenAuthentication(String token) throws UnsupportedEncodingException {
		this.authStr = new StringBuilder("?");
		authStr.append(MoodleTools.encode("wstoken")).append("=")
				.append(MoodleTools.encode(token));
	}

	@Override
	public String getAuthentication(){
		return this.authStr.toString();

	}

}
