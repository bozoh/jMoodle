package ml.jmoodle.functions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.w3c.dom.Document;

import ml.jmoodle.authentications.MoodleAuthentication;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.exceptions.MoodleWSFunctionCallException;
import ml.jmoodle.functions.rest.fixtures.UsersFixture;

public class MoodleWSFunctionCallTest {
	
	@Rule
	public MockitoRule rule = MockitoJUnit.rule();
	
	@Mock
	MoodleConfig cfgMock;
	
	@Mock
	MoodleWSFunction fncMock;
	
	@Mock
	MoodleAuthentication mdlAuthMock;
	
	MoodleWSFunctionCall fcntCall;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		when(cfgMock.getMoodleURL()).thenReturn(new URL("http://test.test"));
		when(fncMock.getFunctionData()).thenReturn(UsersFixture.CREATE_USER_DATA);
		when(mdlAuthMock.getAuthentication()).thenReturn("?wstoken=MYTOKEN");
		fcntCall=MoodleWSFunctionCall.getInstance(cfgMock);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testIfMoodleConfigGetMoodleURLIsCalled() throws Exception{
		fcntCall.call(fncMock);
		
		verify(cfgMock).getMoodleURL();
	}
	
	@Test
	public final void testIfMoodelWSFunctionGetFunctionDataIsCalled() throws Exception {
		fcntCall.call(fncMock);
		
		verify(fncMock).getFunctionData();
	}
	
	@Test
	public final void testIfResponceIsProcessed() throws Exception {
		Document response=fcntCall.call(fncMock);
		
		assertEquals(response.getElementsByTagName("RESPONSE").getLength(), 1);
		
		//Assert something
		fail("Not yet implemented"); // TODO
	}
	
	@Test(expected=MoodleWSFunctionCallException.class)
	public final void testIfErroResponceThrowsException() throws MoodleWSFunctionCallException {
		Object response=fcntCall.call(fncMock);
	}

}
