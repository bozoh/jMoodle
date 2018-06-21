package ml.jmoodle.functions.rest.core.user;

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
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.core.user.exceptions.MoodleRestGetUsersByIdException;
import ml.jmoodle.functions.rest.core.user.exceptions.MoodleRestGetUsersException;
import ml.jmoodle.functions.rest.core.user.tools.MoodleUserTools;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleTools;



/**
 * Get User function by user id
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_user_get_users_by_id", "moodle_user_get_users_by_id" })
public class MoodleRestGetUsersById extends MoodleWSBaseFunction {
	private static final String SINCE_VERSION = "2.0.0";
	private Set<Long> entitiesIds;
	private MoodleUserTools tool;

	public MoodleRestGetUsersById(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		super(moodleConfig);
		this.entitiesIds = new HashSet<>();
		this.tool = new MoodleUserTools();
	}

	@Override
	public String getSinceVersion() throws MoodleWSFucntionException {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleWSFucntionException {
		try {
			if ((MoodleTools.compareVersion(mdlConfig.getVersion(), "2.2.0") < 0))
				return "moodle_user_get_users_by_id";
		} catch (MoodleConfigException e) {
			throw new MoodleRestGetUsersByIdException(e);
		}
		return "core_user_get_users_by_id";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<MoodleUser> doCall() throws MoodleWSFucntionException {
		return (Set<MoodleUser>) super.doCall();
	}
	
	@Override
	public String getFunctionData() throws MoodleWSFucntionException {
		if (this.entitiesIds == null || this.entitiesIds.isEmpty()) 
			throw new MoodleRestGetUsersByIdException(
				MoodleCommonsErrorMessages.notSet("Users")
			);
		StringBuilder retVal = new StringBuilder(super.getFunctionData());
		try {
			retVal.append(MoodleRestFunctionTools.serializeEntityId("userids", entitiesIds));
		} catch (UnsupportedEncodingException e) {
			throw new MoodleRestGetUsersByIdException(e);
		}
		return retVal.toString();
	}
	
	@Override
	protected Set<MoodleUser> processResponse(Document response) throws MoodleWSFucntionException {
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList usersNodeList = (NodeList) xPath.compile("/RESPONSE/MULTIPLE/SINGLE")
					.evaluate(response, XPathConstants.NODESET);
			Set<MoodleUser> users = tool.deSerialize(usersNodeList);
			
			return users;
		} catch (XPathExpressionException e) {
			throw new MoodleRestGetUsersException(e);
		}
	}

	
	public void setUsers(Set<MoodleUser> entities) throws MoodleRestGetUsersByIdException {
		for (MoodleUser entity : entities) {
			this.addUser(entity);
		}
	}

	public void addUser(MoodleUser entity) throws MoodleRestGetUsersByIdException {
		verifyEntity(entity);
		this.entitiesIds.add(entity.getId());
	}

	private void verifyEntity(MoodleUser entity) throws MoodleRestGetUsersByIdException {
		if(entity == null) {
			throw new MoodleRestGetUsersByIdException(
				MoodleCommonsErrorMessages.notSet("User")
			);
		} 
		if(MoodleTools.isEmpty(entity.getId())) {
			throw new MoodleRestGetUsersByIdException(
				MoodleCommonsErrorMessages.mustHave("User", "id", entity)
			);
		}
	}

}
