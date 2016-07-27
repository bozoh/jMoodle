package ml.jmoodle.functions.rest;

import java.util.LinkedHashSet;
import java.util.Set;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.tools.MoodleRestUserFunctionsTools;
import ml.jmoodle.tools.MoodleTools;

@MoodleWSFunction(names = { "core_user_create_users", "moodle_user_create_users" })
public class MoodleRestCreateUser extends ml.jmoodle.functions.MoodleWSFunction {
	private MoodleRestUserFunctionsTools userFuntionsTools = new MoodleRestUserFunctionsTools();
	private boolean isLegacy;
	private static final String SINCE_VERSION = "2.0.0";
	private Set<MoodleUser> users = new LinkedHashSet<MoodleUser>();

	public MoodleRestCreateUser(String moodleVersion) throws MoodleWSFucntionException, MoodleConfigException {
		super(moodleVersion);
		isLegacy=(MoodleTools.compareVersion(moodleVersion, "2.2.0") < 0);
	}

	

	@Override
	public String getFunctionStr() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the users
	 */
	public Set<MoodleUser> getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(Set<MoodleUser> users) {
		this.users = users;
	}

	public void addUser(MoodleUser user) {
		this.users.add(user);
	}

	@Override
	public String getSinceVersion() {
		return SINCE_VERSION;
	}

	/**
	 * Get the right function name, this funtcions changes the name in 2.2.0
	 * 
	 * @throws MoodleConfigException
	 */
	@Override
	public String getFunctionName() throws MoodleConfigException {
		// this funtcions changes the name in 2.2.0 
		if (isLegacy)
			return "moodle_user_create_users";
		return "core_user_create_users";
	}

}
