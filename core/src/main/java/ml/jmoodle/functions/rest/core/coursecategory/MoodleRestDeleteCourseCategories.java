package ml.jmoodle.functions.rest.core.coursecategory;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleCourseCategory;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.core.coursecategory.exceptions.MoodleRestDeleteCourseCategoriesException;
import ml.jmoodle.functions.rest.core.coursecategory.tools.MoodleCourseCategoryTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleTools;

/**
 * Delete User function by criteria
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_course_delete_categories" })
public class MoodleRestDeleteCourseCategories extends MoodleWSBaseFunction {

	private static final String SINCE_VERSION = "2.3.0";
	private MoodleCourseCategoryTools mcct;
	private Set<MoodleCourseCategory> entities;


	public MoodleRestDeleteCourseCategories(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		super(moodleConfig);
		this.entities = new HashSet<>();
		this.mcct = new MoodleCourseCategoryTools();
	}

	@Override
	public String getFunctionData() throws MoodleWSFucntionException  {
		if (this.entities.isEmpty()) {
			throw new MoodleRestDeleteCourseCategoriesException(MoodleCommonsErrorMessages.notSet("Criteria"));
		}

		StringBuilder returnData = new StringBuilder(super.getFunctionData());
		try {
			returnData.append(this.mcct.serializeToDelete(this.entities));
		} catch (UnsupportedEncodingException e) {
			throw new MoodleRestDeleteCourseCategoriesException(e);
		}
		return returnData.toString();
	}

	@Override
	public String getSinceVersion() throws MoodleWSFucntionException {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleWSFucntionException {
		return "core_course_delete_categories";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<MoodleCourseCategory> doCall() throws MoodleWSFucntionException {
		return (Set<MoodleCourseCategory>) super.doCall();
	}

	protected Object processResponse(Document response) throws MoodleRestDeleteCourseCategoriesException {
		return null;
	}

	public void addCatrgory(MoodleCourseCategory entity) throws MoodleRestDeleteCourseCategoriesException {
		verifyEntity(entity);
		this.entities.add(entity);
	}

	private void verifyEntity(MoodleCourseCategory entity) throws MoodleRestDeleteCourseCategoriesException {
		if (entity == null)
			throw new MoodleRestDeleteCourseCategoriesException(
				MoodleCommonsErrorMessages.mustHave("CourseCategory", "", entity)
			);
		
		if (MoodleTools.isEmpty(entity.getId()))
			throw new MoodleRestDeleteCourseCategoriesException(
				MoodleCommonsErrorMessages.mustHave("CourseCategory", "id", entity)
			);	

	}

	public void setCategories(Set<MoodleCourseCategory> entities) throws MoodleRestDeleteCourseCategoriesException {
		for (MoodleCourseCategory e : entities) {
			this.addCatrgory(e);
		}

	}


}
