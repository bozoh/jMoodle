package ml.jmoodle.functions.rest.tools;

import static org.hamcrest.CoreMatchers.*;
import org.junit.Assert;
import org.junit.Test;

import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.tools.MoodleTools;

public class MoodleRestUserFunctionsToolsTest {
	private MoodleRestUserFunctionsTools uft = new MoodleRestUserFunctionsTools();

	@Test
	public final void testSerializeUsers() {
		Assert.fail("not implemented");
	}

	@Test
	public final void testSerializeUser() throws Exception {
		MoodleUser moodleUser = new MoodleUser();
		moodleUser.setId(33l);
		moodleUser.setUsername("asas");
		moodleUser.setFirstname("eeee");
		moodleUser.setLastname("wwww");
		moodleUser.setPassword("1212121");
		moodleUser.setEmail("as2Asss@as.xom");
		
		String userStr = uft.serializeUser(moodleUser);
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[0][id]")+"="+MoodleTools.encode(String.valueOf(moodleUser.getId()))));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[0][username]")+"="+MoodleTools.encode(moodleUser.getUsername())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[0][firstname]")+"="+MoodleTools.encode(moodleUser.getFirstname())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[0][lastname]")+"="+MoodleTools.encode(moodleUser.getLastname())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[0][password]")+"="+MoodleTools.encode(moodleUser.getPassword())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[0][email]")+"="+MoodleTools.encode(moodleUser.getEmail())));
		// users[0][id] = int
		// users[0][username]= string
		// users[0][password]= string
		// users[0][firstname]= string
		// users[0][lastname]= string
		// users[0][email]= string
		// users[0][auth]= string
		// users[0][idnumber]= string
		// users[0][lang]= string
		// users[0][calendartype]= string
		// users[0][theme]= string
		// users[0][timezone]= string
		// users[0][mailformat]= int
		// users[0][description]= string
		// users[0][city]= string
		// users[0][country]= string
		// users[0][firstnamephonetic]= string
		// users[0][lastnamephonetic]= string
		// users[0][middlename]= string
		// users[0][alternatename]= string
		// users[0][preferences][0][type]= string
		// users[0][preferences][0][value]= string
		// users[0][customfields][0][type]= string
		// users[0][customfields][0][value]= string
		
	}

	@Test
	public final void testUnSerializeUsers() {
		Assert.fail("not implemented");
	}

	@Test
	public final void testUnSerializeUser() {
		Assert.fail("not implemented");
	}

}
