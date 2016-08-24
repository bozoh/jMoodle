package ml.jmoodle.functions.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
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
import ml.jmoodle.functions.MoodleWSFunctionCall;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleRestDeleteUsersException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.tools.MoodleRestUserFunctionsTools;
import ml.jmoodle.tools.MoodleTools;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MoodleTools.class, MoodleRestDeleteUsers.class, MoodleWSFunctionCall.class })
public class MoodleRestDeleteUsersTest implements MoodleRestFunctionsCommonsTest{
	URL mdlUrl;

	MoodleConfig configMck;
	String serializedUserIds = "userids%5B0%5D=86&userids%5B1%5D=458&userids%5B2%5D=871";

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
		PowerMockito.when(MoodleTools.encode(anyString())).thenCallRealMethod();
	}

	@Test
	public final void testIfGetTheRightClassUsingFactory() throws Exception {
		MoodleWSFunction function1 = MoodleWSFunctionFactory.getFunction(MoodleWSFunctions.CORE_USER_DELETE_USERS,
				configMck);
		MoodleWSFunction function2 = MoodleWSFunctionFactory.getFunction(MoodleWSFunctions.MOODLE_USER_DELETE_USERS,
				configMck);
		assertThat(function1, instanceOf(MoodleRestDeleteUsers.class));
		assertThat(function2, instanceOf(MoodleRestDeleteUsers.class));
	}

	@Test
	public final void testIfReturnTheRightAddedVersion() throws Exception {
		// This function id added in 2.0.0
		MoodleRestDeleteUsers createUser = new MoodleRestDeleteUsers(configMck);
		assertEquals("2.0.0", createUser.getSinceVersion());
	}

	@Test(expected = MoodleWSFucntionException.class)
	public final void testIfThrowExceptionIfWrongMoodleVersionInConstructor() throws MoodleConfigException, MoodleWSFucntionException
			 {
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenReturn(-1);
		when(configMck.getVersion()).thenReturn("1.4.0");
		new MoodleRestDeleteUsers(configMck);

	}
	
	@Test
	public final void testIfAddUserWithoutIdThrowsExecpiton() throws MoodleWSFucntionException, MoodleConfigException
			{
		MoodleUser user1 = Fixture.from(MoodleUser.class).gimme("MoodleRestDeleteUsersFunction");
		MoodleUser user2 = Fixture.from(MoodleUser.class).gimme("MoodleRestDeleteUsersFunction");

		user1.setId(null);
		user2.setId(0l);

		MoodleRestDeleteUsers function1 = (MoodleRestDeleteUsers) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_DELETE_USERS, configMck);

		try {
			function1.addUser(user1);
		} catch (MoodleRestDeleteUsersException e) {
			assertThat(e, instanceOf(MoodleRestDeleteUsersException.class));
		}

		try {
			function1.addUser(user2);
		} catch (MoodleRestDeleteUsersException e) {
			assertThat(e, instanceOf(MoodleRestDeleteUsersException.class));
		}
	}

	@Test(expected = MoodleRestDeleteUsersException.class)
	public final void testIfGetFunctionDataThrowExceptionWhenNoUsersIsSet()
			throws MoodleWSFucntionException, MoodleConfigException {

		MoodleRestDeleteUsers function1 = (MoodleRestDeleteUsers) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_DELETE_USERS, configMck);

		function1.getFunctionData();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public final void testIfGetFunctionDataCallMoodleRestUserFunctioTooleSerializeUsersIDs() throws Exception {

		Set<MoodleUser> mdlUsers = new LinkedHashSet(
				Fixture.from(MoodleUser.class).gimme(3, "MoodleRestDeleteUsersFunction"));
		MoodleRestUserFunctionsTools userFunctionsTools = mock(MoodleRestUserFunctionsTools.class);

		MoodleRestDeleteUsers function1 = PowerMockito.spy((MoodleRestDeleteUsers) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_DELETE_USERS, configMck));

		PowerMockito.doReturn(userFunctionsTools).when(function1, "getUserFuntionsTools");

		function1.setUsers(mdlUsers);
		function1.getFunctionData();

		verify(userFunctionsTools).serliazeUsersIds(mdlUsers);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public final void testIfGetTheRightFunctionStrByMoodleVersion() throws Exception {
		Set<MoodleUser> mdlUsers = new LinkedHashSet(
				Fixture.from(MoodleUser.class).gimme(3, "MoodleRestDeleteUsersFunction"));
		MoodleRestUserFunctionsTools userFunctionsTools = mock(MoodleRestUserFunctionsTools.class);
		when(userFunctionsTools.serliazeUsersIds(anySet())).thenReturn(serializedUserIds);

		MoodleRestDeleteUsers function1 = PowerMockito.spy((MoodleRestDeleteUsers) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_DELETE_USERS, configMck));
		// PowerMockito.doReturn(mdlUsers).when(function1, "getUsers");
		PowerMockito.doReturn(userFunctionsTools).when(function1, "getUserFuntionsTools");

		StringBuilder expectedStr = new StringBuilder();
		expectedStr.append(MoodleTools.encode("wsfunction")).append("=")
				.append(MoodleTools.encode(MoodleWSFunctions.CORE_USER_DELETE_USERS.toString().toLowerCase())).append("&")
				.append(serializedUserIds);
		function1.setUsers(mdlUsers);
		assertThat(function1.getFunctionData(), equalTo(expectedStr.toString()));

		expectedStr.delete(0, expectedStr.length());

		when(configMck.getVersion()).thenReturn("2.1.3");
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenCallRealMethod();
		MoodleRestDeleteUsers function2 = PowerMockito.spy((MoodleRestDeleteUsers) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_DELETE_USERS, configMck));
		// PowerMockito.doReturn(mdlUsers).when(function2, "getUsers");
		PowerMockito.doReturn(userFunctionsTools).when(function2, "getUserFuntionsTools");

		expectedStr.append(MoodleTools.encode("wsfunction")).append("=")
				.append(MoodleTools.encode(MoodleWSFunctions.MOODLE_USER_DELETE_USERS.toString().toLowerCase())).append("&")
				.append(serializedUserIds);
		function2.setUsers(mdlUsers);
		assertThat(function2.getFunctionData(), equalTo(expectedStr.toString()));
	}

	

	@Test
	public final void testIfGetTheRightFunctionNameByMoodleVersion() throws Exception {
		// "core_user_create_users", "moodle_user_create_users"
		// This function chages name in moodle 2.2

		MoodleRestDeleteUsers mdlfnc22 = (MoodleRestDeleteUsers) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_DELETE_USERS, configMck);
		String fnc22Name = mdlfnc22.getFunctionName();

		when(configMck.getVersion()).thenReturn("2.1.0");
		MoodleRestDeleteUsers mdlfnc20 = new MoodleRestDeleteUsers(configMck);
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenReturn(-1);
		String fnc20Name = mdlfnc20.getFunctionName();

		assertEquals("core_user_delete_users", fnc22Name);
		assertEquals("moodle_user_delete_users", fnc20Name);

	}

	@Test
	public final void testIfDoCallMethodCallsMoodleWSFunctionCallCallMethod() throws Exception {

		// Document userResponse = usersFixture.getRespone();
		MoodleWSFunctionCall wsFunctionCallMck = mock(MoodleWSFunctionCall.class);
		// when(wsFunctionCallMck.call(any(MoodleWSFunction.class))).thenReturn(null);
		PowerMockito.mockStatic(MoodleWSFunctionCall.class);
		PowerMockito.when(MoodleWSFunctionCall.getInstance(any(MoodleConfig.class))).thenReturn(wsFunctionCallMck);

		MoodleRestDeleteUsers mdlfnc = (MoodleRestDeleteUsers) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_DELETE_USERS, configMck);
		
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Set<MoodleUser> mdlUsers = new LinkedHashSet(
				Fixture.from(MoodleUser.class).gimme(3, "MoodleRestDeleteUsersFunction"));

		mdlfnc.setUsers(mdlUsers);
		try {
			mdlfnc.doCall();
		} catch (NullPointerException e) {
			// Null poiter is excpeted since wsFunctionCall is a mock
		}

		verify(wsFunctionCallMck).call(mdlfnc);
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final void testIfDoCallRetunrsNull() throws Exception {

		
		MoodleWSFunctionCall wsFunctionCallMck = mock(MoodleWSFunctionCall.class);
		when(wsFunctionCallMck.call(any(MoodleWSFunction.class))).thenReturn(null);
		PowerMockito.mockStatic(MoodleWSFunctionCall.class);
		PowerMockito.when(MoodleWSFunctionCall.getInstance(any(MoodleConfig.class))).thenReturn(wsFunctionCallMck);

		MoodleRestDeleteUsers mdlfnc = (MoodleRestDeleteUsers) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_DELETE_USERS, configMck);

		Set<MoodleUser> usersToAdd = new LinkedHashSet(
				Fixture.from(MoodleUser.class).gimme(3, "MoodleRestDeleteUsersFunction"));
	
		
		assertThat(usersToAdd.size(), equalTo(3));
		

		mdlfnc.setUsers(usersToAdd);
		assertThat(mdlfnc.doCall(), nullValue());
		

	}
	
}
