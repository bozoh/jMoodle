package ml.jmoodle.functions.rest.core.user.exceptions;

import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;

/**
 * Create User(s) Function
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
public class MoodleRestUpdateUsersException extends MoodleWSFucntionException {


	private static final long serialVersionUID = -2659275638403326547L;

	public MoodleRestUpdateUsersException() {
		// TODO Auto-generated constructor stub
	}

	public MoodleRestUpdateUsersException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MoodleRestUpdateUsersException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MoodleRestUpdateUsersException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
