package ml.jmoodle.functions.rest.core.coursecategory;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleCourseCategory;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.core.coursecategory.exceptions.MoodleRestUpdateCourseCategoriesException;
import ml.jmoodle.functions.rest.core.coursecategory.tools.MoodleCourseCategoryTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleTools;

/**
 * Update User function by criteria
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_course_update_categories" })
public class MoodleRestUpdateCourseCategories extends MoodleWSBaseFunction {

	private static final String SINCE_VERSION = "2.3.0";
	private MoodleCourseCategoryTools mcct;
	private Set<MoodleCourseCategory> entities;


	public MoodleRestUpdateCourseCategories(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		super(moodleConfig);
		this.entities = new HashSet<>();
		this.mcct = new MoodleCourseCategoryTools();
	}

	@Override
	public String getFunctionData() throws MoodleWSFucntionException {
		if (this.entities.isEmpty()) {
			throw new MoodleRestUpdateCourseCategoriesException(MoodleCommonsErrorMessages.notSet("Criteria"));
		}
		StringBuilder returnData = new StringBuilder(super.getFunctionData());
			returnData.append(this.mcct.serialize(this.entities));
			return returnData.toString();
	}

	@Override
	public String getSinceVersion() throws MoodleWSFucntionException {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleWSFucntionException {
		return "core_course_update_categories";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<MoodleCourseCategory> doCall() throws MoodleWSFucntionException {
		return (Set<MoodleCourseCategory>) super.doCall();
	}

	protected Object processResponse(Document response) throws MoodleRestUpdateCourseCategoriesException {
		return null;
	}

	public void addCatrgory(MoodleCourseCategory entity) throws MoodleRestUpdateCourseCategoriesException {
		verifyEntity(entity);
		this.entities.add(entity);
	}

	private void verifyEntity(MoodleCourseCategory entity) throws MoodleRestUpdateCourseCategoriesException {
		if (entity == null)
			throw new MoodleRestUpdateCourseCategoriesException(
				MoodleCommonsErrorMessages.mustHave("CourseCategory", "", entity)
			);
		
		if (MoodleTools.isEmpty(entity.getId()))
			throw new MoodleRestUpdateCourseCategoriesException(
				MoodleCommonsErrorMessages.mustHave("CourseCategory", "id", entity)
			);	

		if (MoodleTools.isEmpty(entity.getName()))
			throw new MoodleRestUpdateCourseCategoriesException(
				MoodleCommonsErrorMessages.mustHave("CourseCategory", "name", entity)
			);
	}

	public void setCategories(Set<MoodleCourseCategory> entities) throws MoodleRestUpdateCourseCategoriesException {
		for (MoodleCourseCategory e : entities) {
			this.addCatrgory(e);
		}

	}

}
