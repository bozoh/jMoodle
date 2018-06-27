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
import ml.jmoodle.commons.MoodleEnrolmentMethod;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.converters.MoodleEnrolmentMethodConverter;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.exceptions.MoodleWSFunctionCallException;
import ml.jmoodle.functions.rest.core.enrol.exceptions.MoodleRestGetCourseEnrolmentMethodsException;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleParamMap;
import ml.jmoodle.tools.MoodleTools;

/**
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_enrol_get_course_enrolment_methods" })
public class MoodleRestGetCourseEnrolmentMethods extends MoodleWSBaseFunction {
	private static final String SINCE_VERSION = "3.0.0";

	private MoodleCourse course = null;

	public MoodleRestGetCourseEnrolmentMethods(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
		super(moodleConfig);
	}


	/**
	 * Add a set of courses
	 * 
	 * @param entities the courses set
	 * @throws MoodleRestGetCourseEnrolmentMethodsException
	 */
	public void setCourse(MoodleCourse entity) throws MoodleRestGetCourseEnrolmentMethodsException{
		verifyEntity(entity);
		this.course = entity;
	}

	

	/**
	 * Checks "must have" properties
	 */
	private void verifyEntity(MoodleCourse entity) throws MoodleRestGetCourseEnrolmentMethodsException{
		
		if (entity==null)
			throw new MoodleRestGetCourseEnrolmentMethodsException(MoodleCommonsErrorMessages
				.notSet("Course"));

		if (MoodleTools.isEmpty(entity.getId()))
			throw new MoodleRestGetCourseEnrolmentMethodsException(MoodleCommonsErrorMessages
				.mustHave("Course", "id", entity));
		
	}

	@Override
	public String getFunctionData() throws MoodleRestGetCourseEnrolmentMethodsException {
		if (course == null) 
			throw new MoodleRestGetCourseEnrolmentMethodsException(MoodleCommonsErrorMessages
				.notSet("Course"));
		try {
			StringBuilder sb = new StringBuilder(super.getFunctionData());
			sb.append("&");
			MoodleParamMap map = new MoodleParamMap();
			map.put("[courseid]", course.getId());
			sb.append(map.toParamString());
			return sb.toString();
		} catch (UnsupportedEncodingException | MoodleWSFucntionException e) {
			throw new MoodleRestGetCourseEnrolmentMethodsException(e);
		}

	}

	@Override
	public String getSinceVersion() {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleRestGetCourseEnrolmentMethodsException {
		return "core_enrol_get_course_enrolment_methods";
	}

	/**
	 * Call Create Course WS Function
	 * 
	 * @return A set of MoodleUser
	 * @throws MoodleWSFunctionCallException
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Set<MoodleEnrolmentMethod> doCall() throws MoodleWSFucntionException {
		return (Set<MoodleEnrolmentMethod>) super.doCall();
	}

	protected Set<MoodleEnrolmentMethod> processResponse(Document response) throws MoodleRestGetCourseEnrolmentMethodsException {
		Set<MoodleEnrolmentMethod> methods = new HashSet<>();
		MoodleEnrolmentMethodConverter converter = new MoodleEnrolmentMethodConverter();
		try {
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("/RESPONSE/MULTIPLE/SINGLE").evaluate(response,
					XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node singleNode = nodeList.item(i);
				Map<String, Object> singleValuesMap = MoodleRestFunctionTools.getSingleAttributes(singleNode);
				methods.add(converter.toEntity(singleValuesMap));
			}

		} catch (XPathExpressionException e) {
			throw new MoodleRestGetCourseEnrolmentMethodsException(
					MoodleWSFucntionException.errorProcessingResponseMsg(response.toString()));
		}

		return methods;
	}

}
