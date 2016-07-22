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

	/**
	 *  Compare 2 version
	 * @param v1
	 * @param v2
	 * @return  - if v1<v2 , 0 if v1=v2, + if v1>v2
	 */
	public static int compareVersion(String v1, String v2) {
		// TODO Auto-generated method stub
		return 0;
	}
}
