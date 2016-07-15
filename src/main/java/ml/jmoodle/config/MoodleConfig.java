package ml.jmoodle.config;

import java.net.MalformedURLException;
import java.net.URL;

public class MoodleConfig {
	public static final String MOODLE_REST_ENDPOINT = "/webservice/rest/server.php";

	private URL moodleURL;

	/**
	 * 
	 * Builds Moodle configuration
	 * 
	 * @param moodleURL
	 *            must be the same value of $CFG->wwwroot in moodle config.php
	 *            file
	 * @throws MalformedURLException
	 */
	public MoodleConfig(URL moodleURL) throws MalformedURLException {
		this(moodleURL.toString());
	}

	public MoodleConfig(String moodleURL) throws MalformedURLException {
		if (!(moodleURL.trim().toLowerCase().startsWith("http://")
				|| moodleURL.trim().toLowerCase().startsWith("https://"))) {
			moodleURL = "http://" + moodleURL;
		}
		this.moodleURL = new URL(moodleURL + MOODLE_REST_ENDPOINT);
	}

	/**
	 * @return the moodleURL
	 * 
	 */
	protected URL getMoodleURL() {
		return this.moodleURL;
	}
}