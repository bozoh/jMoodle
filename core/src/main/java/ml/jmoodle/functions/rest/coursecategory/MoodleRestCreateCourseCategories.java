package ml.jmoodle.functions.rest.coursecategory;

import java.util.HashMap;
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
import ml.jmoodle.commons.MoodleCourseCategory;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.coursecategory.exceptions.MoodleRestCreateCourseCategoriesException;
import ml.jmoodle.functions.rest.coursecategory.tools.MoodleCourseCategoryTools;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleTools;

/**
 * Create User function by criteria
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_course_create_categories" })
public class MoodleRestCreateCourseCategories extends MoodleWSBaseFunction {

	private static final String SINCE_VERSION = "2.3.0";
	private MoodleCourseCategoryTools mcct;
	// Use a map to get track of it
	private Map<String, MoodleCourseCategory> entities;


	public MoodleRestCreateCourseCategories(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		super(moodleConfig);
		this.entities = new HashMap<>();
		this.mcct = new MoodleCourseCategoryTools();
	}

	@Override
	public String getFunctionData() throws MoodleWSFucntionException {
		if (this.entities.isEmpty()) {
			throw new MoodleRestCreateCourseCategoriesException(MoodleCommonsErrorMessages.notSet("Criteria"));
		}
		StringBuilder returnData = new StringBuilder(super.getFunctionData());
			returnData.append(this.mcct.serialize(getEntities()));
			return returnData.toString();
	}

	@Override
	public String getSinceVersion() throws MoodleWSFucntionException {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleWSFucntionException {
		return "core_course_create_categories";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<MoodleCourseCategory> doCall() throws MoodleWSFucntionException {
		return (Set<MoodleCourseCategory>) super.doCall();
	}

	protected Set<MoodleCourseCategory> processResponse(Document response) throws MoodleRestCreateCourseCategoriesException {
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("/RESPONSE/MULTIPLE/SINGLE")
					.evaluate(response, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node singleNode = nodeList.item(i);
				Map<String, Object> singleValuesMap = MoodleRestFunctionTools.getSingleAttributes(singleNode);
				MoodleCourseCategory enitity = this.entities.get(singleValuesMap.get("name"));
				enitity.setId(Long.parseLong((String) singleValuesMap.get("id")));
			}
		} catch (XPathExpressionException e) {
			throw new MoodleRestCreateCourseCategoriesException(e);
		}
		return getEntities();
	}

	public void addCatrgory(MoodleCourseCategory entity) throws MoodleRestCreateCourseCategoriesException {
		verifyEntity(entity);
		entity.setId(null);
		if (MoodleTools.isEmpty(entity.getParent())) {
			entity.setParent(0l);
		}
		this.entities.put(entity.getName(), entity);
	}

	private void verifyEntity(MoodleCourseCategory entity) throws MoodleRestCreateCourseCategoriesException {
		if (entity == null)
			throw new MoodleRestCreateCourseCategoriesException(
				MoodleCommonsErrorMessages.mustHave("CourseCategory", "", entity)
			);
		if (MoodleTools.isEmpty(entity.getName()))
			throw new MoodleRestCreateCourseCategoriesException(
				MoodleCommonsErrorMessages.mustHave("CourseCategory", "name", entity)
			);
	}

	public void setCategories(Set<MoodleCourseCategory> entities) throws MoodleRestCreateCourseCategoriesException {
		for (MoodleCourseCategory e : entities) {
			this.addCatrgory(e);
		}

	}

	private Set<MoodleCourseCategory> getEntities() {
		return new HashSet<MoodleCourseCategory>(entities.values());
	}


}
