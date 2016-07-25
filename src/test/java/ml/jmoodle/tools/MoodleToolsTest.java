package ml.jmoodle.tools;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;

/**
 * MoodleTools a set of commons tools
 * 
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ MoodleConfig.class })
public class MoodleToolsTest {

	@Test
	public void MoodleToolsEncodeTest() throws Exception {
		String param = "aá[]ç&=?ãb";
		String encodedParam = MoodleTools.encode(param);
		//See more http://meyerweb.com/eric/tools/dencoder/
		assertEquals("a%C3%A1%5B%5D%C3%A7%26%3D%3F%C3%A3b", encodedParam);
	}

	@Test
	public void MoodleToolsCompareVersionCallVerifyVersionTest() throws Exception {
		PowerMockito.spy(MoodleConfig.class);
		PowerMockito.doReturn(true).when(MoodleConfig.class, "verifyVersion", anyString());
				
		String mdlVersion = "4.1.8";
		
		// Verify if verifyVersion is Called
		MoodleTools.compareVersion(mdlVersion, mdlVersion);
		PowerMockito.verifyStatic(times(2));
		MoodleConfig.verifyVersion(eq(mdlVersion));
	}
	@Test
	public void MoodleToolsCompareVersionTest() throws Exception {
		PowerMockito.spy(MoodleConfig.class);
		PowerMockito.doReturn(true).when(MoodleConfig.class, "verifyVersion", anyString());


		String bigestMdlVersion = "4.1.9";
		String mdlVersion = "4.1.8";
		String smallestMdlVersion = "4.1.7";

		assert (MoodleTools.compareVersion(bigestMdlVersion, mdlVersion) > 0);
		assert (MoodleTools.compareVersion(bigestMdlVersion, smallestMdlVersion) > 0);
		assert (MoodleTools.compareVersion(bigestMdlVersion, bigestMdlVersion) == 0);
		assert (MoodleTools.compareVersion(smallestMdlVersion, smallestMdlVersion) == 0);
		assert (MoodleTools.compareVersion(mdlVersion, mdlVersion) == 0);
		assert (MoodleTools.compareVersion(mdlVersion, bigestMdlVersion) < 0);
		assert (MoodleTools.compareVersion(smallestMdlVersion, bigestMdlVersion) < 0);

	}


	@Test(expected = MoodleConfigException.class)
	public void MoodleToolsCompareVersionThrowExceptionWhenInvalidMoodleVersionTest() throws MoodleConfigException  {
		MoodleTools.compareVersion("0.1", "1.0.0-RC");
	}
	


}
