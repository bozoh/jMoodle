package ml.jmoodle.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hold the basic configuration for sending 
 * WS request to Moodle WS API
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
	private double version = 0d;
	private int majorVersion = 0;
	private int minorVersion = 0;
	private int patchVersion = 0;

	/**
	 * 
	 * Builds Basic Moodle configuration
	 * 
	 * @param moodleURL must be the same value of $CFG->wwwroot 
	 * in moodle config.php file
	 * 
	 * @throws MalformedURLException
	 */

	public MoodleConfig(String moodleURL, String moodleVersion) throws MalformedURLException {
		if (!(moodleURL.trim().toLowerCase().startsWith("http://")
				|| moodleURL.trim().toLowerCase().startsWith("https://"))) {
			this.moodleURL.append("http://");
		}
		this.moodleURL.append(moodleURL).append(MOODLE_REST_ENDPOINT);

		Pattern versionPattern = Pattern.compile("^(\\d)\\.(\\d)\\.(\\d)$");
		Matcher versionMatch = versionPattern.matcher(moodleVersion);
		if (versionMatch.matches()) {
			this.majorVersion = Integer.parseInt(versionMatch.group(1));
			this.minorVersion = Integer.parseInt(versionMatch.group(2));
			this.patchVersion = Integer.parseInt(versionMatch.group(3));
			this.version = Double
					.parseDouble(versionMatch.group(1) + "." + versionMatch.group(2) + versionMatch.group(3));
		}
	}

	/**
	 * @return the version
	 */
	public double getVersion() {
		return this.version;
	}

	/**
	 * @return the majorVersion
	 */
	public int getMajorVersion() {
		return this.majorVersion;
	}

	/**
	 * @return the minorVersion
	 */
	public int getMinorVersion() {
		return this.minorVersion;
	}

	/**
	 * @return the patchVersion
	 */
	public int getPatchVersion() {
		return this.patchVersion;
	}

	/**
	 * @return the Moodle site root URL with rest endpoint
	 * 
	 */

	public URL getMoodleURL() throws MalformedURLException {
		return new URL(this.moodleURL.toString());
	}
}