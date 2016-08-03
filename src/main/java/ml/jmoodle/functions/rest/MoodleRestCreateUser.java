package ml.jmoodle.functions.rest;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashSet;
import java.util.Set;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleRestCreateUserException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.tools.MoodleRestUserFunctionsTools;
import ml.jmoodle.tools.MoodleTools;

@MoodleWSFunction(names = { "core_user_create_users", "moodle_user_create_users" })
public class MoodleRestCreateUser extends MoodleWSBaseFunction {
	private static final String SINCE_VERSION = "2.0.0";
	private MoodleRestUserFunctionsTools userFuntionsTools;
	private Set<MoodleUser> users;

	public MoodleRestCreateUser(String moodleVersion) throws MoodleWSFucntionException, MoodleConfigException {
		super(moodleVersion);
		this.users = new LinkedHashSet<MoodleUser>();
		this.userFuntionsTools = new MoodleRestUserFunctionsTools();
	}

	@Override
	public String getFunctionStr() throws MoodleRestCreateUserException {
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
			if ((MoodleTools.compareVersion(mdlVersion, "2.2.0") < 0))
				return "moodle_user_create_users";
		} catch (MoodleConfigException e) {
			throw new MoodleRestCreateUserException(e);
		}
		return "core_user_create_users";
	}

	
	/**
	 * Process Create User Response
	 * 
	 * @param response Moodle Server Respone in REST Format
	 * @return A set of MoodleUser
	 */
	@Override
	public Object processResponse(String response) throws MoodleRestCreateUserException {
		//TODO
		return null;
	}

}
