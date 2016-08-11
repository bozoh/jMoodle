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
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import ml.jmoodle.functions.exceptions.MoodleRestUpdateUserException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.fixtures.UsersFixture;
import ml.jmoodle.functions.rest.tools.MoodleRestUserFunctionsTools;
import ml.jmoodle.tools.MoodleTools;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MoodleTools.class, MoodleRestUpdateUser.class, MoodleWSFunctionCall.class })
public class MoodleRestUpdateUserTest implements MoodleRestFunctionsCommonsTest{
	URL mdlUrl;

	MoodleConfig configMck;
	String serializedUser = "users%5B0%5D%5Bid%5D=355&users%5B0%5D%5Busername%5D=Rona-Auer&users%5B0%5D%5Bpassword%5D=awe2&users%5B0%5D%5Bfirstname%5D=Rona&users%5B0%5D%5Blastname%5D=Auer&users%5B0%5D%5Bemail%5D=Rona-Auer%40email.test&users%5B0%5D%5Bauth%5D=manual&users%5B0%5D%5Bidnumber%5D=024886573360022&users%5B0%5D%5Blang%5D=en_us&users%5B0%5D%5Bcalendartype%5D=gregorian&users%5B0%5D%5Btheme%5D=aasas&users%5B0%5D%5Btimezone%5D=Sao_Paulo&users%5B0%5D%5Bmailformat%5D=0&users%5B0%5D%5Bdescription%5D=foo+bar&users%5B0%5D%5Bcity%5D=Neil+Koch&users%5B0%5D%5Bcountry%5D=%24lang&users%5B0%5D%5Bfirstnamephonetic%5D=%2Fqwwsss%2F&users%5B0%5D%5Blastnamephonetic%5D=%2Fqwwsss%2F&users%5B0%5D%5Bmiddlename%5D=Yost&users%5B0%5D%5Balternatename%5D=Alva+Bins&users%5B0%5D%5Bcustomfields%5D%5B0%5D%5Btype%5D=brithday&users%5B0%5D%5Bcustomfields%5D%5B0%5D%5Bvalue%5D=533122112&users%5B0%5D%5Bcustomfields%5D%5B1%5D%5Btype%5D=anivers%C3%A1rio&users%5B0%5D%5Bcustomfields%5D%5B1%5D%5Bvalue%5D=100100010&users%5B0%5D%5Bpreferences%5D%5B0%5D%5Btype%5D=maildigest&users%5B0%5D%5Bpreferences%5D%5B0%5D%5Bvalue%5D=1&users%5B0%5D%5Bpreferences%5D%5B1%5D%5Btype%5D=editorformat&users%5B0%5D%5Bpreferences%5D%5B1%5D%5Bvalue%5D=4";

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
	public final void testIfGetTheRightClassUsingFactory() throws Exception {
		MoodleWSFunction function1 = MoodleWSFunctionFactory.getFunction(MoodleWSFunctions.CORE_USER_UPDATE_USERS,
				configMck);
		MoodleWSFunction function2 = MoodleWSFunctionFactory.getFunction(MoodleWSFunctions.MOODLE_USER_UPDATE_USERS,
				configMck);
		assertThat(function1, instanceOf(MoodleRestUpdateUser.class));
		assertThat(function2, instanceOf(MoodleRestUpdateUser.class));
	}

	@Test
	public final void testIfReturnTheRightAddedVersion() throws Exception {
		// This function id added in 2.0.0
		MoodleRestUpdateUser createUser = new MoodleRestUpdateUser(configMck);
		assertEquals("2.0.0", createUser.getSinceVersion());
	}

	@Test(expected = MoodleWSFucntionException.class)
	public final void testIfThrowExceptionIfWrongMoodleVersionInConstructor() throws MoodleConfigException, MoodleWSFucntionException
			 {
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenReturn(-1);
		when(configMck.getVersion()).thenReturn("1.4.0");
		new MoodleRestUpdateUser(configMck);

	}
	
	@Test
	public final void testIfAddUserWithoutIdThrowsExecpiton() throws MoodleWSFucntionException, MoodleConfigException
			{
		MoodleUser user1 = Fixture.from(MoodleUser.class).gimme("MoodleRestUpdateUserFunction");
		MoodleUser user2 = Fixture.from(MoodleUser.class).gimme("MoodleRestUpdateUserFunction");

		user1.setId(null);
		user2.setId(0l);

		MoodleRestUpdateUser function1 = (MoodleRestUpdateUser) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_UPDATE_USERS, configMck);

		try {
			function1.addUser(user1);
		} catch (MoodleRestUpdateUserException e) {
			assertThat(e, instanceOf(MoodleRestUpdateUserException.class));
		}

