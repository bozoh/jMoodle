package ml.jmoodle.tools;

public class MoodleCommonsErrorMessages {

	public MoodleCommonsErrorMessages() {

	}

	public static final String notSet(String name) {
		StringBuilder msg = new StringBuilder();
		msg.append("No ").append(name).append(" is set");
		return msg.toString();
	}
	
	public static final String mustHave(String entity, String name, Object obj) {
		StringBuilder msg = new StringBuilder();
		msg.append(entity)
			.append(" MUST HAVE a");
		if (name.toLowerCase().charAt(0) == 'a' || name.toLowerCase().charAt(0) == 'e'
				|| name.toLowerCase().charAt(0) == 'i' || name.toLowerCase().charAt(0) == 'o'
				|| name.toLowerCase().charAt(0) == 'u') {
			msg.append("n");
		}
		msg.append(" ").append(name).append(":\n").append(obj.toString());

		return msg.toString();
	}

}
