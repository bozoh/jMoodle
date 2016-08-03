package ml.jmoodle.functions;

import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;

public interface MoodleWSFunction {

	public String getFunctionStr() throws MoodleWSFucntionException;

	public String getSinceVersion() throws MoodleWSFucntionException;

	public String getFunctionName() throws MoodleWSFucntionException;

	public Object processResponse(String response) throws MoodleWSFucntionException;

}
