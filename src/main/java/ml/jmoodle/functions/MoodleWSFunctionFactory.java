package ml.jmoodle.functions;

import java.lang.reflect.InvocationTargetException;

import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;


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

public class MoodleWSFunctionFactory {


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
	public static final MoodleWSFunction getFunction(MoodleWSFunctions functionName, MoodleConfig moodleConfig)
			throws MoodleWSFucntionException, MoodleConfigException {
		try {
			MoodleWSFunction function = factory(functionName.getValue(), moodleConfig);
			// checkVersion(function.getFunctionName(), moodleVersion,
			// function.getAddedVersion());
			return function;
		} catch (Exception e) {
			throw new MoodleWSFucntionException(e);
		}

	}

	//To improve testability 
	private static MoodleWSFunction factory(String className, MoodleConfig mdlConfig)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException,
			SecurityException, InvocationTargetException, NoSuchMethodException {

		Class<? extends MoodleWSFunction> clazz = (Class<? extends MoodleWSFunction>) Class.forName(className);
		return clazz.getConstructor(MoodleConfig.class).newInstance(mdlConfig);
	}

	

}
