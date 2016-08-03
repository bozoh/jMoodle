package ml.jmoodle.functions.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSFunction;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleRestCreateUserException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.tools.MoodleRestUserFunctionsTools;
import ml.jmoodle.tools.MoodleTools;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MoodleTools.class, MoodleRestCreateUser.class })
public class MoodleRestCreateUserTest {
	URL mdlUrl;

	MoodleConfig configMck;
	String serializedUser="users%5B0%5D%5Bid%5D=355&users%5B0%5D%5Busername%5D=Rona-Auer&users%5B0%5D%5Bpassword%5D=awe2&users%5B0%5D%5Bfirstname%5D=Rona&users%5B0%5D%5Blastname%5D=Auer&users%5B0%5D%5Bemail%5D=Rona-Auer%40email.test&users%5B0%5D%5Bauth%5D=manual&users%5B0%5D%5Bidnumber%5D=024886573360022&users%5B0%5D%5Blang%5D=en_us&users%5B0%5D%5Bcalendartype%5D=gregorian&users%5B0%5D%5Btheme%5D=aasas&users%5B0%5D%5Btimezone%5D=Sao_Paulo&users%5B0%5D%5Bmailformat%5D=0&users%5B0%5D%5Bdescription%5D=foo+bar&users%5B0%5D%5Bcity%5D=Neil+Koch&users%5B0%5D%5Bcountry%5D=%24lang&users%5B0%5D%5Bfirstnamephonetic%5D=%2Fqwwsss%2F&users%5B0%5D%5Blastnamephonetic%5D=%2Fqwwsss%2F&users%5B0%5D%5Bmiddlename%5D=Yost&users%5B0%5D%5Balternatename%5D=Alva+Bins&users%5B0%5D%5Bcustomfields%5D%5B0%5D%5Btype%5D=brithday&users%5B0%5D%5Bcustomfields%5D%5B0%5D%5Bvalue%5D=533122112&users%5B0%5D%5Bcustomfields%5D%5B1%5D%5Btype%5D=anivers%C3%A1rio&users%5B0%5D%5Bcustomfields%5D%5B1%5D%5Bvalue%5D=100100010&users%5B0%5D%5Bpreferences%5D%5B0%5D%5Btype%5D=maildigest&users%5B0%5D%5Bpreferences%5D%5B0%5D%5Bvalue%5D=1&users%5B0%5D%5Bpreferences%5D%5B1%5D%5Btype%5D=editorformat&users%5B0%5D%5Bpreferences%5D%5B1%5D%5Bvalue%5D=4";

	@BeforeClass
	public static void setUpClass() {
		FixtureFactoryLoader.loadTemplates("ml.jmoodle.functions.rest.fixtures");
	}

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
		MoodleWSFunction function1 = MoodleWSFunctionFactory.getFunction(MoodleWSFunctions.CORE_USER_CREATE_USERS,
				configMck.getVersion());
		MoodleWSFunction function2 = MoodleWSFunctionFactory.getFunction(MoodleWSFunctions.MOODLE_USER_CREATE_USERS,
				configMck.getVersion());
		assertThat(function1, instanceOf(MoodleRestCreateUser.class));
		assertThat(function2, instanceOf(MoodleRestCreateUser.class));
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

	@Test(expected = MoodleRestCreateUserException.class)
	public final void testIfGetFunctionStrThrowExceptionWhenNoUsersIsSet()
			throws MoodleWSFucntionException, MoodleConfigException {

		MoodleRestCreateUser function1 = (MoodleRestCreateUser) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_CREATE_USERS, configMck.getVersion());

		function1.getFunctionStr();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public final void testIfGetFunctionStrCallMoodleRestUserFunctioTooleSerializeUsers() throws Exception {

		Set<MoodleUser> mdlUsers = new LinkedHashSet(
				Fixture.from(MoodleUser.class).gimme(3, "MoodleRestUserFunctionsToolsTestUser1"));
		MoodleRestUserFunctionsTools userFunctionsTools = mock(MoodleRestUserFunctionsTools.class);

		MoodleRestCreateUser function1 = PowerMockito.spy((MoodleRestCreateUser) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_CREATE_USERS, configMck.getVersion()));

		PowerMockito.doReturn(userFunctionsTools).when(function1, "getUserFuntionsTools");

		function1.setUsers(mdlUsers);
		function1.getFunctionStr();

		verify(userFunctionsTools).serliazeUsers(mdlUsers);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public final void testIfGetTheRightFunctionStrByMoodleVersion() throws Exception {
		Set<MoodleUser> mdlUsers = new LinkedHashSet(
				Fixture.from(MoodleUser.class).gimme(3, "MoodleRestUserFunctionsToolsTestUser1"));
		MoodleRestUserFunctionsTools userFunctionsTools = mock(MoodleRestUserFunctionsTools.class);
		when(userFunctionsTools.serliazeUsers(anySet())).thenReturn(serializedUser);
				

		MoodleRestCreateUser function1 = PowerMockito.spy((MoodleRestCreateUser) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_CREATE_USERS, configMck.getVersion()));
		// PowerMockito.doReturn(mdlUsers).when(function1, "getUsers");
		PowerMockito.doReturn(userFunctionsTools).when(function1, "getUserFuntionsTools");

		StringBuilder expectedStr = new StringBuilder();
		expectedStr.append(MoodleTools.encode("wsfunction")).append("=")
				.append(MoodleTools.encode(MoodleWSFunctions.CORE_USER_CREATE_USERS.getValue())).append("&")
				.append(serializedUser);
		function1.setUsers(mdlUsers);
		assertThat(function1.getFunctionStr(), equalTo(expectedStr.toString()));

		expectedStr.delete(0, expectedStr.length());

		MoodleRestCreateUser function2 = PowerMockito.spy((MoodleRestCreateUser) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_CREATE_USERS, "2.1.3"));
		// PowerMockito.doReturn(mdlUsers).when(function2, "getUsers");
		PowerMockito.doReturn(userFunctionsTools).when(function2, "getUserFuntionsTools");

		expectedStr.append(MoodleTools.encode("wsfunction")).append("=")
				.append(MoodleTools.encode(MoodleWSFunctions.MOODLE_USER_CREATE_USERS.getValue())).append("&")
				.append(serializedUser);
		function2.setUsers(mdlUsers);
		assertThat(function2.getFunctionStr(), equalTo(expectedStr.toString()));
	}

	@Test
	public final void testIfGetTheRightFunctionNameByMoodleVersion() throws Exception {
		// "core_user_create_users", "moodle_user_create_users"
		// This function chages name in moodle 2.2

		MoodleRestCreateUser mdlfnc22 = (MoodleRestCreateUser) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_CREATE_USERS, configMck.getVersion());
		String fnc22Name = mdlfnc22.getFunctionName();

		MoodleRestCreateUser mdlfnc20 = new MoodleRestCreateUser("2.1.0");
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenReturn(-1);
		String fnc20Name = mdlfnc20.getFunctionName();

		assertEquals("core_user_create_users", fnc22Name);
		assertEquals("moodle_user_create_users", fnc20Name);

	}
	
	@Test
	public final void testIfProcessResponseRetunrsMoodleUserColletion() throws Exception {
		fail("Not Yet Implemented");
	}
}
