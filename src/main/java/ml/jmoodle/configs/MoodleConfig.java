package ml.jmoodle.configs;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ml.jmoodle.configs.expections.MoodleConfigException;

/**
 * Hold the basic configuration for sending WS request to Moodle WS API
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */

public class MoodleConfig {
	public static final String MOODLE_REST_ENDPOINT = "/webservice/rest/server.php";
	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final String MOODLE_VERSION_PATTERN = "^(\\d+)\\.(\\d+)\\.(\\d+)$";

	private StringBuilder moodleURL = new StringBuilder();
	private String version;

	/**
	 * 
	 * Builds Basic Moodle configuration
	 * 
	 * @param moodleURL
	 *            must be the same value of $CFG->wwwroot in moodle config.php
	 *            file
	 * 
	 * @throws MalformedURLException
	 * @throws MoodleConfigException
	 */

	public MoodleConfig(String moodleURL, String moodleVersion) throws MoodleConfigException {
		MoodleConfig.verifyVersion(moodleVersion);
		this.version = moodleVersion;

		if (!(moodleURL.trim().toLowerCase().startsWith("http://")
				|| moodleURL.trim().toLowerCase().startsWith("https://"))) {
			this.moodleURL.append("http://");
		}
		this.moodleURL.append(moodleURL).append(MOODLE_REST_ENDPOINT);
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @return the Moodle site root URL with rest endpoint
	 * 
	 */

	public URL getMoodleURL() throws MalformedURLException {
		return new URL(this.moodleURL.toString());
	}

	/**
	 * Verify the moodle version and return the moodle version regexp matcher
	 * throw an Exception if it is a invalid moodle version
	 * 
	 * @param mdlVersion
	 * @return the moodle version regexp matcher
	 * @throws MoodleConfigException
	 */
	public static Matcher verifyVersion(String mdlVersion) throws MoodleConfigException {
		Pattern versionPattern = Pattern.compile(MoodleConfig.MOODLE_VERSION_PATTERN);
		Matcher matcher = versionPattern.matcher(mdlVersion);
		boolean haveMatch = matcher.matches();
		if (!(haveMatch && matcher.groupCount() == 3)) {
			throw new MoodleConfigException("Invalid moodle version [" + mdlVersion + "]");
		}

		return matcher;
	}

}