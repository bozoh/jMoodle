package ml.jmoodle.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;

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
	 * @param value
	 *            to be encoded
	 * @return URLEncoded string
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String value) throws UnsupportedEncodingException {
		return URLEncoder.encode(value, MoodleConfig.DEFAULT_ENCODING);
	}

	/**
	 * Compare 2 Moodles version
	 * 
	 * @param v1
	 * @param v2
	 * @return - if v1<v2 , 0 if v1=v2, + if v1>v2
	 * @throws MoodleToolsException
	 * @throws MoodleConfigException 
	 */
	public static int compareVersion(String v1, String v2) throws MoodleConfigException {
		MoodleConfig.verifyVersion(v1);
		MoodleConfig.verifyVersion(v2);
		Matcher v1Match = getMatcher(v1);
		Matcher v2Match = getMatcher(v2);
		v1Match.matches();
		v2Match.matches();
		Double vd1 = Double.valueOf(v1Match.group(1) + "." + v1Match.group(2) + v1Match.group(3));
		Double vd2 = Double.valueOf(v2Match.group(1) + "." + v2Match.group(2) + v2Match.group(3));
		return vd1.compareTo(vd2);
	}

	private static Matcher getMatcher(String version) {
		Pattern versionPattern = Pattern.compile(MoodleConfig.MOODLE_VERSION_PATTERN);
		return versionPattern.matcher(version);
	}

	
}
