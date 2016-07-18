package ml.jmoodle.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ml.jmoodle.configs.MoodleConfig;

/**
 * MoodleTools a set of commons tools
 * 
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
public class MoodleTools {

	/**
	 * A encoder warper 
	 * 
	 * @param value to be encoded
	 * @return URLEncoded string
	 * @throws UnsupportedEncodingException
	 */
	public static String encode (String value) throws UnsupportedEncodingException {
		return URLEncoder.encode(value, MoodleConfig.DEFAULT_ENCODING);
	}
}
