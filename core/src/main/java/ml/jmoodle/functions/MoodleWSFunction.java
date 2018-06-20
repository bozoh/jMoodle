package ml.jmoodle.functions;

import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;

public interface MoodleWSFunction {

	public String getFunctionData() throws MoodleWSFucntionException;

	public String getSinceVersion() throws MoodleWSFucntionException;

	public String getFunctionName() throws MoodleWSFucntionException;

	public Object doCall() throws MoodleWSFucntionException;
	
	

}
