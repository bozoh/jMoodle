package ml.jmoodle.functions.rest.coursecategory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.Criteria;
import ml.jmoodle.commons.MoodleCourseCategory;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.coursecategory.exceptions.MoodleRestGetCourseCategoriesException;
import ml.jmoodle.functions.rest.coursecategory.tools.MoodleCourseCategoryTools;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleTools;

/**
 * Get User function by criteria
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_course_get_categories" })
public class MoodleRestGetCourseCategories extends MoodleWSBaseFunction {

	private static final String SINCE_VERSION = "2.3.0";
	private MoodleCourseCategoryTools mcct;
	private List<Criteria> criterias;


	public MoodleRestGetCourseCategories(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		super(moodleConfig);
		this.criterias = new ArrayList<Criteria>();
		this.mcct = new MoodleCourseCategoryTools();
	}

	@Override
	public String getFunctionData() throws MoodleWSFucntionException {
		if (this.criterias.isEmpty()) {
			throw new MoodleRestGetCourseCategoriesException(MoodleCommonsErrorMessages.notSet("Criteria"));
		}
		StringBuilder returnData = new StringBuilder(super.getFunctionData());
			try {
				returnData.append(MoodleRestFunctionTools.serializeCriterias(this.criterias));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | UnsupportedEncodingException e) {
				throw new MoodleRestGetCourseCategoriesException(e);
			}
			return returnData.toString();
	}

	@Override
	public String getSinceVersion() throws MoodleWSFucntionException {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleWSFucntionException {
		return "core_course_get_categories";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<MoodleCourseCategory> doCall() throws MoodleWSFucntionException {
		return (Set<MoodleCourseCategory>) super.doCall();
	}

	protected Set<MoodleCourseCategory> processResponse(Document response) throws MoodleRestGetCourseCategoriesException {
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList usersNodeList = (NodeList) xPath.compile("/RESPONSE/MULTIPLE/SINGLE")
					.evaluate(response, XPathConstants.NODESET);
			Set<MoodleCourseCategory> users = mcct.deSerialize(usersNodeList);
			return users;
		} catch (XPathExpressionException e) {
			throw new MoodleRestGetCourseCategoriesException(e);
		}
	}

	public void addCriteria(Criteria criteria) throws MoodleRestGetCourseCategoriesException {
		if (criteria == null)
			throw new MoodleRestGetCourseCategoriesException(
				MoodleCommonsErrorMessages.mustHave("Criteria", "", criteria)
			);
		if (MoodleTools.isEmpty(criteria.getKey()))
			throw new MoodleRestGetCourseCategoriesException(
				MoodleCommonsErrorMessages.mustHave("Criteria", "key", criteria)
			);
		if (MoodleTools.isEmpty(criteria.getKey()))
			throw new MoodleRestGetCourseCategoriesException(
				MoodleCommonsErrorMessages.mustHave("Criteria", "value", criteria)
			);
		
			
		this.criterias.add(criteria);
	}

	public void setCriterias(Set<Criteria> citerias) throws MoodleRestGetCourseCategoriesException {
		for (Criteria criteria : citerias) {
			this.addCriteria(criteria);
		}

	}


}
