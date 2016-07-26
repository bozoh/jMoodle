package ml.jmoodle.functions;

import java.lang.reflect.InvocationTargetException;

import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.tools.MoodleTools;

/**
 * 
 * Base class to create functions
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 *
 * @see https://docs.moodle.org/dev/Web_service_API_functions
 */

public abstract class MoodleWSFunction {

	protected static String mdlVersion;

	/**
	 * WS function factory
	 * 
	 * @param functionName
	 *            Use the MoodleWSFunctions to get the right function class name
	 * @param moodleVersion
	 *            Moodle Version
	 * @return
	 * @throws MoodleWSFucntionException
	 * @throws MoodleConfigException
	 * @throws MoodleToolsException
	 */
	public static final MoodleWSFunction getFunction(MoodleWSFunctions functionName, String moodleVersion)
			throws MoodleWSFucntionException, MoodleConfigException {
		MoodleWSFunction.mdlVersion = moodleVersion;
		try {
			MoodleWSFunction function = factory(functionName.getValue());
			// checkVersion(function.getFunctionName(), moodleVersion,
			// function.getAddedVersion());
			return function;
		} catch (Exception e) {
			throw new MoodleWSFucntionException(e);
		}

	}

	private static MoodleWSFunction factory(String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException,
			SecurityException, InvocationTargetException, NoSuchMethodException {

		Class<? extends MoodleWSFunction> clazz = (Class<? extends MoodleWSFunction>) Class.forName(className);
		return clazz.getConstructor(String.class).newInstance(mdlVersion);
	}

	public MoodleWSFunction() {
	}

	public MoodleWSFunction(String moodleVersion) throws MoodleWSFucntionException, MoodleConfigException {
		mdlVersion = moodleVersion;
		String addVersion = getAddedVersion();
		if (MoodleTools.compareVersion(moodleVersion, addVersion) < 0) {
			throw new MoodleWSFucntionException("The function [" + getFunctionName() + "] is only added in version ["
					+ addVersion + "], but your moodle version is [" + moodleVersion + "]");
		}
	}

	private static void checkVersion(String functionName, String moodleVersion, String functionAddedVersion)
			throws MoodleWSFucntionException, MoodleConfigException {

	}

	public abstract String getFunctionStr();

	public abstract String getAddedVersion();

	public abstract String getFunctionName() throws MoodleConfigException;

}
