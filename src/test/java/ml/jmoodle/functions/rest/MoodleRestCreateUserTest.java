package ml.jmoodle.functions.rest;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSFunction;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.tools.MoodleTools;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MoodleTools.class })
public class MoodleRestCreateUserTest {
	URL mdlUrl;

	MoodleConfig configMck;

	@Before()
	public final void setUp() throws Exception {
		this.mdlUrl = new URL("http://test.mdl");
		// PowerMockito.whenNew(MoodleConfig.class).withAnyArguments();
		configMck = mock(MoodleConfig.class);
		when(configMck.getMoodleURL()).thenReturn(mdlUrl);
		when(configMck.getVersion()).thenReturn("2.2.0");

		PowerMockito.mockStatic(MoodleTools.class);
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenReturn(0);
	}

	@Test
	public final void testGetTheRightClassUsingFactoryMethod() throws Exception {
		MoodleWSFunction function1 = MoodleWSFunction.getFunction(MoodleWSFunctions.CORE_USER_CREATE_USERS,
				configMck.getVersion());
		MoodleWSFunction function2 = MoodleWSFunction.getFunction(MoodleWSFunctions.MOODLE_USER_CREATE_USERS,
				configMck.getVersion());
		assert (function1 instanceof MoodleRestCreateUser);
		assert (function2 instanceof MoodleRestCreateUser);
	}

	@Test
	public final void testIfReturnTheRightAddedVersion() throws Exception {
		// This function id added in 2.0.0
		MoodleRestCreateUser createUser = new MoodleRestCreateUser(configMck.getVersion());
		assertEquals("2.0.0", createUser.getSinceVersion());
	}

	@Test(expected = MoodleWSFucntionException.class)
	public final void testIfThrowExceptionIfWrongMoodleVersionInConstructor()
			throws MoodleWSFucntionException, MoodleConfigException {
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenReturn(-1);
		new MoodleRestCreateUser("1.4.0");

	}

	@Test(expected = MoodleWSFucntionException.class)
	public final void testIfThrowExceptionIfWrongMoodleVersionInFactory()
			throws MoodleWSFucntionException, MoodleConfigException {
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenReturn(-1);
		MoodleWSFunction.getFunction(MoodleWSFunctions.CORE_USER_CREATE_USERS, configMck.getVersion());

	}

	@Test
	public final void testIfGetTheRightFunctionStrByMoodleVersion() throws Exception {
		fail("not implemented");
	}

	@Test
	public final void testIfGetTheRightFunctionNameByMoodleVersion() throws Exception {
		// "core_user_create_users", "moodle_user_create_users"
		// This function chages name in moodle 2.2
		MoodleRestCreateUser mdlfnc22 = (MoodleRestCreateUser) MoodleWSFunction
				.getFunction(MoodleWSFunctions.CORE_USER_CREATE_USERS, configMck.getVersion());
		MoodleRestCreateUser mdlfnc20 = new MoodleRestCreateUser("2.1.0");
		String fnc22Name = mdlfnc22.getFunctionName();
		String fnc20Name = mdlfnc20.getFunctionName();

		assertEquals("core_user_create_users", fnc22Name);
		assertEquals("moodle_user_create_users", fnc20Name);

	}
}
