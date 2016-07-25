package ml.jmoodle.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.tools.exceptions.MoodleToolsException;

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
	 * Compare 2 version
	 * 
	 * @param v1
	 * @param v2
	 * @return - if v1<v2 , 0 if v1=v2, + if v1>v2
	 * @throws MoodleToolsException
	 */
	public static int compareVersion(String v1, String v2) throws MoodleToolsException {
		if (verifyVersion(v1) && verifyVersion(v2)) {
			Matcher v1Match = getMatcher(v1);
			Matcher v2Match = getMatcher(v2);
			Double vd1 = Double.valueOf(v1Match.group(1) + "." + v1Match.group(2) + v1Match.group(3));
			Double vd2 = Double.valueOf(v2Match.group(1) + "." + v2Match.group(2) + v2Match.group(3));
			return vd1.compareTo(vd2);
		} else {
			throw new MoodleToolsException("Invalid moodle version [" + v1 + "] [" + v2 + "]");
		}
	}

	private static Matcher getMatcher(String version) {
		Pattern versionPattern = Pattern.compile("^(\\d)\\.(\\d)\\.(\\d)$");
		return versionPattern.matcher(version);
	}

	public static boolean verifyVersion(String mdlVersion) {
		return getMatcher(mdlVersion).matches();

	}
}
