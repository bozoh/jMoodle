package ml.jmoodle.authentication;

import ml.jmoodle.config.MoodleConfig;

abstract class MoodleAuthenticationBase implements MoodleAuthentication{
	private MoodleConfig config;

	protected MoodleAuthenticationBase(MoodleConfig config) {
	
		this.config = config;
	}

	/**
	 * @return the Moodle configuration
	 */
	protected MoodleConfig getConfig() {
		return config;
	}
	
	public String getAuthentication() {
		//TODO
		return null;
		
	}
}
