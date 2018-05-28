package ml.jmoodle.configs;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ml.jmoodle.authentications.MoodleAuthentication;

import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.tools.MoodleTools;

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
	

	private StringBuilder moodleURL = new StringBuilder();
	private MoodleAuthentication mdlAuth;
	private String version;

	/**
	 * 
	 * Builds Basic Moodle configuration
	 * 
	 * @param moodleURL - must be the same value of $CFG->wwwroot in moodle config.php file
	 * @param mdlAuth - moodle auth schema (token or usr/passwd)
	 * @param moodleVersion - Moodle version
	 * 
	 * @throws MalformedURLException
	 * @throws MoodleConfigException
	 */

	public MoodleConfig(String moodleURL, MoodleAuthentication mdlAuth, String moodleVersion) throws MoodleConfigException {
		if (!MoodleTools.verifyMoodleVersion(moodleVersion)) {
			throw new MoodleConfigException("Invalid moodle version [" + moodleVersion + "]");
		}
		this.version = moodleVersion;
		this.mdlAuth = mdlAuth; 
		if (!(moodleURL.trim().toLowerCase().startsWith("http://")
				|| moodleURL.trim().toLowerCase().startsWith("https://"))) {
			this.moodleURL.append("http://");
		}
		try {
			this.moodleURL.append(moodleURL)
			.append(MOODLE_REST_ENDPOINT).append("?")
			.append(this.mdlAuth.getAuthentication());
		} catch (UnsupportedEncodingException e) {
			throw new MoodleConfigException(e);
		}
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
}