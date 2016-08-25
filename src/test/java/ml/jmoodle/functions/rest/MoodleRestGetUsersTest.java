package ml.jmoodle.functions.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSFunction;
import ml.jmoodle.functions.MoodleWSFunctionCall;
import ml.jmoodle.functions.MoodleWSFunctionFactory;
import ml.jmoodle.functions.MoodleWSFunctions;
import ml.jmoodle.functions.exceptions.MoodleRestGetUsersException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.MoodleRestGetUsers.Criteria;
import ml.jmoodle.functions.rest.fixtures.UsersFixture;
import ml.jmoodle.functions.rest.tools.MoodleRestUserFunctionsTools;
import ml.jmoodle.tools.MoodleTools;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MoodleTools.class, MoodleRestGetUsers.class, MoodleWSFunctionCall.class })
public class MoodleRestGetUsersTest implements MoodleRestFunctionsCommonsTest {
	URL mdlUrl;

	MoodleConfig configMck;
	String serializedCriterias = "criteria%5B0%5D%5Bkey%5D=firstname&criteria%5B0%5D%5Bvalue%5D=Johnathon&criteria%5B1%5D%5Bkey%5D=firstname&criteria%5B1%5D%5Bvalue%5D=Christian&criteria%5B2%5D%5Bkey%5D=lastname&criteria%5B2%5D%5Bvalue%5D=VonRueden&criteria%5B3%5D%5Bkey%5D=lastname&criteria%5B3%5D%5Bvalue%5D=Ankunding&criteria%5B4%5D%5Bkey%5D=firstname&criteria%5B4%5D%5Bvalue%5D=Salvatore&criteria%5B5%5D%5Bkey%5D=lastname&criteria%5B5%5D%5Bvalue%5D=Gutkowski&criteria%5B6%5D%5Bkey%5D=username&criteria%5B6%5D%5Bvalue%5D=Salvatore-VonRueden&criteria%5B7%5D%5Bkey%5D=username&criteria%5B7%5D%5Bvalue%5D=Johnathon-Gutkowski&criteria%5B8%5D%5Bkey%5D=username&criteria%5B8%5D%5Bvalue%5D=Christian-Ankunding";

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

