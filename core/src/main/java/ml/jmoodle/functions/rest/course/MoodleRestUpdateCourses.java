package ml.jmoodle.functions.rest.course;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.commons.MoodleWarning;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.converters.MoodleWarningConverter;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.exceptions.MoodleWSFunctionCallException;
import ml.jmoodle.functions.rest.course.exceptions.MoodleRestUpdateCoursesException;
import ml.jmoodle.functions.rest.course.tools.MoodleCourseTools;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleTools;

/**
 * Update Course(s) Function
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_course_update_courses"})
public class MoodleRestUpdateCourses extends MoodleWSBaseFunction {
	private static final String SINCE_VERSION = "2.5.0";

	// Using map insted a set to store the entities ids
	private Map<String, MoodleCourse> entities;
	private MoodleCourseTools tool = null;
	public MoodleRestUpdateCourses(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
		super(moodleConfig);
		this.entities = new TreeMap<String, MoodleCourse>();
		tool = new MoodleCourseTools();
	}

	/**
	 * @return the Courses
	 */
	public Set<MoodleCourse> getCourses() {
		return new HashSet<MoodleCourse>(entities.values());
	}

	/**
	 * Add a set of courses
	 * 
	 * @param entities the courses set
	 * @throws MoodleRestUpdateCoursesException
	 */
	public void setCourses(Set<MoodleCourse> entities) throws MoodleRestUpdateCoursesException  {
		for (Iterator<MoodleCourse> iterator = entities.iterator(); iterator.hasNext();) {
			MoodleCourse MoodleCourse = (MoodleCourse) iterator.next();
			addCourse(MoodleCourse);
		}
	}

	/**
	 * Add a single course in course set
	 * 
	 * @param entity the course
	 * @throws MoodleRestUpdateCoursesException
	 */
	public void addCourse(MoodleCourse entity) throws MoodleRestUpdateCoursesException {
		verifyEntity(entity);		
		this.entities.put(entity.getShortname(), entity);
	}

	

	/**
	 * Checks "must have" properties
	 */
	private void verifyEntity(MoodleCourse entity) throws MoodleRestUpdateCoursesException{
		
		if (MoodleTools.isEmpty(entity.getId()))
			throw new MoodleRestUpdateCoursesException(MoodleCommonsErrorMessages
				.mustHave("Course", "id", entity));

		if (MoodleTools.isEmpty(entity.getShortname()))
			throw new MoodleRestUpdateCoursesException(MoodleCommonsErrorMessages
				.mustHave("Course", "shortname", entity));
		
		if (MoodleTools.isEmpty(entity.getFullname()))
			throw new MoodleRestUpdateCoursesException(MoodleCommonsErrorMessages
				.mustHave("Course", "fullname", entity));
		
		if (MoodleTools.isEmpty(entity.getCategoryId()))
			throw new MoodleRestUpdateCoursesException(MoodleCommonsErrorMessages
				.mustHave("Course", "categoryid", entity));

	}

	@Override
	public String getFunctionData() throws MoodleRestUpdateCoursesException {
		Set<MoodleCourse> entities = getCourses();
		if (entities.isEmpty()) {
			throw new MoodleRestUpdateCoursesException(
				MoodleCommonsErrorMessages.notSet("Courses")
			);
		}
		try {
			StringBuilder retVal = new StringBuilder(super.getFunctionData());
			retVal.append(tool.serialize(entities));
			return retVal.toString();
		} catch (MoodleWSFucntionException e) {
			throw new MoodleRestUpdateCoursesException(e);
		}

	}

	@Override
	public String getSinceVersion() {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleRestUpdateCoursesException {
		return "core_course_update_courses";
	}

	/**
	 * Call Update Course WS Function
	 * 
	 * @return A set of MoodleCourse
	 * @throws MoodleWSFunctionCallException
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Set<MoodleCourse> doCall() throws MoodleWSFucntionException {
		return (Set<MoodleCourse>) super.doCall();
	}

	protected Set<MoodleWarning> processResponse(Document response) throws MoodleRestUpdateCoursesException {
		Set<MoodleWarning> warnings = new HashSet<>();
		
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			NodeList nodeList = (NodeList) xPath.compile("/RESPONSE/SINGLE/KEY[@name=\"warnings\"]/MULTIPLE/SINGLE").evaluate(response,
					XPathConstants.NODESET);
			MoodleWarningConverter mwc = new MoodleWarningConverter();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node singleNode = nodeList.item(i);
				Map<String, Object> singleValuesMap = MoodleRestFunctionTools.getSingleAttributes(singleNode);
				warnings.add(mwc.toEntity(singleValuesMap));
			}

		} catch (XPathExpressionException e) {
			throw new MoodleRestUpdateCoursesException(
					MoodleWSFucntionException.errorProcessingResponseMsg(response.toString()));
		}

		return warnings;
	}

}
