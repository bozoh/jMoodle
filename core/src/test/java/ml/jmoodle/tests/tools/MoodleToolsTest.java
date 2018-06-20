package ml.jmoodle.tests.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ml.jmoodle.tools.MoodleTools;

/**
 * MoodleTools a set of commons tools
 * 
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
public class MoodleToolsTest {

	@Test
	public void MoodleToolsEncodeTest() throws Exception {
		String param = "aá[]ç&=?ãb";
		String encodedParam = MoodleTools.encode(param);
		//See more http://meyerweb.com/eric/tools/dencoder/
		assertEquals("a%C3%A1%5B%5D%C3%A7%26%3D%3F%C3%A3b", encodedParam);
	}

	@Test
	public void MoodleToolsVerifyVersionTest() throws Exception {
		assertTrue(MoodleTools.verifyMoodleVersion("4.1.8"));
		assertFalse(MoodleTools.verifyMoodleVersion("4.1-RC"));
	}
	
	@Test
	public void MoodleToolsCompareVersionTest() throws Exception {

		String bigestMdlVersion = "4.1.9";
		String mdlVersion = "4.1.8";
		String smallestMdlVersion = "4.1.7";

		assertTrue(MoodleTools.compareVersion(bigestMdlVersion, mdlVersion) > 0);
		assertTrue(MoodleTools.compareVersion(bigestMdlVersion, smallestMdlVersion) > 0);
		assertTrue(MoodleTools.compareVersion(bigestMdlVersion, bigestMdlVersion) == 0);
		assertTrue(MoodleTools.compareVersion(smallestMdlVersion, smallestMdlVersion) == 0);
		assertTrue(MoodleTools.compareVersion(mdlVersion, mdlVersion) == 0);
		assertTrue(MoodleTools.compareVersion(mdlVersion, bigestMdlVersion) < 0);
		assertTrue(MoodleTools.compareVersion(smallestMdlVersion, bigestMdlVersion) < 0);

	}

}