		MoodleWSFunction function = MoodleWSFunctionFactory.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS,
				configMck);

		assertThat(function, instanceOf(MoodleRestGetUsers.class));

	}

	@Test
	public final void testIfReturnTheRightAddedVersion() throws Exception {
		// This function id added in 2.5.0
		MoodleRestGetUsers createUser = new MoodleRestGetUsers(configMck);
		assertEquals("2.5.0", createUser.getSinceVersion());
	}

	@Test(expected = MoodleWSFucntionException.class)
	public final void testIfThrowExceptionIfWrongMoodleVersionInConstructor()
			throws MoodleConfigException, MoodleWSFucntionException {
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenReturn(-1);
		when(configMck.getVersion()).thenReturn("1.4.0");
		new MoodleRestGetUsers(configMck);

	}

	@Test
	public final void testIfAddCriteriaWithoutKeyThrowsExecpiton() throws Exception {
		UsersFixture usersFixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersResponse");
		Set<Criteria> criterias = usersFixture.getCriterias();
		Iterator<Criteria> i = criterias.iterator();
		MoodleRestGetUsers.Criteria criteria1 = i.next();
		MoodleRestGetUsers.Criteria criteria2 = i.next();

		criteria1.setName(null);
		criteria1.setName("");

		MoodleRestGetUsers function1 = (MoodleRestGetUsers) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS, configMck);

		try {
			function1.addCriteria(criteria1);
		} catch (MoodleRestGetUsersException e) {
			assertThat(e, instanceOf(MoodleRestGetUsersException.class));
		}

		try {
			function1.addCriteria(criteria2);
		} catch (MoodleRestGetUsersException e) {
			assertThat(e, instanceOf(MoodleRestGetUsersException.class));
		}
	}

	@Test(expected = MoodleRestGetUsersException.class)
	public final void testIfGetFunctionDataThrowExceptionWhenNoCriteriasIsSet()
			throws MoodleWSFucntionException, MoodleConfigException {

		MoodleRestGetUsers function1 = (MoodleRestGetUsers) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS, configMck);

		function1.getFunctionData();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public final void testIfGetFunctionDataCallMoodleRestUserFunctioTooleSerializeCriterias() throws Exception {

		UsersFixture usersFixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersResponse");

		Set<MoodleRestGetUsers.Criteria> criterias = usersFixture.getCriterias();
		MoodleRestUserFunctionsTools userFunctionsTools = mock(MoodleRestUserFunctionsTools.class);

		MoodleRestGetUsers function1 = PowerMockito.spy((MoodleRestGetUsers) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS, configMck));

		PowerMockito.doReturn(userFunctionsTools).when(function1, "getUserFuntionsTools");

		function1.setCriterias(criterias);
		function1.getFunctionData();

		verify(userFunctionsTools).serliazeCriterias(criterias);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public final void testIfGetTheRightFunctionStrByMoodleVersion() throws Exception {
		UsersFixture usersFixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersResponse");

		Set<MoodleRestGetUsers.Criteria> criterias = usersFixture.getCriterias();
		MoodleRestUserFunctionsTools userFunctionsTools = mock(MoodleRestUserFunctionsTools.class);
		when(userFunctionsTools.serliazeCriterias(anySet())).thenReturn(serializedCriterias);

		MoodleRestGetUsers function1 = PowerMockito.spy((MoodleRestGetUsers) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS, configMck));
		// PowerMockito.doReturn(mdlUsers).when(function1, "getUsers");
		PowerMockito.doReturn(userFunctionsTools).when(function1, "getUserFuntionsTools");

		StringBuilder expectedStr = new StringBuilder();
		expectedStr.append(MoodleTools.encode("wsfunction")).append("=")
				.append(MoodleTools.encode("core_user_get_users")).append("&").append(serializedCriterias);
		function1.setCriterias(criterias);
		assertThat(function1.getFunctionData(), equalTo(expectedStr.toString()));

	}

	@Test
	public final void testIfGetTheRightFunctionNameByMoodleVersion() throws Exception {
		// "core_user_create_users", "moodle_user_create_users"
		// This function chages name in moodle 2.2

		MoodleRestGetUsers mdlfnc = (MoodleRestGetUsers) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS, configMck);
		String fncName = mdlfnc.getFunctionName();

		assertEquals("core_user_get_users", fncName);
	}

	@Test
	public final void testIfDoCallMethodCallsMoodleWSFunctionCallCallMethod() throws Exception {

		MoodleWSFunctionCall wsFunctionCallMck = mock(MoodleWSFunctionCall.class);
		// when(wsFunctionCallMck.call(any(MoodleWSFunction.class))).thenReturn(null);
		PowerMockito.mockStatic(MoodleWSFunctionCall.class);
		PowerMockito.when(MoodleWSFunctionCall.getInstance(any(MoodleConfig.class))).thenReturn(wsFunctionCallMck);

		MoodleRestGetUsers mdlfnc = (MoodleRestGetUsers) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS, configMck);

		UsersFixture fixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersResponse");

		@SuppressWarnings({ "rawtypes", "unchecked" })
		Set<MoodleRestGetUsers.Criteria> criterias = fixture.getCriterias();

		mdlfnc.setCriterias(criterias);
		try {
			mdlfnc.doCall();
		} catch (NullPointerException e) {
			// Null poiter is excpeted since wsFunctionCall is a mock
		}

		verify(wsFunctionCallMck).call(mdlfnc);
	}

	@Test
	public final void testIfDoCallMethodCallsMoodleRestUserFunctionToolsUnserializeUsersMethod() throws Exception {

		UsersFixture usersFixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersResponse");
		Document userResponse = usersFixture.getGetUsersRespone();

		MoodleWSFunctionCall wsFunctionCallMck = mock(MoodleWSFunctionCall.class);
		when(wsFunctionCallMck.call(any(MoodleWSFunction.class))).thenReturn(userResponse);
		PowerMockito.mockStatic(MoodleWSFunctionCall.class);
		PowerMockito.when(MoodleWSFunctionCall.getInstance(any(MoodleConfig.class))).thenReturn(wsFunctionCallMck);

		MoodleRestUserFunctionsTools userFunctionsTools = mock(MoodleRestUserFunctionsTools.class);
		MoodleRestGetUsers mdlfnc = PowerMockito.spy((MoodleRestGetUsers) MoodleWSFunctionFactory
				.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS, configMck));
		// PowerMockito.doReturn(mdlUsers).when(function1, "getUsers");
		PowerMockito.doReturn(userFunctionsTools).when(mdlfnc, "getUserFuntionsTools");

		UsersFixture fixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersResponse");

		@SuppressWarnings({ "rawtypes", "unchecked" })
		Set<MoodleRestGetUsers.Criteria> criterias = fixture.getCriterias();

		mdlfnc.setCriterias(criterias);
		try {
			mdlfnc.doCall();
		} catch (NullPointerException e) {
			// Null poiter is excpeted since wsFunctionCall is a mock
		}

		verify(userFunctionsTools).unSerializeUsers(any(NodeList.class));
	}

	@Test
	public final void testIfDoCallMethodReturnMoodleUserCollection() throws Exception {
		// TODO test warnings

		UsersFixture usersFixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersResponse");
		Document userResponse = usersFixture.getGetUsersRespone();

		MoodleWSFunctionCall wsFunctionCallMck = mock(MoodleWSFunctionCall.class);
		when(wsFunctionCallMck.call(any(MoodleWSFunction.class))).thenReturn(userResponse);
		PowerMockito.mockStatic(MoodleWSFunctionCall.class);
		PowerMockito.when(MoodleWSFunctionCall.getInstance(any(MoodleConfig.class))).thenReturn(wsFunctionCallMck);

//		MoodleRestUserFunctionsTools userFunctionsTools = mock(MoodleRestUserFunctionsTools.class);
		MoodleRestGetUsers mdlfnc = (MoodleRestGetUsers) MoodleWSFunctionFactory
			.getFunction(MoodleWSFunctions.CORE_USER_GET_USERS, configMck);
//		// PowerMockito.doReturn(mdlUsers).when(function1, "getUsers");
//		PowerMockito.doReturn(userFunctionsTools).when(mdlfnc, "getUserFuntionsTools");

		UsersFixture fixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersResponse");

		Set<MoodleRestGetUsers.Criteria> criterias = fixture.getCriterias();

		mdlfnc.setCriterias(criterias);
		Set<MoodleUser> response = mdlfnc.doCall();

		Set<MoodleUser> expectedUsers = usersFixture.getMdlUsers();
		
		assertEquals(expectedUsers.size(), response.size());
		
		for (MoodleUser moodleUser : expectedUsers) {
			assertThat(response, hasItem(moodleUser));
		}
	}

	public static void main(String args[])
			throws Exception{
		MoodleRestUserFunctionsTools tools = new MoodleRestUserFunctionsTools();
		FixtureFactoryLoader.loadTemplates("ml.jmoodle.functions.rest.fixtures");
		UsersFixture fixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersResponse");
		Document userResponse = fixture.getGetUsersRespone();
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList usersNodeList = (NodeList) xPath.compile("/RESPONSE/SINGLE/KEY[@name=\"users\"]/MULTIPLE/SINGLE")
				.evaluate(userResponse, XPathConstants.NODESET);
		
		
		Set<MoodleUser> response = tools.unSerializeUsers(usersNodeList);
		for (MoodleUser moodleUser : response) {
			System.out.println(moodleUser);
		}
		

	}

}
