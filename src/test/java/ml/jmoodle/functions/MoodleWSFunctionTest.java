package ml.jmoodle.functions;

import static org.mockito.Matchers.anyString;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ml.jmoodle.tools.MoodleTools;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MoodleWSFunction.class, MoodleTools.class, MoodleWSFunctions.class })
public class MoodleWSFunctionTest {

	// @Mock
	// Class clazzMock;
	final String moodleVersion = "2.3.1";
	final String functionVersion = "3.1.0";

	@Spy
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
		PowerMockito.spy(MoodleWSFunction.class);
		PowerMockito.doReturn(mdlFnctionMock).when(MoodleWSFunction.class, "factory", anyString());
		// Normal Spy
		Mockito.doReturn(functionVersion).when(mdlFnctionMock).getSinceVersion();

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
	public void testFactoryMethodIsCalled() throws Exception {
		MoodleWSFunction.getFunction(classNameMock, moodleVersion);
		PowerMockito.verifyPrivate(MoodleWSFunction.class).invoke("factory", anyString());
	}

	
	@Test
	public void testGetFunctionWithMoodleVersionBiggerThenFunctionVersion() throws Exception {
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenReturn(1);
		assert (mdlFnctionMock.equals(MoodleWSFunction.getFunction(classNameMock, moodleVersion)));
	}

	@Test
	public void testGetFunctionWithMoodleVersionEquasThenFunctionVersion() throws Exception {
		PowerMockito.when(MoodleTools.compareVersion(anyString(), anyString())).thenReturn(0);
		assert (mdlFnctionMock.equals(MoodleWSFunction.getFunction(classNameMock, moodleVersion)));
	}

}
