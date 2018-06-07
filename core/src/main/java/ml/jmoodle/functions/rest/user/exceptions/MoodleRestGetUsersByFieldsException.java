package ml.jmoodle.functions.rest.user.exceptions;


import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.user.MoodleRestGetUsersByFields.Field;

public class MoodleRestGetUsersByFieldsException extends MoodleWSFucntionException {

	private static final long serialVersionUID = -1851500159808076764L;

	public MoodleRestGetUsersByFieldsException() {
		// TODO Auto-generated constructor stub
	}

	public MoodleRestGetUsersByFieldsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MoodleRestGetUsersByFieldsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MoodleRestGetUsersByFieldsException(Throwable cause) {
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