		try {
			function1.addUser(user2);
		} catch (MoodleRestUpdateUserException e) {
			assertThat(e, instanceOf(MoodleRestUpdateUserException.class));
		}
	}

	@Test(expected = MoodleRestUpdateUserException.class)
	public final void testIfGetFunctionDataThrowExceptionWhenNoUsersIsSet()
			throws MoodleWSFucntionException, MoodleConfigException {

		MoodleRestUpdateUser function1 = (MoodleRestUpdateUser) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_UPDATE_USERS, configMck);

		function1.getFunctionData();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public final void testIfGetFunctionDataCallMoodleRestUserFunctioTooleSerializeUsers() throws Exception {

		Set<MoodleUser> mdlUsers = new LinkedHashSet(
				Fixture.from(MoodleUser.class).gimme(3, "MoodleRestUpdateUserFunction"));
		MoodleRestUserFunctionsTools userFunctionsTools = mock(MoodleRestUserFunctionsTools.class);

		MoodleRestUpdateUser function1 = PowerMockito.spy((MoodleRestUpdateUser) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_UPDATE_USERS, configMck));

		PowerMockito.doReturn(userFunctionsTools).when(function1, "getUserFuntionsTools");

		function1.setUsers(mdlUsers);
		function1.getFunctionData();

		verify(userFunctionsTools).serliazeUsers(mdlUsers);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public final void testIfGetTheRightFunctionStrByMoodleVersion() throws Exception {
		Set<MoodleUser> mdlUsers = new LinkedHashSet(
				Fixture.from(MoodleUser.class).gimme(3, "MoodleRestUpdateUserFunction"));
		MoodleRestUserFunctionsTools userFunctionsTools = mock(MoodleRestUserFunctionsTools.class);
		when(userFunctionsTools.serliazeUsers(anySet())).thenReturn(serializedUser);

		MoodleRestUpdateUser function1 = PowerMockito.spy((MoodleRestUpdateUser) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_UPDATE_USERS, configMck));
		// PowerMockito.doReturn(mdlUsers).when(function1, "getUsers");
		PowerMockito.doReturn(userFunctionsTools).when(function1, "getUserFuntionsTools");

		StringBuilder expectedStr = new StringBuilder();
		expectedStr.append(MoodleTools.encode("wsfunction")).append("=")
				.append(MoodleTools.encode(MoodleWSFunctions.CORE_USER_UPDATE_USERS.getValue())).append("&")
				.append(serializedUser);
		function1.setUsers(mdlUsers);
		assertThat(function1.getFunctionData(), equalTo(expectedStr.toString()));

		expectedStr.delete(0, expectedStr.length());

		when(configMck.getVersion()).thenReturn("2.1.3");
		MoodleRestUpdateUser function2 = PowerMockito.spy((MoodleRestUpdateUser) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_UPDATE_USERS, configMck));
		// PowerMockito.doReturn(mdlUsers).when(function2, "getUsers");
		PowerMockito.doReturn(userFunctionsTools).when(function2, "getUserFuntionsTools");

		expectedStr.append(MoodleTools.encode("wsfunction")).append("=")
				.append(MoodleTools.encode(MoodleWSFunctions.CORE_USER_UPDATE_USERS.getValue())).append("&")
				.append(serializedUser);
		function2.setUsers(mdlUsers);
		assertThat(function2.getFunctionData(), equalTo(expectedStr.toString()));
	}

	

	@Test
	public final void testIfGetTheRightFunctionNameByMoodleVersion() throws Exception {
		// "core_user_create_users", "moodle_user_create_users"
		// This function chages name in moodle 2.2

		MoodleRestUpdateUser mdlfnc22 = (MoodleRestUpdateUser) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_UPDATE_USERS, configMck);
		String fnc22Name = mdlfnc22.getFunctionName();

		when(configMck.getVersion()).thenReturn("2.1.0");
		MoodleRestUpdateUser mdlfnc20 = new MoodleRestUpdateUser(configMck);
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenReturn(-1);
		String fnc20Name = mdlfnc20.getFunctionName();

		assertEquals("core_user_update_users", fnc22Name);
		assertEquals("moodle_user_update_users", fnc20Name);

	}

	@Test
	public final void testIfDoCallMethodCallsMoodleWSFunctionCallCallMethod() throws Exception {

		// Document userResponse = usersFixture.getRespone();
		MoodleWSFunctionCall wsFunctionCallMck = mock(MoodleWSFunctionCall.class);
		// when(wsFunctionCallMck.call(any(MoodleWSFunction.class))).thenReturn(null);
		PowerMockito.mockStatic(MoodleWSFunctionCall.class);
		PowerMockito.when(MoodleWSFunctionCall.getInstance(any(MoodleConfig.class))).thenReturn(wsFunctionCallMck);

		MoodleRestUpdateUser mdlfnc = (MoodleRestUpdateUser) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_UPDATE_USERS, configMck);
		
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Set<MoodleUser> mdlUsers = new LinkedHashSet(
				Fixture.from(MoodleUser.class).gimme(3, "MoodleRestUpdateUserFunction"));

		mdlfnc.setUsers(mdlUsers);
		try {
			mdlfnc.doCall();
		} catch (NullPointerException e) {
			// Null poiter is excpeted since wsFunctionCall is a mock
		}

		verify(wsFunctionCallMck).call(mdlfnc);
	}

	@Test
	public final void testIfDoCallRetunrsNull() throws Exception {

		
		MoodleWSFunctionCall wsFunctionCallMck = mock(MoodleWSFunctionCall.class);
		when(wsFunctionCallMck.call(any(MoodleWSFunction.class))).thenReturn(null);
		PowerMockito.mockStatic(MoodleWSFunctionCall.class);
		PowerMockito.when(MoodleWSFunctionCall.getInstance(any(MoodleConfig.class))).thenReturn(wsFunctionCallMck);

		MoodleRestUpdateUser mdlfnc = (MoodleRestUpdateUser) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_UPDATE_USERS, configMck);

		
		@SuppressWarnings("unchecked")
		Set<MoodleUser> usersToAdd = new LinkedHashSet(
				Fixture.from(MoodleUser.class).gimme(3, "MoodleRestUpdateUserFunction"));
	
		
		assertThat(usersToAdd.size(), equalTo(3));
		

		mdlfnc.setUsers(usersToAdd);
		assertThat(mdlfnc.doCall(), nullValue());
		

	}

	
	
}
