package ml.jmoodle.config;

import java.net.URL;
import static org.mockito.Mockito.*;
import org.junit.Rule;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;




public class MoodleConfigTest {
	
	@Rule
    public MockitoRule rule = MockitoJUnit.rule();

	
	public static void setUpBeforeClass() throws Exception {
		//MockitoAnnotations.initMocks(MoodleConfigTest.class);
	}

	public static void tearDownAfterClass() throws Exception {
	}

	public void setUp() throws Exception {
	}

	public void tearDown() throws Exception {
	}

	public final void MoodleConfigConstructorTest() {
		URL testUrl = new URL("http://test.com");
		MoodleConfig config=spy(new MoodleConfig(testUrl));
		doReturn(testUrl).when(config).getMoodleURL();
		
	}

	
}
