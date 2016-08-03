package ml.jmoodle.functions;

import static org.mockito.Matchers.anyString;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.tools.MoodleTools;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MoodleWSFunctionFactory.class, MoodleTools.class, MoodleWSFunctions.class })
public class MoodleWSFunctionFactoryTest {

	
	@Rule
	public MockitoRule rule = MockitoJUnit.rule();
	// @Mock
	// Class clazzMock;
	final String moodleVersion = "2.3.1";
	final String functionVersion = "3.1.0";

	@Mock
	MoodleWSFunction mdlFnctionMock;
	//

	MoodleWSFunctions classNameMock = PowerMockito.mock(MoodleWSFunctions.class);
	// MoodleWSFunctions.MOODLE_USER_CREATE_USERS;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// Spy Static
		PowerMockito.spy(MoodleWSFunctionFactory.class);
		PowerMockito.doReturn(mdlFnctionMock).when(MoodleWSFunctionFactory.class, "factory", anyString(), anyString());
		
		when(mdlFnctionMock.getSinceVersion()).thenReturn(functionVersion);

		// Mock an enum, must use powermock
		PowerMockito.when(classNameMock.getValue()).thenReturn("Doesn't matter");

		PowerMockito.mockStatic(MoodleTools.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Test
	public void testGetFunctionCallFactoryMethod() throws Exception {
		MoodleWSFunctionFactory.getFunction(classNameMock, moodleVersion);
		PowerMockito.verifyPrivate(MoodleWSFunctionFactory.class).invoke("factory", anyString(), anyString());
	}

	@Test
	public void testGetFunctionWithMoodleVersionBiggerThenFunctionVersion() throws Exception {
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenReturn(1);
		MoodleWSFunction function = (MoodleWSFunction) MoodleWSFunctionFactory.getFunction(classNameMock,
				moodleVersion);
		assertThat(function, sameInstance(mdlFnctionMock));
	}

	@Test
	public void testGetFunctionWithMoodleVersionEquasThenFunctionVersion() throws Exception {
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenReturn(0);
		MoodleWSFunction function = (MoodleWSFunction) MoodleWSFunctionFactory.getFunction(classNameMock,
				moodleVersion);
		assertThat(function, sameInstance(mdlFnctionMock));
	}

}
