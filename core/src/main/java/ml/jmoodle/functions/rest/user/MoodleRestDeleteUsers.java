package ml.jmoodle.functions.rest.user;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.user.exceptions.MoodleRestDeleteUsersException;

import ml.jmoodle.functions.rest.user.tools.MoodleRestUserFunctionsTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleTools;

/**
 * Detete User(s) Function
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_user_delete_users", "moodle_user_delete_users" })
public class MoodleRestDeleteUsers extends MoodleWSBaseFunction {

	private Set<MoodleUser> users;
	private MoodleRestUserFunctionsTools usersTools;
	private static final String SINCE_VERSION = "2.0.0";

	public MoodleRestDeleteUsers(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		super(moodleConfig);
		this.users = new HashSet<MoodleUser>();
		this.usersTools = new MoodleRestUserFunctionsTools();
	}

	@Override
	public String getFunctionData() throws MoodleWSFucntionException {
		if (getUsers().isEmpty())
			throw new MoodleRestDeleteUsersException(MoodleCommonsErrorMessages.notSet("Users"));
		try {
			StringBuilder fnctData = new StringBuilder(super.getFunctionData());
			fnctData.append(getUserFuntionsTools().serliazeMoodleUsersIds(getUsers()));
			return fnctData.toString();
		} catch (UnsupportedEncodingException e) {
			throw new MoodleRestDeleteUsersException(e);
		}
	}

	@Override
	public String getSinceVersion() throws MoodleWSFucntionException {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleWSFucntionException {
		try {
			if ((MoodleTools.compareVersion(mdlConfig.getVersion(), "2.2.0") < 0))
				return "moodle_user_delete_users";
		} catch (MoodleConfigException e) {
			throw new MoodleRestDeleteUsersException(e);
		}
		return "core_user_delete_users";
	}
	
	
	@Override
	public Object  doCall() throws MoodleWSFucntionException {
		return super.doCall();
	}
	
	@Override
	protected Object processResponse(Document response) throws MoodleWSFucntionException {
		return null;
	}

	

	public void setUsers(Set<MoodleUser> users) throws MoodleRestDeleteUsersException {
		for (MoodleUser moodleUser : users) {
			addUser(moodleUser);
		}

	}

	public void addUser(MoodleUser user) throws MoodleRestDeleteUsersException {
		if (user == null || user.getId() == null || user.getId().longValue() <= 0l)
			throw new MoodleRestDeleteUsersException(MoodleCommonsErrorMessages.mustHave("User", "id", user));
		this.users.add(user);
	}

	public Set<MoodleUser> getUsers() {
		return this.users;
	}

	/**
	 * @return the usersTools
	 */
	private MoodleRestUserFunctionsTools getUserFuntionsTools() {
		return usersTools;
	}

	

}
