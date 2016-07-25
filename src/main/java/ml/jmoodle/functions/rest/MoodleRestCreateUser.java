package ml.jmoodle.functions.rest;

import java.util.LinkedHashSet;
import java.util.Set;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.tools.MoodleRestUserFunctionsTools;

@MoodleWSFunction(names = { "core_user_create_users", "moodle_user_create_users" })
public class MoodleRestCreateUser extends ml.jmoodle.functions.MoodleWSFunction {
	private MoodleRestUserFunctionsTools userFuntionsTools  = new MoodleRestUserFunctionsTools();
	
	public MoodleRestCreateUser(String moodleVersion) throws MoodleWSFucntionException, MoodleConfigException {
		super(moodleVersion);
	}

	private String addedVersion = "2.0.0";
	private Set<MoodleUser> users = new LinkedHashSet<MoodleUser>();

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
	 * @param users the users to set
	 */
	public void setUsers(Set<MoodleUser> users) {
		this.users = users;
	}
	
	public void addUser(MoodleUser user) {
		this.users.add(user);
	}

	@Override
	public String getAddedVersion() {
		return addedVersion;
	}

	@Override
	public String getFunctionName() {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
