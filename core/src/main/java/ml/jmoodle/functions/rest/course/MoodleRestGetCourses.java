package ml.jmoodle.functions.rest.course;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.course.exceptions.MoodleRestGetCoursesException;
import ml.jmoodle.functions.rest.course.tools.MoodleCourseTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleTools;

/**
 * Get Course function by criteria
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_course_get_courses", "moodle_course_get_courses" })
public class MoodleRestGetCourses extends MoodleWSBaseFunction {

	private static final String SINCE_VERSION = "2.0.0";
	
	// Using map instead a set to set the Courses ids
	private Set<Long> coursesIds;
	private MoodleCourseTools mct;

	

	public MoodleRestGetCourses(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		super(moodleConfig);
		this.coursesIds = new LinkedHashSet<Long>();
		this.mct = new MoodleCourseTools();
	}

	@Override
	public String getFunctionData() throws MoodleWSFucntionException {
		try {
			StringBuilder returnData = new StringBuilder(super.getFunctionData());
			if (this.coursesIds.size() > 0) {
				returnData.append(mct.serializeCoursesId(this.coursesIds));
			}
			return returnData.toString();
		} catch (UnsupportedEncodingException e) {
			throw new MoodleRestGetCoursesException(e);
		}
	}

	@Override
	public String getSinceVersion() throws MoodleWSFucntionException {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleWSFucntionException {
		// this funtcions changes the name in 2.2.0
		try {
			if ((MoodleTools.compareVersion(mdlConfig.getVersion(), "2.2.0") < 0))
				return "moodle_course_get_courses";
		} catch (MoodleConfigException e) {
			throw new MoodleRestGetCoursesException(e);
		}
		return "core_course_get_courses";
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<MoodleCourse> doCall() throws MoodleWSFucntionException {
		return (Set<MoodleCourse>) super.doCall();
	}

	protected Set<MoodleCourse> processResponse(Document response) throws MoodleRestGetCoursesException {
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList entitiesNodeList = (NodeList) xPath.compile("/RESPONSE/MULTIPLE/SINGLE")
					.evaluate(response, XPathConstants.NODESET);
			Set<MoodleCourse> entities = mct.deSerialize(entitiesNodeList);
			return entities;
			// TODO Process Warnings
		} catch (XPathExpressionException e) {
			throw new MoodleRestGetCoursesException(e);
		}
	}

	public void addCourseId(Long courseId) throws MoodleRestGetCoursesException {
		if (MoodleTools.isEmpty(courseId))
			throw new MoodleRestGetCoursesException("Invalid course id: [" + courseId +"]");
		this.coursesIds.add(courseId);
	}

	public void setCorusesIds(Set<Long> coursesId) throws MoodleRestGetCoursesException {
		for (Long courseId : coursesId) {
			this.addCourseId(courseId);
		}

	}

}
