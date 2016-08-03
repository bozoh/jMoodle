package ml.jmoodle.functions;

import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.tools.MoodleTools;

public abstract class MoodleWSBaseFunction implements MoodleWSFunction {

	protected static String mdlVersion;

	public MoodleWSBaseFunction(String moodleVersion) throws MoodleWSFucntionException {
		mdlVersion = moodleVersion;
		String since = getSinceVersion();

		try {
			if (MoodleTools.compareVersion(moodleVersion, since) < 0) {
				throw new MoodleWSFucntionException("This function is added in version [" + since
						+ "], but your moodle version is [" + moodleVersion + "]");
			}
		} catch (MoodleConfigException e) {
			throw new MoodleWSFucntionException(e);
		}
	}

}
