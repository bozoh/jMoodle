package ml.jmoodle.functions.rest.core.user;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.core.user.exceptions.MoodleRestUpdateUsersException;
import ml.jmoodle.functions.rest.core.user.tools.MoodleUserTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleTools;

/**
 * Update User(s) Function
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_user_update_users", "moodle_user_update_users" })
public class MoodleRestUpdateUsers extends MoodleWSBaseFunction {

	private static final String SINCE_VERSION = "2.0.0";
	private Set<MoodleUser> mdlUsers;
	private MoodleUserTools tool;

	public MoodleRestUpdateUsers(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		super(moodleConfig);
		this.mdlUsers = new HashSet<MoodleUser>();
		this.tool = new MoodleUserTools();
	}

	@Override
	public String getFunctionData() throws MoodleWSFucntionException {
		if (mdlUsers.isEmpty()) {
			throw new MoodleRestUpdateUsersException(MoodleCommonsErrorMessages.notSet("Users"));
		}
		
		StringBuilder fnctData = new StringBuilder(super.getFunctionData());
		fnctData.append(tool.serialize(mdlUsers));
		return fnctData.toString();
	}

	@Override
	public String getSinceVersion() throws MoodleWSFucntionException {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleWSFucntionException {
		try {
			if ((MoodleTools.compareVersion(mdlConfig.getVersion(), "2.2.0") < 0))
				return "moodle_user_update_users";
		} catch (MoodleConfigException e) {
			throw new MoodleRestUpdateUsersException(e);
		}
		return "core_user_update_users";
	}
	
	@Override
	public Object  doCall() throws MoodleWSFucntionException {
		return super.doCall();
	}
	
	@Override
	protected Object processResponse(Document response) throws MoodleWSFucntionException {
		return null;
	}

	public void addUser(MoodleUser mdlUser) throws MoodleRestUpdateUsersException {
		verifyEntity(mdlUser);		
		this.mdlUsers.add(mdlUser);

	}

	public void setUsers(Set<MoodleUser> mdlUsers) throws MoodleRestUpdateUsersException {
		for (MoodleUser moodleUser : mdlUsers) {
			this.addUser(moodleUser);
		}

	}

	private void verifyEntity(MoodleUser entity) throws MoodleRestUpdateUsersException {
		if (entity == null) 
			throw new MoodleRestUpdateUsersException(MoodleCommonsErrorMessages.notSet("User"));
		
		if (MoodleTools.isEmpty(entity.getId()))
			throw new MoodleRestUpdateUsersException(MoodleCommonsErrorMessages.mustHave("User", "ID", entity));

	}

}
