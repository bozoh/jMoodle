package ml.jmoodle.functions.rest.core.course;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.w3c.dom.Document;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.exceptions.MoodleWSFunctionCallException;
import ml.jmoodle.functions.rest.core.course.exceptions.MoodleRestDeleteCoursesException;
import ml.jmoodle.functions.rest.core.course.tools.MoodleCourseTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleTools;

/**
 * Delete Course(s) Function
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_course_delete_courses"})
public class MoodleRestDeleteCourses extends MoodleWSBaseFunction {
	private static final String SINCE_VERSION = "2.3.0";

	// Using map insted a set to store the entities ids
	private Set<Long> entityids;
	private MoodleCourseTools tool = null;
	public MoodleRestDeleteCourses(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
		super(moodleConfig);
		this.entityids = new HashSet<>();
		tool = new MoodleCourseTools();
	}

	
	/**
	 * Add a set of courses
	 * 
	 * @param entities the courses set
	 * @throws MoodleRestDeleteCoursesException
	 */
	public void setCourses(Set<MoodleCourse> entities) throws MoodleRestDeleteCoursesException  {
		for (Iterator<MoodleCourse> iterator = entities.iterator(); iterator.hasNext();) {
			MoodleCourse MoodleCourse = (MoodleCourse) iterator.next();
			addCourse(MoodleCourse);
		}
	}

	/**
	 * Add a single course in course set
	 * 
	 * @param entity the course
	 * @throws MoodleRestDeleteCoursesException
	 */
	public void addCourse(MoodleCourse entity) throws MoodleRestDeleteCoursesException {
		verifyEntity(entity);		
		this.entityids.add(entity.getId());
	}

	/**
	 * Checks "must have" properties
	 */
	private void verifyEntity(MoodleCourse entity) throws MoodleRestDeleteCoursesException{
		
		if (MoodleTools.isEmpty(entity.getId()))
			throw new MoodleRestDeleteCoursesException(MoodleCommonsErrorMessages
				.mustHave("Course", "id", entity));


	}

	@Override
	public String getFunctionData() throws MoodleRestDeleteCoursesException {
		
		if (this.entityids.isEmpty()) {
			throw new MoodleRestDeleteCoursesException(
				MoodleCommonsErrorMessages.notSet("Courses")
			);
		}
		try {
			StringBuilder retVal = new StringBuilder(super.getFunctionData());
			retVal.append(tool.serializeCoursesId("courseids", entityids));
			return retVal.toString();
		} catch (MoodleWSFucntionException | UnsupportedEncodingException e) {
			throw new MoodleRestDeleteCoursesException(e);
		}

	}

	@Override
	public String getSinceVersion() {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleRestDeleteCoursesException {
		return "core_course_delete_courses";
	}

	/**
	 * Call Delete Course WS Function
	 * 
	 * @return A set of MoodleCourse
	 * @throws MoodleWSFunctionCallException
	 */

	

	protected Object processResponse(Document response)  {
		return null;
	}

}
