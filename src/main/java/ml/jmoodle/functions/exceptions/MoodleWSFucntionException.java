package ml.jmoodle.functions.exceptions;

public class MoodleWSFucntionException extends Exception {

	private static final long serialVersionUID = -7206417792865186607L;

	public MoodleWSFucntionException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MoodleWSFucntionException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MoodleWSFucntionException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MoodleWSFucntionException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public static String errorProcessingResponseMsg(String responseStr) {
		
		return "Erro Processing Response:\n"+responseStr;
	}
	
	
	

}
