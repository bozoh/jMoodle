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

	public static final String MOODLE_VERSION_PATTERN = "^(\\d+)\\.(\\d+)\\.(\\d+)$";

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
		
		if (!verifyMoodleVersion(v1)) {
			throw new MoodleConfigException("Invalid moodle version [" + v1 + "]");
		}
		
		if (!verifyMoodleVersion(v2)) {
			throw new MoodleConfigException("Invalid moodle version [" + v2 + "]");
		}

		Matcher m1 = getVersionMacher(v1);
		Matcher m2 = getVersionMacher(v2);
		m1.matches();
		m2.matches();

		Double vd1 = Double.valueOf(m1.group(1) + "." + m1.group(2) + m1.group(3));
		Double vd2 = Double.valueOf(m2.group(1) + "." + m2.group(2) + m2.group(3));

		return vd1.compareTo(vd2);
	}


	private static Matcher getVersionMacher(String v) {
		Pattern versionPattern = Pattern.compile(MOODLE_VERSION_PATTERN);
		return versionPattern.matcher(v);
	}

	/**
	 * Verify the moodle version and return the moodle version regexp matcher
	 * throw an Exception if it is a invalid moodle version
	 * 
	 * @param mdlVersion
	 * @return true if is a valid version
	 */
	public static boolean verifyMoodleVersion(String mdlVersion) {
		Matcher m = getVersionMacher(mdlVersion);
		return (m.matches() && m.groupCount() == 3);
	}

	public static boolean isEmpty(String str) {
		return (str == null || str.trim().isEmpty());
	}

}
