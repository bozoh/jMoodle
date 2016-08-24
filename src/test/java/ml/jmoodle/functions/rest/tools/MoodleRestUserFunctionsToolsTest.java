package ml.jmoodle.functions.rest.tools;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.functions.rest.MoodleRestGetUsers;
import ml.jmoodle.functions.rest.MoodleRestGetUsersByFields;
import ml.jmoodle.functions.rest.MoodleRestGetUsersByFields.Field;
import ml.jmoodle.functions.rest.MoodleRestGetUsers.Criteria;
import ml.jmoodle.functions.rest.fixtures.UsersFixture;
import ml.jmoodle.tools.MoodleTools;

public class MoodleRestUserFunctionsToolsTest {
	private MoodleRestUserFunctionsTools uft = new MoodleRestUserFunctionsTools();

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("ml.jmoodle.functions.rest.fixtures");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public final void testSerializeUsers() throws Exception {
		List users = Fixture.from(MoodleUser.class).gimme(3, "MoodleRestUpdateUserFunction");
		String usersStr = uft.serliazeUsers(new LinkedHashSet<MoodleUser>(users));
		int index = 0;
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			MoodleUser mdlUser = (MoodleUser) iterator.next();
			assertsUser(usersStr, mdlUser, index);
			index++;

		}

	}

	@Test
	public final void testSerializeCtriterias() throws Exception {
		UsersFixture fixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersResponse");
		Set<MoodleRestGetUsers.Criteria> criterias = fixture.getCriterias();
		String criteriaStr = uft.serliazeCriterias(criterias);
		int index = 0;
		for (Criteria criteria : criterias) {
			assertsCriteria(criteriaStr, criteria, index);
			index++;
		}

	}

	private void assertsCriteria(String criteriaStr, Criteria criteria, int index) throws UnsupportedEncodingException {
		Assert.assertThat(criteriaStr, containsString(MoodleTools.encode("criteria[" + index + "][key]") + "="
				+ MoodleTools.encode(String.valueOf(criteria.getName()))));
		Assert.assertThat(criteriaStr, containsString(MoodleTools.encode("criteria[" + index + "][value]") + "="
				+ MoodleTools.encode(String.valueOf(criteria.getValue()))));

	}

	@Test
	public final void testSerializeUser() throws Exception {
		MoodleUser moodleUser = Fixture.from(MoodleUser.class).gimme("MoodleRestUpdateUserFunction");

		String userStr = uft.serializeUser(moodleUser);
		assertsUser(userStr, moodleUser, 0);

	}

	@Test
	public final void testSerliazeUserFields() throws Exception {
		UsersFixture usersFixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersByFieldsResponse");

		MoodleRestGetUsersByFields.Field field = MoodleRestGetUsersByFields.Field.ID;
		Set<String> values = usersFixture.getFieldValues();
		String userFieldsStr = uft.serliazeFields(field, values);
		int i = 0;
		for (String value : values) {
			assertUserFields(field, userFieldsStr, value, i);
			i++;
		}
	}

	private void assertUserFields(Field field, String userFieldsStr, String value, int i)
			throws UnsupportedEncodingException {
		// field= str
		// values[0]=

		Assert.assertThat(userFieldsStr,
				containsString(MoodleTools.encode("field") + "=" + MoodleTools.encode(field.toString())));
		Assert.assertThat(userFieldsStr,
				containsString(MoodleTools.encode("values[" + i + "]") + "=" + MoodleTools.encode(value)));

	}

	@Test
	public final void testIfUnSerializeUsersReturnsUsersSet() throws Exception {
		UsersFixture usersFixture = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersResponse");
		Document response = usersFixture.getGetUsersRespone();
		
		Set<MoodleUser> testSet = uft.unSerializeUsers(response);
		Set<MoodleUser> expected = usersFixture.getMdlUsers();
		for (MoodleUser moodleUser : expected) {
			assertThat(testSet, hasItem(moodleUser));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public final void testSerializeUserIds() throws Exception {
		Set<MoodleUser> users = new HashSet(Fixture.from(MoodleUser.class).gimme(3, "MoodleRestDeleteUsersFunction"));

		String userIdsStr = uft.serliazeUsersIds(users);
		int i = 0;
		for (MoodleUser user : users) {
			assertUserIds(userIdsStr, user, i);
			i++;
		}

	}

	private void assertUserIds(String userIdsStr, MoodleUser user, int i) throws UnsupportedEncodingException {
		// userids[0] = int
		Assert.assertThat(userIdsStr, containsString(
				MoodleTools.encode("userids[" + i + "]") + "=" + MoodleTools.encode(String.valueOf(user.getId()))));

	}

	public static void assertsUser(String userStr, MoodleUser moodleUser, int index)
			throws UnsupportedEncodingException {
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][id]") + "="
				+ MoodleTools.encode(String.valueOf(moodleUser.getId()))));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][username]") + "="
				+ MoodleTools.encode(moodleUser.getUsername())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][firstname]") + "="
				+ MoodleTools.encode(moodleUser.getFirstname())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][lastname]") + "="
				+ MoodleTools.encode(moodleUser.getLastname())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][password]") + "="
				+ MoodleTools.encode(moodleUser.getPassword())));
		Assert.assertThat(userStr, containsString(
				MoodleTools.encode("users[" + index + "][email]") + "=" + MoodleTools.encode(moodleUser.getEmail())));
		Assert.assertThat(userStr, containsString(
				MoodleTools.encode("users[" + index + "][auth]") + "=" + MoodleTools.encode(moodleUser.getAuth())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][idnumber]") + "="
				+ MoodleTools.encode(moodleUser.getIdnumber())));

		Assert.assertThat(userStr, containsString(
				MoodleTools.encode("users[" + index + "][lang]") + "=" + MoodleTools.encode(moodleUser.getLang())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][calendartype]") + "="
				+ MoodleTools.encode(moodleUser.getCalendartype())));
		Assert.assertThat(userStr, containsString(
				MoodleTools.encode("users[" + index + "][theme]") + "=" + MoodleTools.encode(moodleUser.getTheme())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][timezone]") + "="
				+ MoodleTools.encode(moodleUser.getTimezone())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][mailformat]") + "="
				+ MoodleTools.encode(String.valueOf(moodleUser.getMailformat()))));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][description]") + "="
				+ MoodleTools.encode(moodleUser.getDescription())));
		Assert.assertThat(userStr, containsString(
				MoodleTools.encode("users[" + index + "][city]") + "=" + MoodleTools.encode(moodleUser.getCity())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][country]") + "="
				+ MoodleTools.encode(moodleUser.getCountry())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][firstnamephonetic]") + "="
				+ MoodleTools.encode(moodleUser.getFirstnamephonetic())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][lastnamephonetic]") + "="
				+ MoodleTools.encode(moodleUser.getLastnamephonetic())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][middlename]") + "="
				+ MoodleTools.encode(moodleUser.getMiddlename())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][alternatename]") + "="
				+ MoodleTools.encode(moodleUser.getAlternatename())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][preferences][0][type]") + "="
				+ MoodleTools.encode(moodleUser.getPreferences().iterator().next().getName())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][preferences][0][value]")
				+ "=" + MoodleTools.encode(moodleUser.getPreferences().iterator().next().getValue())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][customfields][0][type]")
				+ "=" + MoodleTools.encode(moodleUser.getCustomfields().iterator().next().getName())));
		Assert.assertThat(userStr, containsString(MoodleTools.encode("users[" + index + "][customfields][0][value]")
				+ "=" + MoodleTools.encode(moodleUser.getCustomfields().iterator().next().getValue())));
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


	public static void main(String[] args) throws Exception {
		FixtureFactoryLoader.loadTemplates("ml.jmoodle.functions.rest.fixtures");
		UsersFixture users = Fixture.from(UsersFixture.class).gimme("MoodleRestGetUsersByFieldsResponse");
		MoodleRestUserFunctionsTools uft = new MoodleRestUserFunctionsTools();
		System.out.println(uft.serliazeFields(Field.ID, users.getFieldValues()));
		
		
		String test="text";
		System.out.println(MoodleUser.CustomFieldType.valueOf(test.toUpperCase()).name());
		// M
	}


}
