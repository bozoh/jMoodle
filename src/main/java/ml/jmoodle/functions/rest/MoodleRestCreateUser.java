package ml.jmoodle.functions.rest;

import java.io.UnsupportedEncodingException;
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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.MoodleWSFunctionCall;
import ml.jmoodle.functions.exceptions.MoodleRestCreateUserException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.exceptions.MoodleWSFunctionCallException;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.functions.rest.tools.MoodleRestUserFunctionsTools;
import ml.jmoodle.tools.MoodleTools;

@MoodleWSFunction(names = { "core_user_create_users", "moodle_user_create_users" })
public class MoodleRestCreateUser extends MoodleWSBaseFunction {
	private static final String SINCE_VERSION = "2.0.0";
	private MoodleRestUserFunctionsTools userFuntionsTools;
	// Using map insted a set to set the Users ids
	private Map<String, MoodleUser> users;

	public MoodleRestCreateUser(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
		super(moodleConfig);
		this.users = new TreeMap<String, MoodleUser>();
		this.userFuntionsTools = new MoodleRestUserFunctionsTools();
	}

	@Override
	public String getFunctionData() throws MoodleRestCreateUserException {
		if (getUsers().isEmpty()) {
			throw new MoodleRestCreateUserException("No users is set");
		}
		try {
			StringBuilder retVal = new StringBuilder();
			retVal.append(MoodleTools.encode("wsfunction")).append("=").append(MoodleTools.encode(getFunctionName()))
					.append("&").append(getUserFuntionsTools().serliazeUsers(getUsers()));
			return retVal.toString();
		} catch (UnsupportedEncodingException e) {
			throw new MoodleRestCreateUserException(e);
		}

	}

	private MoodleRestUserFunctionsTools getUserFuntionsTools() {
		return this.userFuntionsTools;
	}

	/**
	 * @return the users
	 */
	public Set<MoodleUser> getUsers() {
		return new HashSet<MoodleUser>(users.values());
	}

	/**
	 * @param users
	 *            the users to set
	 * @throws MoodleRestCreateUserException
	 */
	public void setUsers(Set<MoodleUser> users) throws MoodleRestCreateUserException {
		for (Iterator<MoodleUser> iterator = users.iterator(); iterator.hasNext();) {
			MoodleUser moodleUser = (MoodleUser) iterator.next();
			addUser(moodleUser);
		}
	}

	/**
	 * 
	 * @param user
	 * @throws MoodleRestCreateUserException
	 *             If a user is already added
	 */
	public void addUser(MoodleUser user) throws MoodleRestCreateUserException {
		if (users.containsKey(user.getUsername()))
			throw new MoodleRestCreateUserException("User is already added:\n" + user.toString());
		this.users.put(user.getUsername(), user);
	}

	@Override
	public String getSinceVersion() {
		return SINCE_VERSION;
	}

	/**
	 * Get the right function name, this functions changes the name in 2.2.0
	 * 
	 * @throws MoodleRestCreateUserException
	 * 
	 * @throws MoodleConfigException
	 */
	@Override
	public String getFunctionName() throws MoodleRestCreateUserException {
		// this funtcions changes the name in 2.2.0
		try {
			if ((MoodleTools.compareVersion(mdlConfig.getVersion(), "2.2.0") < 0))
				return "moodle_user_create_users";
		} catch (MoodleConfigException e) {
			throw new MoodleRestCreateUserException(e);
		}
		return "core_user_create_users";
	}

	/**
	 * Call Create User WS Function
	 * 
	 * @return A set of MoodleUser
	 * @throws MoodleWSFunctionCallException
	 */
	@Override
	public Set<MoodleUser> doCall() throws MoodleRestCreateUserException {
		MoodleWSFunctionCall wsFunctionCall = MoodleWSFunctionCall.getInstance(mdlConfig);
		try {
			return processResponse(wsFunctionCall.call(this));
		} catch (MoodleWSFunctionCallException e) {
			throw new MoodleRestCreateUserException(e);
		}
	}

	private Set<MoodleUser> processResponse(Document response) throws MoodleRestCreateUserException {
		try {
			//
			// <?xml version="1.0" encoding="UTF-8" ?>
			// <RESPONSE>
			// <MULTIPLE>
			// <SINGLE>
			// <KEY name="id">
			// <VALUE>int</VALUE>
			// </KEY>
			// <KEY name="username">
			// <VALUE>string</VALUE>
			// </KEY>
			// </SINGLE>
			// </MULTIPLE>
			// </RESPONSE>

			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("/RESPONSE/MULTIPLE/SINGLE").evaluate(response,
					XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node singleNode = nodeList.item(i);
				Map<String, String> singleValuesMap=MoodleRestFunctionTools.getSingleAttributes(singleNode);
				MoodleUser moodleUser = users.get(singleValuesMap.get("username"));
				moodleUser.setId(Long.parseLong(singleValuesMap.get("id")));
			}

		} catch (XPathExpressionException e) {
			throw new MoodleRestCreateUserException("Erro processing the response:\n" + response.toString());
		}

		return getUsers();
	}

}
