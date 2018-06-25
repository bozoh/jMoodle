package ml.jmoodle.functions.rest.core.enrol;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.commons.OptionParameter;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.exceptions.MoodleWSFunctionCallException;
import ml.jmoodle.functions.rest.core.enrol.exceptions.MoodleRestGetEnrolledUsersException;
import ml.jmoodle.functions.rest.core.user.tools.MoodleUserTools;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleParamMap;
import ml.jmoodle.tools.MoodleTools;

/**
 * Create Course(s) Function
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_enrol_get_enrolled_users", "moodle_user_get_users_by_courseid" })
public class MoodleRestGetEnrolledUsers extends MoodleWSBaseFunction {
	private static final String SINCE_VERSION = "2.1.0";

	
	private MoodleCourse course = null;
	private Set<OptionParameter> options = null;

	public MoodleRestGetEnrolledUsers(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
		super(moodleConfig);
	}


	/**
	 * Add a set of courses
	 * 
	 * @param entities the courses set
	 * @throws MoodleRestGetEnrolledUsersException
	 */
	public void setCourse(MoodleCourse entity) throws MoodleRestGetEnrolledUsersException{
		verifyEntity(entity);
		this.course = entity;
	}

	

	/**
	 * Checks "must have" properties
	 */
	private void verifyEntity(MoodleCourse entity) throws MoodleRestGetEnrolledUsersException{
		
		if (entity==null)
			throw new MoodleRestGetEnrolledUsersException(MoodleCommonsErrorMessages
				.notSet("Course"));

		if (MoodleTools.isEmpty(entity.getId()))
			throw new MoodleRestGetEnrolledUsersException(MoodleCommonsErrorMessages
				.mustHave("Course", "id", entity));
		
	}

	@Override
	public String getFunctionData() throws MoodleRestGetEnrolledUsersException {
		try {
			StringBuilder sb = new StringBuilder(super.getFunctionData());
			sb.append("&");
			MoodleParamMap map = new MoodleParamMap();
			map.put("[courseid]", course.getId());
			sb.append(map.toParamString());
			if (this.options != null && !this.options.isEmpty()) {
				sb.append("&");
				sb.append(MoodleRestFunctionTools.serializeOptionParameters(this.options));
			}
			return sb.toString();
		} catch (UnsupportedEncodingException | MoodleWSFucntionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new MoodleRestGetEnrolledUsersException(e);
		}

	}

	@Override
	public String getSinceVersion() {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleRestGetEnrolledUsersException {
		// this funtcions changes the name in 2.2.0
		try {
			if ((MoodleTools.compareVersion(mdlConfig.getVersion(), "2.2.0") < 0))
				return "moodle_user_get_users_by_courseid";
		} catch (MoodleConfigException e) {
			throw new MoodleRestGetEnrolledUsersException(e);
		}
		return "core_enrol_get_enrolled_users";
	}

	/**
	 * Call Create Course WS Function
	 * 
	 * @return A set of MoodleUser
	 * @throws MoodleWSFunctionCallException
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Set<MoodleUser> doCall() throws MoodleWSFucntionException {
		return (Set<MoodleUser>) super.doCall();
	}

	protected Set<MoodleUser> processResponse(Document response) throws MoodleRestGetEnrolledUsersException {
		Set<MoodleUser> users = new HashSet<>();
		MoodleUserTools mut = new MoodleUserTools();
		try {
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("/RESPONSE/MULTIPLE/SINGLE").evaluate(response,
					XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node singleNode = nodeList.item(i);
				Map<String, Object> singleValuesMap = MoodleRestFunctionTools.getSingleAttributes(singleNode);
				users.add(mut.toEntity(singleValuesMap));
			}

		} catch (XPathExpressionException e) {
			throw new MoodleRestGetEnrolledUsersException(
					MoodleWSFucntionException.errorProcessingResponseMsg(response.toString()));
		}

		return users;
	}

	public void addOption(OptionParameter optionParameter) {
		if (this.options == null) {
			this.options = new HashSet<>();
		}
		this.options.add(optionParameter);
	}

	public void setOptions(Set<OptionParameter> options) {
		this.options = options;
	}

}
