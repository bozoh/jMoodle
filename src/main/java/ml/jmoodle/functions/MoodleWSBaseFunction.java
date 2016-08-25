package ml.jmoodle.functions;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;

import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.exceptions.MoodleRestGetUsersException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.exceptions.MoodleWSFunctionCallException;
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
public abstract class MoodleWSBaseFunction implements MoodleWSFunction {

	protected static MoodleConfig mdlConfig;
	protected static final String MOODLE_FUNTION_NAME_PARAM = "wsfunction";

	public MoodleWSBaseFunction(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		mdlConfig = moodleConfig;
		String since = getSinceVersion();
		String moodleVersion = mdlConfig.getVersion();

		try {
			if (MoodleTools.compareVersion(moodleVersion, since) < 0) {
				throw new MoodleWSFucntionException("This function is added in version [" + since
						+ "], but your moodle version is [" + moodleVersion + "]");
			}
		} catch (MoodleConfigException e) {
			throw new MoodleWSFucntionException(e);
		}
	}

	public String getFunctionData() throws MoodleWSFucntionException {
		try {
			StringBuilder fnctData = new StringBuilder();
			fnctData.append(MoodleTools.encode(MOODLE_FUNTION_NAME_PARAM)).append("=")
					.append(MoodleTools.encode(getFunctionName())).append("&");
			return fnctData.toString();
		} catch (UnsupportedEncodingException e) {
			throw new MoodleWSFucntionException(e);
		}
	}

	@Override
	public Object doCall() throws MoodleWSFucntionException {
		try {
			MoodleWSFunctionCall wsFunctionCall = MoodleWSFunctionCall.getInstance(mdlConfig);
			Document response = wsFunctionCall.call(this);
			if (response == null || response.getChildNodes().getLength() <= 0)
				return null;
			return processResponse(response);
		} catch (MoodleWSFunctionCallException e) {
			throw new MoodleRestGetUsersException(e);
		}

	}

	protected abstract Object processResponse(Document response) throws MoodleWSFucntionException;

}
