package ml.jmoodle.functions.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import ml.jmoodle.functions.exceptions.MoodleRestGetUsersByFieldsException;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSFunction;
import ml.jmoodle.functions.MoodleWSFunctionCall;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleRestGetUsersByIdException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.fixtures.UsersFixture;
import ml.jmoodle.functions.rest.tools.MoodleRestUserFunctionsTools;
import ml.jmoodle.tools.MoodleTools;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MoodleTools.class, MoodleRestGetUsersById.class, MoodleWSFunctionCall.class })
public class MoodleRestGetUsersByIdTest implements MoodleRestFunctionsCommonsTest {
	URL mdlUrl;

	MoodleConfig configMck;
	String serializedIds = "field=id&values%5B0%5D=3936&values%5B1%5D=326&values%5B2%5D=4373";

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

		MoodleWSFunction function = MoodleWSFunctionFactory.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_ID,
				configMck);

		assertThat(function, instanceOf(MoodleRestGetUsersById.class));

	}

	@Test
	public final void testIfReturnTheRightAddedVersion() throws Exception {
		// This function id added in 2.5.0
		MoodleRestGetUsersById createUser = new MoodleRestGetUsersById(configMck);
		assertEquals("2.0.0", createUser.getSinceVersion());
	}

	@Test(expected = MoodleWSFucntionException.class)
	public final void testIfThrowExceptionIfWrongMoodleVersionInConstructor()
			throws MoodleConfigException, MoodleWSFucntionException {
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenReturn(-1);
		when(configMck.getVersion()).thenReturn("1.4.0");
		new MoodleRestGetUsersById(configMck);

	}

	@Test
	public final void testIfAddFieldWithoutValueThrowsExecpiton() throws Exception {

		MoodleRestGetUsersById function1 = (MoodleRestGetUsersById) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_ID, configMck);

		try {
			function1.addId(0l);
		} catch (MoodleRestGetUsersByIdException e) {
			assertThat(e, instanceOf(MoodleRestGetUsersByIdException.class));
		}

		try {
			function1.addId(null);
		} catch (MoodleRestGetUsersByIdException e) {
			assertThat(e, instanceOf(MoodleRestGetUsersByIdException.class));
		}
	}

	@Test(expected = MoodleRestGetUsersByIdException.class)
	public final void testIfGetFunctionDataThrowExceptionWhenNoIdIsSet()
			throws MoodleWSFucntionException, MoodleConfigException {

		MoodleRestGetUsersById function1 = (MoodleRestGetUsersById) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_ID, configMck);

		function1.getFunctionData();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public final void testIfGetFunctionDataCallMoodleRestUserFunctioTooleSerializeUsersIds() throws Exception {

		UsersFixture usersFixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersByIdResponse");

		Set<Long> ids = usersFixture.getIdsValues();
		MoodleRestUserFunctionsTools userFunctionsTools = mock(MoodleRestUserFunctionsTools.class);

		MoodleRestGetUsersById function1 = PowerMockito.spy((MoodleRestGetUsersById) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_ID, configMck));

		PowerMockito.doReturn(userFunctionsTools).when(function1, "getUserFuntionsTools");

		function1.setIds(ids);

		function1.getFunctionData();

		verify(userFunctionsTools).serliazeUsersIds(ids);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public final void testIfGetTheRightFunctionStrByMoodleVersion() throws Exception {
		UsersFixture usersFixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersByIdResponse");

		MoodleRestUserFunctionsTools userFunctionsTools = mock(MoodleRestUserFunctionsTools.class);
		when(userFunctionsTools.serliazeUsersIds(anySet())).thenReturn(serializedIds);

		MoodleRestGetUsersById function1 = PowerMockito.spy((MoodleRestGetUsersById) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_ID, configMck));
		// PowerMockito.doReturn(mdlUsers).when(function1, "getUsers");
		PowerMockito.doReturn(userFunctionsTools).when(function1, "getUserFuntionsTools");

		StringBuilder expectedStr = new StringBuilder();
		expectedStr.append(MoodleTools.encode("wsfunction")).append("=")
				.append(MoodleTools.encode("CORE_USER_GET_USERS_BY_ID".toLowerCase())).append("&")
				.append(serializedIds);

		function1.setIds(usersFixture.getIdsValues());
		assertThat(function1.getFunctionData(), equalTo(expectedStr.toString()));

		when(configMck.getVersion()).thenReturn("2.1.0");
		// Clear Buffer
		expectedStr.delete(0, expectedStr.length());
		expectedStr.append(MoodleTools.encode("wsfunction")).append("=")
				.append(MoodleTools.encode("MOODLE_USER_GET_USERS_BY_ID".toLowerCase())).append("&")
				.append(serializedIds);
		
		assertThat(function1.getFunctionData(), equalTo(expectedStr.toString()));
	}

	@Test
	public final void testIfGetTheRightFunctionNameByMoodleVersion() throws Exception {
		// "core_user_create_users", "moodle_user_create_users"
		// This function chages name in moodle 2.2

		MoodleRestGetUsersById mdlfnc = (MoodleRestGetUsersById) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_ID, configMck);
		String fncName = mdlfnc.getFunctionName();
		// 2.2.0 and above
		assertEquals("CORE_USER_GET_USERS_BY_ID".toLowerCase(), fncName);
		
		
		when(configMck.getVersion()).thenReturn("2.1.0");
		// 2.0.x and 2.1.x
		assertEquals("MOODLE_USER_GET_USERS_BY_ID".toLowerCase(), mdlfnc.getFunctionName());
	}

	@Test
	public final void testIfDoCallMethodCallsMoodleWSFunctionCallCallMethod() throws Exception {

		// Document userResponse = usersFixture.getRespone();
		MoodleWSFunctionCall wsFunctionCallMck = mock(MoodleWSFunctionCall.class);
		// when(wsFunctionCallMck.call(any(MoodleWSFunction.class))).thenReturn(null);
		PowerMockito.mockStatic(MoodleWSFunctionCall.class);
		PowerMockito.when(MoodleWSFunctionCall.getInstance(any(MoodleConfig.class))).thenReturn(wsFunctionCallMck);

		MoodleRestGetUsersById mdlfnc = (MoodleRestGetUsersById) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_ID, configMck);

		UsersFixture fixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersByIdResponse");

		mdlfnc.setIds(fixture.getIdsValues());
		mdlfnc.doCall();

		verify(wsFunctionCallMck).call(mdlfnc);
	}

	@Test
	public final void testIfDoCallCallUnserializeUsers() throws Exception {

		UsersFixture usersFixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersByIdResponse");
		Document userResponse = usersFixture.getGetUsersByIdRespone();
		MoodleWSFunctionCall wsFunctionCallMck = mock(MoodleWSFunctionCall.class);
		when(wsFunctionCallMck.call(any(MoodleWSFunction.class))).thenReturn(userResponse);
		PowerMockito.mockStatic(MoodleWSFunctionCall.class);
		PowerMockito.when(MoodleWSFunctionCall.getInstance(any(MoodleConfig.class))).thenReturn(wsFunctionCallMck);

		MoodleRestGetUsersById mdlfnc = (MoodleRestGetUsersById) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_ID, configMck);

		Set<MoodleUser> expetedUserResponse = usersFixture.getMdlUsers();
		mdlfnc.setIds(usersFixture.getIdsValues());

		Set<MoodleUser> response = mdlfnc.doCall();
		assertThat(response, notNullValue());
		assertThat(response.size(), equalTo(3));
		for (MoodleUser moodleUser : response) {
			assertThat(moodleUser, instanceOf(MoodleUser.class));
			assertThat(expetedUserResponse, hasItem(moodleUser));
		}

	}

}
