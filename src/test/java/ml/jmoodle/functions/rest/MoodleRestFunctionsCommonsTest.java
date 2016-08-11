package ml.jmoodle.functions.rest;

import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;

public interface MoodleRestFunctionsCommonsTest {
			
		public void testIfGetTheRightClassUsingFactory() throws Exception;
	
		public void testIfReturnTheRightAddedVersion() throws Exception;
		public void testIfThrowExceptionIfWrongMoodleVersionInConstructor() throws MoodleConfigException, MoodleWSFucntionException;
		public void testIfGetTheRightFunctionStrByMoodleVersion() throws Exception;
		public void testIfGetTheRightFunctionNameByMoodleVersion() throws Exception;
		public void testIfDoCallMethodCallsMoodleWSFunctionCallCallMethod() throws Exception;
}
