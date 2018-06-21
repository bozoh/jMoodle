package ml.jmoodle.functions.rest.core.user;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.Criteria;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.commons.MoodleWarning;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.core.user.exceptions.MoodleRestGetUsersException;
import ml.jmoodle.functions.rest.core.user.tools.MoodleUserTools;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;

/**
 * Get User function by criteria
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_user_get_users" })
public class MoodleRestGetUsers extends MoodleWSBaseFunction {

	private static final String SINCE_VERSION = "2.5.0";
	private MoodleUserTools tool;
	// Using map instead a set to set the Users ids
	private Set<Criteria> criterias;
	private Set<MoodleWarning> warnings = null;


	
	public MoodleRestGetUsers(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		super(moodleConfig);
		this.criterias = new HashSet<Criteria>();
		this.tool = new MoodleUserTools();
	}

	@Override
	public String getFunctionData() throws MoodleWSFucntionException {
		if (criterias.isEmpty()) {
			throw new MoodleRestGetUsersException(MoodleCommonsErrorMessages.notSet("Criteria"));
		}
		try {
			StringBuilder returnData = new StringBuilder(super.getFunctionData());
			returnData.append(MoodleRestFunctionTools.serializeCriterias(criterias));
			return returnData.toString();
		} catch (UnsupportedEncodingException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new MoodleRestGetUsersException(e);
		}
	}

	@Override
	public String getSinceVersion() throws MoodleWSFucntionException {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleWSFucntionException {
		return "core_user_get_users";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<MoodleUser> doCall() throws MoodleWSFucntionException {
		return (Set<MoodleUser>) super.doCall();
	}

	protected Set<MoodleUser> processResponse(Document response) throws MoodleRestGetUsersException {
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList usersNodeList = (NodeList) xPath.compile("/RESPONSE/SINGLE/KEY[@name=\"users\"]/MULTIPLE/SINGLE")
					.evaluate(response, XPathConstants.NODESET);
			Set<MoodleUser> users = tool.deSerialize(usersNodeList);

			//Processing Warnings
			NodeList warningNodeList = (NodeList) xPath.compile("/RESPONSE/SINGLE/KEY[@name=\"warnings\"]/MULTIPLE/SINGLE")
				.evaluate(response, XPathConstants.NODESET);
			this.warnings = MoodleRestFunctionTools.deSerializeWarnings(warningNodeList);
			
			return users;
		} catch (XPathExpressionException e) {
			throw new MoodleRestGetUsersException(e);
		}
	}

	public void addCriteria(Criteria criteria) throws MoodleRestGetUsersException {
		verifyEntity(criteria);
		this.criterias.add(criteria);
	}

	private void verifyEntity(Criteria criteria) throws MoodleRestGetUsersException {
		if (criteria == null || criteria.getKey() == null || criteria.getKey().trim().isEmpty())
			throw new MoodleRestGetUsersException(MoodleCommonsErrorMessages.mustHave("Criteria", "key", criteria));
	}

	public void setCriterias(Set<Criteria> criterias) throws MoodleRestGetUsersException {
		for (Criteria criteria : criterias) {
			this.addCriteria(criteria);
		}

	}

	public Set<MoodleWarning> getWarnings() {
		return warnings;
	}

	public boolean hasWarnings() {
		return (warnings != null && !warnings.isEmpty());
	}

}
