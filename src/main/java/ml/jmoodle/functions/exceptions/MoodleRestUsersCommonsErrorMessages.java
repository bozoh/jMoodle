package ml.jmoodle.functions.exceptions;

import ml.jmoodle.commons.MoodleUser;

public class MoodleRestUsersCommonsErrorMessages {

	public MoodleRestUsersCommonsErrorMessages() {

	}

	public static final String NO_USER_IS_SET_ERROR = "No users is set";

	public static final String mustHave(String name, MoodleUser mdlUser) {
		StringBuilder msg = new StringBuilder();
		msg.append("User MUST HAVE a");
		if (name.toLowerCase().charAt(0) == 'a' || name.toLowerCase().charAt(0) == 'e'
				|| name.toLowerCase().charAt(0) == 'i' || name.toLowerCase().charAt(0) == 'o'
				|| name.toLowerCase().charAt(0) == 'u') {
			msg.append("n");
		}
		msg.append(" ").append(name).append(":\n").append(mdlUser.toString());

		return msg.toString();
	}

}
