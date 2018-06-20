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
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.exceptions.MoodleWSFunctionCallException;
import ml.jmoodle.functions.rest.course.exceptions.MoodleRestCreateCoursesException;
import ml.jmoodle.functions.rest.course.tools.MoodleCourseTools;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
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
@MoodleWSFunction(names = { "core_course_create_courses", "moodle_course_create_courses" })
public class MoodleRestCreateCourses extends MoodleWSBaseFunction {
	private static final String SINCE_VERSION = "2.0.0";

	// Using map insted a set to store the entities ids
	private Map<String, MoodleCourse> entities;
	private MoodleCourseTools tool = null;
	public MoodleRestCreateCourses(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
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
	 * @throws MoodleRestCreateCoursesException
	 */
	public void setCourses(Set<MoodleCourse> entities) throws MoodleRestCreateCoursesException  {
		for (Iterator<MoodleCourse> iterator = entities.iterator(); iterator.hasNext();) {
			MoodleCourse MoodleCourse = (MoodleCourse) iterator.next();
			addCourse(MoodleCourse);
		}
	}

	/**
	 * Add a single course in course set
	 * 
	 * @param entity the course
	 * @throws MoodleRestCreateCoursesException
	 */
	public void addCourse(MoodleCourse entity) throws MoodleRestCreateCoursesException {
		verifyEntity(entity);		
		this.entities.put(entity.getShortname(), entity);
	}

	

	/**
	 * Checks "must have" properties
	 */
	private void verifyEntity(MoodleCourse entity) throws MoodleRestCreateCoursesException{
		
		if (MoodleTools.isEmpty(entity.getShortname()))
			throw new MoodleRestCreateCoursesException(MoodleCommonsErrorMessages
				.mustHave("Course", "shortname", entity));
		
		if (MoodleTools.isEmpty(entity.getFullname()))
			throw new MoodleRestCreateCoursesException(MoodleCommonsErrorMessages
				.mustHave("Course", "fullname", entity));
		
		if (MoodleTools.isEmpty(entity.getCategoryId()))
			throw new MoodleRestCreateCoursesException(MoodleCommonsErrorMessages
				.mustHave("Course", "categoryid", entity));

	}

	@Override
	public String getFunctionData() throws MoodleRestCreateCoursesException {
		Set<MoodleCourse> entities = getCourses();
		if (entities.isEmpty()) {
			throw new MoodleRestCreateCoursesException(
				MoodleCommonsErrorMessages.notSet("Couses")
			);
		}
		try {
			StringBuilder retVal = new StringBuilder(super.getFunctionData());
			retVal.append(tool.serialize(entities));
			return retVal.toString();
		} catch (MoodleWSFucntionException e) {
			throw new MoodleRestCreateCoursesException(e);
		}

	}

	@Override
	public String getSinceVersion() {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleRestCreateCoursesException {
		// this funtcions changes the name in 2.2.0
		try {
			if ((MoodleTools.compareVersion(mdlConfig.getVersion(), "2.2.0") < 0))
				return "moodle_course_create_courses";
		} catch (MoodleConfigException e) {
			throw new MoodleRestCreateCoursesException(e);
		}
		return "core_course_create_courses";
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

	protected Set<MoodleCourse> processResponse(Document response) throws MoodleRestCreateCoursesException {
		try {
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("/RESPONSE/MULTIPLE/SINGLE").evaluate(response,
					XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node singleNode = nodeList.item(i);
				Map<String, Object> singleValuesMap = MoodleRestFunctionTools.getSingleAttributes(singleNode);
				MoodleCourse MoodleCourse = entities.get(singleValuesMap.get("shortname"));
				MoodleCourse.setId(Long.parseLong((String) singleValuesMap.get("id")));
			}

		} catch (XPathExpressionException e) {
			throw new MoodleRestCreateCoursesException(
					MoodleWSFucntionException.errorProcessingResponseMsg(response.toString()));
		}

		return getCourses();
	}

}
