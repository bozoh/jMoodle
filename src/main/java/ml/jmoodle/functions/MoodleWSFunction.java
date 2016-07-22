package ml.jmoodle.functions;

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
	 * @param functionName  Use the MoodleWSFunctions to get the right function class name
	 * @param moodleVersion Moodle Version
	 * @return
	 * @throws MoodleWSFucntionException
	 */
	public static final MoodleWSFunction getFunction(MoodleWSFunctions functionName, String moodleVersion)
			throws MoodleWSFucntionException {

		try {
			MoodleWSFunction.mdlVersion = moodleVersion;
			MoodleWSFunction function = factory(functionName.getValue());
			checkVersion(function.getFunctionName(), moodleVersion, function.getAddedVersion());

			return function;
		} catch (InstantiationException e) {
			throw new MoodleWSFucntionException(e);
		} catch (IllegalAccessException e) {
			throw new MoodleWSFucntionException(e);
		} catch (ClassNotFoundException e) {
			throw new MoodleWSFucntionException(e);
		}

	}
	private static MoodleWSFunction factory(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<? extends MoodleWSFunction> clazz = (Class<? extends MoodleWSFunction>) Class.forName(className);
		return clazz.newInstance();
	}
	
	public MoodleWSFunction(){}
	
	public MoodleWSFunction (String moodleVersion) throws MoodleWSFucntionException {
		MoodleWSFunction.mdlVersion=moodleVersion;
		MoodleWSFunction.checkVersion(getFunctionName(), moodleVersion, getAddedVersion());
	}

	 
	private static void checkVersion(String functionName, String moodleVersion, String functionAddedVersion) throws MoodleWSFucntionException {
		if (MoodleTools.compareVersion(moodleVersion, functionAddedVersion) < 0) {
			throw new MoodleWSFucntionException("The function [" + functionName + "] is only added in version ["
					+ functionAddedVersion + "], but your moodle version is [" + moodleVersion + "]");
		}
		
	}
	public abstract String getFunctionStr();

	public abstract String getAddedVersion();
	public abstract String getFunctionName();

}
