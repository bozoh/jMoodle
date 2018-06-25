package ml.jmoodle.functions.rest.core.enrol;

import java.io.UnsupportedEncodingException;
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
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.exceptions.MoodleWSFunctionCallException;
import ml.jmoodle.functions.rest.core.course.tools.MoodleCourseTools;
import ml.jmoodle.functions.rest.core.enrol.exceptions.MoodleRestGetEnrolledCoursesException;
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
@MoodleWSFunction(names = { "core_enrol_get_users_courses", "moodle_enrol_get_users_courses" })
public class MoodleRestGetEnrolledCourses extends MoodleWSBaseFunction {
	private static final String SINCE_VERSION = "2.0.0";

	
	private MoodleUser user;
	public MoodleRestGetEnrolledCourses(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
		super(moodleConfig);
	}


	/**
	 * Add a set of courses
	 * 
	 * @param entities the courses set
	 * @throws MoodleRestGetEnrolledCoursesException
	 */
	public void setUser(MoodleUser entity) throws MoodleRestGetEnrolledCoursesException{
		verifyEntity(entity);
		this.user = entity;
	}

	

	/**
	 * Checks "must have" properties
	 */
	private void verifyEntity(MoodleUser entity) throws MoodleRestGetEnrolledCoursesException{
		
		if (entity==null)
			throw new MoodleRestGetEnrolledCoursesException(MoodleCommonsErrorMessages
				.notSet("User"));

		if (MoodleTools.isEmpty(entity.getId()))
			throw new MoodleRestGetEnrolledCoursesException(MoodleCommonsErrorMessages
				.mustHave("User", "id", entity));
		
	}

	@Override
	public String getFunctionData() throws MoodleRestGetEnrolledCoursesException {
		try {
			StringBuilder sb = new StringBuilder(super.getFunctionData());
			sb.append("&");
			MoodleParamMap map = new MoodleParamMap();
			map.put("[userid]", user.getId());
			return sb.append(map.toParamString()).toString();
		} catch (UnsupportedEncodingException | MoodleWSFucntionException e) {
			throw new MoodleRestGetEnrolledCoursesException(e);
		}

	}

	@Override
	public String getSinceVersion() {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleRestGetEnrolledCoursesException {
		// this funtcions changes the name in 2.2.0
		try {
			if ((MoodleTools.compareVersion(mdlConfig.getVersion(), "2.2.0") < 0))
				return "moodle_enrol_get_users_courses";
		} catch (MoodleConfigException e) {
			throw new MoodleRestGetEnrolledCoursesException(e);
		}
		return "core_enrol_get_users_courses";
	}

	/**
	 * Call Create Course WS Function
	 * 
	 * @return A set of MoodleCourse
	 * @throws MoodleWSFunctionCallException
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Set<MoodleCourse> doCall() throws MoodleWSFucntionException {
		return (Set<MoodleCourse>) super.doCall();
	}

	protected Set<MoodleCourse> processResponse(Document response) throws MoodleRestGetEnrolledCoursesException {
		Set<MoodleCourse> courses = new HashSet<>();
		MoodleCourseTools mct = new MoodleCourseTools();
		try {
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("/RESPONSE/MULTIPLE/SINGLE").evaluate(response,
					XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node singleNode = nodeList.item(i);
				Map<String, Object> singleValuesMap = MoodleRestFunctionTools.getSingleAttributes(singleNode);
				courses.add(mct.toEntity(singleValuesMap));
			}

		} catch (XPathExpressionException e) {
			throw new MoodleRestGetEnrolledCoursesException(
					MoodleWSFucntionException.errorProcessingResponseMsg(response.toString()));
		}

		return courses;
	}

}
