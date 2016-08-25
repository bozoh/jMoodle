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

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSFunction;
import ml.jmoodle.functions.MoodleWSFunctionCall;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleRestGetUsersByFieldsException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.MoodleRestGetUsersByFields.Field;
import ml.jmoodle.functions.rest.fixtures.UsersFixture;
import ml.jmoodle.functions.rest.tools.MoodleRestUserFunctionsTools;
import ml.jmoodle.tools.MoodleTools;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MoodleTools.class, MoodleRestGetUsersByFields.class, MoodleWSFunctionCall.class })
public class MoodleRestGetUsersByFieldsTest implements MoodleRestFunctionsCommonsTest {
	URL mdlUrl;

	MoodleConfig configMck;
	String serializedFields = "field=id&values%5B0%5D=3936&values%5B1%5D=326&values%5B2%5D=4373";

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

		MoodleWSFunction function = MoodleWSFunctionFactory.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_FIELDS,
				configMck);

		assertThat(function, instanceOf(MoodleRestGetUsersByFields.class));

	}

	@Test
	public final void testIfReturnTheRightAddedVersion() throws Exception {
		// This function id added in 2.5.0
		MoodleRestGetUsersByFields createUser = new MoodleRestGetUsersByFields(configMck);
		assertEquals("2.5.0", createUser.getSinceVersion());
	}

	@Test(expected = MoodleWSFucntionException.class)
	public final void testIfThrowExceptionIfWrongMoodleVersionInConstructor()
			throws MoodleConfigException, MoodleWSFucntionException {
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenReturn(-1);
		when(configMck.getVersion()).thenReturn("1.4.0");
		new MoodleRestGetUsersByFields(configMck);

	}

	@Test
	public final void testIfAddFieldWithoutValueThrowsExecpiton() throws Exception {

		MoodleRestGetUsersByFields function1 = (MoodleRestGetUsersByFields) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_FIELDS, configMck);

		function1.setField(MoodleRestGetUsersByFields.Field.ID);
		try {
			function1.addValue("");
		} catch (MoodleRestGetUsersByFieldsException e) {
			assertThat(e, instanceOf(MoodleRestGetUsersByFieldsException.class));
		}

		try {
			function1.addValue(null);
		} catch (MoodleRestGetUsersByFieldsException e) {
			assertThat(e, instanceOf(MoodleRestGetUsersByFieldsException.class));
		}
	}

	@Test(expected = MoodleRestGetUsersByFieldsException.class)
	public final void testIfAddTwoDifferentFieldsThrowsExecpiton() throws Exception {

		MoodleRestGetUsersByFields function1 = (MoodleRestGetUsersByFields) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_FIELDS, configMck);

		function1.setField(MoodleRestGetUsersByFields.Field.ID);
		function1.setField(MoodleRestGetUsersByFields.Field.EMAIL);

	}

	@Test(expected = MoodleRestGetUsersByFieldsException.class)
	public final void testIfGetFunctionDataThrowExceptionWhenNoFieldsIsSet()
			throws MoodleWSFucntionException, MoodleConfigException {

		MoodleRestGetUsersByFields function1 = (MoodleRestGetUsersByFields) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_FIELDS, configMck);

		function1.getFunctionData();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public final void testIfGetFunctionDataCallMoodleRestUserFunctioTooleSerializeFields() throws Exception {

		UsersFixture usersFixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersByFieldsResponse");

		Set<String> values = usersFixture.getFieldValues();
		MoodleRestUserFunctionsTools userFunctionsTools = mock(MoodleRestUserFunctionsTools.class);

		MoodleRestGetUsersByFields function1 = PowerMockito.spy((MoodleRestGetUsersByFields) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_FIELDS, configMck));

		PowerMockito.doReturn(userFunctionsTools).when(function1, "getUserFuntionsTools");

		MoodleRestGetUsersByFields.Field field = MoodleRestGetUsersByFields.Field.ID;
		function1.setField(field);
		function1.setValues(values);
		function1.getFunctionData();

		verify(userFunctionsTools).serliazeFields(field, values);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public final void testIfGetTheRightFunctionStrByMoodleVersion() throws Exception {
		UsersFixture usersFixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersByFieldsResponse");

		Set<String> values = usersFixture.getFieldValues();
		MoodleRestUserFunctionsTools userFunctionsTools = mock(MoodleRestUserFunctionsTools.class);
		when(userFunctionsTools.serliazeFields(any(Field.class), anySet())).thenReturn(serializedFields);

		MoodleRestGetUsersByFields function1 = PowerMockito.spy((MoodleRestGetUsersByFields) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_FIELDS, configMck));
		// PowerMockito.doReturn(mdlUsers).when(function1, "getUsers");
		PowerMockito.doReturn(userFunctionsTools).when(function1, "getUserFuntionsTools");

		StringBuilder expectedStr = new StringBuilder();
		expectedStr.append(MoodleTools.encode("wsfunction")).append("=")
				.append(MoodleTools.encode("CORE_USER_GET_USERS_BY_FIELDS".toLowerCase())).append("&")
				.append(serializedFields);
		function1.setField(Field.ID);
		function1.setValues(values);
		assertThat(function1.getFunctionData(), equalTo(expectedStr.toString()));

	}

	@Test
	public final void testIfGetTheRightFunctionNameByMoodleVersion() throws Exception {
		// "core_user_create_users", "moodle_user_create_users"
		// This function chages name in moodle 2.2

		MoodleRestGetUsersByFields mdlfnc = (MoodleRestGetUsersByFields) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_FIELDS, configMck);
		String fncName = mdlfnc.getFunctionName();

		assertEquals("CORE_USER_GET_USERS_BY_FIELDS".toLowerCase(), fncName);
	}

	@Test
	public final void testIfDoCallMethodCallsMoodleWSFunctionCallCallMethod() throws Exception {

		// Document userResponse = usersFixture.getRespone();
		MoodleWSFunctionCall wsFunctionCallMck = mock(MoodleWSFunctionCall.class);
		// when(wsFunctionCallMck.call(any(MoodleWSFunction.class))).thenReturn(null);
		PowerMockito.mockStatic(MoodleWSFunctionCall.class);
		PowerMockito.when(MoodleWSFunctionCall.getInstance(any(MoodleConfig.class))).thenReturn(wsFunctionCallMck);

		MoodleRestGetUsersByFields mdlfnc = (MoodleRestGetUsersByFields) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_FIELDS, configMck);

		UsersFixture fixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersByFieldsResponse");

		Set<String> values = fixture.getFieldValues();

		mdlfnc.setField(Field.ID);
		mdlfnc.setValues(values);

		mdlfnc.doCall();

		verify(wsFunctionCallMck).call(mdlfnc);
	}

	@Test
	public final void testIfDoCallRetunrsUsersSet() throws Exception {

		UsersFixture usersFixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersByFieldsResponse");
		Document userResponse = usersFixture.getGetUsersByIdRespone();
		MoodleWSFunctionCall wsFunctionCallMck = mock(MoodleWSFunctionCall.class);
		when(wsFunctionCallMck.call(any(MoodleWSFunction.class))).thenReturn(userResponse);
		PowerMockito.mockStatic(MoodleWSFunctionCall.class);
		PowerMockito.when(MoodleWSFunctionCall.getInstance(any(MoodleConfig.class))).thenReturn(wsFunctionCallMck);

		MoodleRestGetUsersByFields mdlfnc = (MoodleRestGetUsersByFields) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS_BY_FIELDS, configMck);

		Set<MoodleUser> expetedUserResponse = usersFixture.getMdlUsers();

		Set<String> values = usersFixture.getFieldValues();

		mdlfnc.setField(Field.ID);
		mdlfnc.setValues(values);

		Set<MoodleUser> response = mdlfnc.doCall();
		assertThat(response, notNullValue());
		assertThat(response.size(), equalTo(3));
		for (MoodleUser moodleUser : response) {
			assertThat(moodleUser, instanceOf(MoodleUser.class));
			assertThat(expetedUserResponse, hasItem(moodleUser));
		}

	}

}
