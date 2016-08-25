package ml.jmoodle.functions.rest;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.MoodleWSFunctionCall;
import ml.jmoodle.functions.exceptions.MoodleRestGetUsersException;
import ml.jmoodle.functions.exceptions.MoodleRestUsersCommonsErrorMessages;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.exceptions.MoodleWSFunctionCallException;
import ml.jmoodle.functions.rest.tools.MoodleRestUserFunctionsTools;
import ml.jmoodle.tools.MoodleTools;

/**
 * Get User function by criteria
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_user_get_users" })
public class MoodleRestGetUsers extends MoodleWSBaseFunction {

	private static final String SINCE_VERSION = "2.5.0";
	private MoodleRestUserFunctionsTools userFuntionsTools;
	// Using map instead a set to set the Users ids
	private Set<Criteria> criterias;

	/**
	 * @return the userFuntionsTools
	 */
	private MoodleRestUserFunctionsTools getUserFuntionsTools() {
		return userFuntionsTools;
	}

	/**
	 * @return the criterias
	 */
	public Set<Criteria> getCriterias() {
		return criterias;
	}

	public MoodleRestGetUsers(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		super(moodleConfig);
		this.criterias = new HashSet<MoodleRestGetUsers.Criteria>();
		this.userFuntionsTools = new MoodleRestUserFunctionsTools();
	}

	@Override
	public String getFunctionData() throws MoodleWSFucntionException {
		if (getCriterias().isEmpty()) {
			throw new MoodleRestGetUsersException(MoodleRestUsersCommonsErrorMessages.notSet("Criteria"));
		}
		try {
			StringBuilder returnData = new StringBuilder(super.getFunctionData());
			returnData.append(getUserFuntionsTools().serliazeCriterias(getCriterias()));
			return returnData.toString();
		} catch (UnsupportedEncodingException e) {
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
			Set<MoodleUser> users = getUserFuntionsTools().unSerializeUsers(usersNodeList);
			return users;
			// TODO Process Warnings
		} catch (XPathExpressionException e) {
			throw new MoodleRestGetUsersException(e);
		}
	}

	public void addCriteria(Criteria criteria) throws MoodleRestGetUsersException {
		if (criteria == null || criteria.getName() == null || criteria.getName().trim().isEmpty())
			throw new MoodleRestGetUsersException(MoodleRestUsersCommonsErrorMessages.mustHave("name", criteria));
		this.criterias.add(criteria);
	}

	public void setCriterias(Set<Criteria> citerias) throws MoodleRestGetUsersException {
		for (Criteria criteria : citerias) {
			this.addCriteria(criteria);
		}

	}

	public class Criteria {

		private String name;
		private String value;

		public Criteria(String name, String value) {
			this.name = name;
			this.value = value;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @param value
		 *            the value to set
		 */
		public void setValue(String value) {
			this.value = value;
		}
	}

}
