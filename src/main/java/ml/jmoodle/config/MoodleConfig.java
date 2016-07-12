package ml.jmoodle.config;

import java.net.MalformedURLException;
import java.net.URL;

public class MoodleConfig {
	private URL moodleURL;

	public MoodleConfig(URL moodleURL) {
		this.moodleURL = moodleURL;
	}

	public MoodleConfig(String moodleURL) throws MalformedURLException {
		if (!(moodleURL.trim().toLowerCase().startsWith("http://")
				|| moodleURL.trim().toLowerCase().startsWith("https://"))) {
			moodleURL = "http://" + moodleURL;
		}
		this.moodleURL = new URL(moodleURL);
	}

	/**
	 * @return the moodleURL
	 */
	protected URL getMoodleURL() {
		return moodleURL;
	}
}