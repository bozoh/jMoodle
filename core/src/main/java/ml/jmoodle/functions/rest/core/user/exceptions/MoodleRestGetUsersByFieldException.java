package ml.jmoodle.functions.rest.core.user.exceptions;


import ml.jmoodle.commons.Field;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;


public class MoodleRestGetUsersByFieldException extends MoodleWSFucntionException {

	private static final long serialVersionUID = -1851500159808076764L;

	public MoodleRestGetUsersByFieldException() {
		// TODO Auto-generated constructor stub
	}

	public MoodleRestGetUsersByFieldException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MoodleRestGetUsersByFieldException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MoodleRestGetUsersByFieldException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public static String fieldAlredySeted(Field setedField, Field newField) {
		StringBuilder msg = new StringBuilder();
		msg.append("You are trying to reste the field [").append(setedField.toString()).append("] to [")
				.append(newField.toString()).append("]");
		return msg.toString();
	}

}
