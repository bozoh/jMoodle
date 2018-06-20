package ml.jmoodle.tests.functions.rest.user;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import ml.jmoodle.commons.Criteria;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.commons.MoodleWarning;
import ml.jmoodle.commons.UserCustomField;
import ml.jmoodle.commons.UserEnrolledCourse;
import ml.jmoodle.commons.UserCustomField.CustomFieldType;
import ml.jmoodle.tests.tools.TestTools;
import ml.jmoodle.commons.UserPreference;

public class MoodleUserFixtureTemplate implements TemplateLoader {

	public static String SERIALIZED_USER_DATA = "users%5B0%5D%5Bid%5D=355&users%5B0%5D%5Busername%5D=Rona-Auer&users%5B0%5D%5Bpassword%5D=awe2&users%5B0%5D%5Bfirstname%5D=Rona&users%5B0%5D%5Blastname%5D=Auer&users%5B0%5D%5Bemail%5D=Rona-Auer%40email.test&users%5B0%5D%5Bauth%5D=manual&users%5B0%5D%5Bidnumber%5D=024886573360022&users%5B0%5D%5Blang%5D=en_us&users%5B0%5D%5Bcalendartype%5D=gregorian&users%5B0%5D%5Btheme%5D=aasas&users%5B0%5D%5Btimezone%5D=Sao_Paulo&users%5B0%5D%5Bmailformat%5D=0&users%5B0%5D%5Bdescription%5D=foo+bar&users%5B0%5D%5Bcity%5D=Neil+Koch&users%5B0%5D%5Bcountry%5D=%24lang&users%5B0%5D%5Bfirstnamephonetic%5D=%2Fqwwsss%2F&users%5B0%5D%5Blastnamephonetic%5D=%2Fqwwsss%2F&users%5B0%5D%5Bmiddlename%5D=Yost&users%5B0%5D%5Balternatename%5D=Alva+Bins&users%5B0%5D%5Bcustomfields%5D%5B0%5D%5Btype%5D=brithday&users%5B0%5D%5Bcustomfields%5D%5B0%5D%5Bvalue%5D=533122112&users%5B0%5D%5Bcustomfields%5D%5B1%5D%5Btype%5D=anivers%C3%A1rio&users%5B0%5D%5Bcustomfields%5D%5B1%5D%5Bvalue%5D=100100010&users%5B0%5D%5Bpreferences%5D%5B0%5D%5Btype%5D=maildigest&users%5B0%5D%5Bpreferences%5D%5B0%5D%5Bvalue%5D=1&users%5B0%5D%5Bpreferences%5D%5B1%5D%5Btype%5D=editorformat&users%5B0%5D%5Bpreferences%5D%5B1%5D%5Bvalue%5D=4";
	public static String CREATE_USER_DATA = "wsfunction=core_user_create_users&" + SERIALIZED_USER_DATA;
	private Set<MoodleUser> mdlUsers;
	

	@Override
	public void load() {
		Fixture.of(MoodleUser.class).addTemplate("MoodleRestUpdateUserFunction", new Rule() {
			{
				add("id", regex("\\d{3,5}"));
				add("firstname", firstName());
				add("lastname", lastName());
				add("username", "${firstname}-${lastname}");
				add("email", "${username}@email.test");
				add("password", random("asasa", "awe2", "2332"));
				add("idnumber", regex("\\d{15}"));
				add("lang", random("pt_br", "en_us", "ch"));
				add("theme", random("aasas", "errr", "asa"));
				add("timezone", random("-3", "-2", "Sao_Paulo"));
				add("description", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("city", name());
				add("country", "$lang");
				add("firstnamephonetic", random("/asiu/", "/AskII/", "/qwwsss/"));
				add("lastnamephonetic", random("/asiu/", "/AskII/", "/qwwsss/"));
				add("middlename", lastName());
				add("alternatename", name());
				add("preferences", has(2).of(UserPreference.class, "MoodleUserPreferences"));
				add("customfields", has(2).of(UserCustomField.class, "MoodleUserCustomFields"));

			}
		});

		Fixture.of(MoodleUser.class).addTemplate("MoodleRestDeleteUsersFunction", new Rule() {
			{
				add("id", regex("\\d{3,5}"));
				add("firstname", firstName());
				add("lastname", lastName());
				add("username", "${firstname}-${lastname}");
				add("email", "${username}@email.test");
				add("password", random("asasa", "awe2", "2332"));
				add("idnumber", regex("\\d{15}"));
				add("lang", random("pt_br", "en_us", "ch"));
				add("theme", random("aasas", "errr", "asa"));
				add("timezone", random("-3", "-2", "Sao_Paulo"));
				add("description", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("city", name());
				add("country", "$lang");
				add("firstnamephonetic", random("/asiu/", "/AskII/", "/qwwsss/"));
				add("lastnamephonetic", random("/asiu/", "/AskII/", "/qwwsss/"));
				add("middlename", lastName());
				add("alternatename", name());
				add("preferences", has(2).of(UserPreference.class, "MoodleUserPreferences"));
				add("customfields", has(2).of(UserCustomField.class, "MoodleUserCustomFields"));


			}
		});

		Fixture.of(MoodleUser.class).addTemplate("MoodleRestGetUserByIdFunctionResponseEntities", new Rule() {
			{
				add("id", regex("\\d{3,10}"));
				add("firstname", firstName());
				add("lastname", lastName());
				add("username", "${firstname}-${lastname}");
				add("email", "${username}@email.test");
				add("password", random("asasa", "awe2", "2332"));
				add("idnumber", regex("\\d{15}"));
				add("lang", random("pt_br", "en_us", "ch"));
				add("theme", random("aasas", "errr", "asa"));
				add("timezone", random("-3", "-2", "Sao_Paulo"));
				add("description", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("city", name());
				add("country", "${lang}");
				add("firstnamephonetic", regex("\\w{1}"));
				add("lastnamephonetic", regex("\\w{1}"));
				add("middlename", lastName());
				add("alternatename", name());
				add("preferences", has(2).of(UserPreference.class, "MoodleUserPreferences"));
				add("customfields", has(2).of(UserCustomField.class, "MoodleUserCustomFields"));
				add("enrolledcourses", has(2).of(UserEnrolledCourse.class, "MoodleUserEnrolledCourse"));


				add("fullname", "${firstname} ${lastname}");

				add("address", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("phone1", regex("(\\d{2})-(\\d{5})-(\\d{4})"));
				add("phone2", regex("(\\d{2})-(\\d{5})-(\\d{4})"));
				add("icq", regex("\\d{6}"));
				add("skype", name());
				add("yahoo", "${email}");
				add("aim", regex("\\d{6}"));
				add("msn", "${email}");
				add("department", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("institution", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("interests", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("firstaccess", randomDate("2011-04-15", "2016-11-07", new SimpleDateFormat("yyyy-MM-dd")));
				add("lastaccess", randomDate("2011-04-15", "2016-11-07", new SimpleDateFormat("yyyy-MM-dd")));
				add("auth", "manual");
				add("confirmed", true);
				add("description", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("descriptionformat", 1);
				add("url", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("profileimageurlsmall", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("profileimageurl", random("lorem ipsum", "ipsum lorem", "foo bar"));
			}
		});

		Fixture.of(MoodleUser.class).addTemplate("MoodleRestGetUserFunctionUser", new Rule() {
			{
				add("id", regex("\\d{3,5}"));
				add("firstname", firstName());
				add("lastname", lastName());
				add("username", "${firstname}-${lastname}");
				add("email", "${username}@email.test");
				add("password", random("asasa", "awe2", "2332"));
				add("idnumber", regex("\\d{15}"));
				add("lang", random("pt_br", "en_us", "ch"));
				add("theme", random("aasas", "errr", "asa"));
				add("timezone", random("-3", "-2", "Sao_Paulo"));
				add("description", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("city", name());
				add("country", "${lang}");
				add("firstnamephonetic", regex("\\w{1}"));
				add("lastnamephonetic", regex("\\w{1}"));
				add("middlename", lastName());
				add("alternatename", name());
				add("preferences", has(2).of(UserPreference.class, "MoodleUserPreferences"));
				add("customfields", has(2).of(UserCustomField.class, "MoodleUserCustomFields"));


				add("fullname", "${firstname} ${lastname}");

				add("address", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("phone1", regex("(\\d{2})-(\\d{5})-(\\d{4})"));
				add("phone2", regex("(\\d{2})-(\\d{5})-(\\d{4})"));
				add("icq", regex("\\d{6}"));
				add("skype", name());
				add("yahoo", "${email}");
				add("aim", regex("\\d{6}"));
				add("msn", "${email}");
				add("department", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("institution", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("interests", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("firstaccess", randomDate("2011-04-15", "2016-11-07", new SimpleDateFormat("yyyy-MM-dd")));
				add("lastaccess", randomDate("2011-04-15", "2016-11-07", new SimpleDateFormat("yyyy-MM-dd")));
				add("auth", "manual");
				add("confirmed", true);
				add("description", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("descriptionformat", 1);
				add("url", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("profileimageurlsmall", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("profileimageurl", random("lorem ipsum", "ipsum lorem", "foo bar"));

			}
		});

		Fixture.of(UserEnrolledCourse.class).addTemplate("MoodleUserEnrolledCourse", 	new Rule() {
			{
				add("id", random(Long.class, range(12, 1234)));
				add("fullname", firstName());
				add("shortname", firstName());
			}
		});
		
		
		Fixture.of(UserPreference.class).addTemplate("MoodleUserPreferences", new Rule() {
			{
				add("value", random("maildigest", "editorformat", "datefrmt"));
				add("type", random("1", "4", "5"));
			}
		});

		Fixture.of(UserCustomField.class).addTemplate("MoodleUserCustomFields", new Rule() {
			{
				add("name", random("brithday", "borndate", "aniversário"));
				add("shortname", random("bd", "brn", "niver"));
				add("type", random(UserCustomField.CustomFieldType.class,
					CustomFieldType.CHECKBOX, CustomFieldType.DATETIME,
					CustomFieldType.MENU, CustomFieldType.TEXT, CustomFieldType.TEXTAREA
				));
				add("value", random("10101010", "100100010", "533122112"));
			}
		});

		Fixture.of(Criteria.class).addTemplate("MoodleRestGetUsersCriteria", new Rule() {
			{
				add("key", random("firstname", "lastname", "username", "idnumer", "id", "email"));
				add("value", "${key}");
			}

		});

		Fixture.of(String.class).addTemplate("MoodleRestGetUsersByFieldValues", new Rule() {
			{
				add("value", random(char.class, range(97, 122)));
			}
		});


		// Seriailized user
		// users%5B0%5D%5Bid%5D=355&users%5B0%5D%5Busername%5D=Rona-Auer&users%5B0%5D%5Bpassword%5D=awe2&users%5B0%5D%5Bfirstname%5D=Rona&users%5B0%5D%5Blastname%5D=Auer&users%5B0%5D%5Bemail%5D=Rona-Auer%40email.test&users%5B0%5D%5Bauth%5D=manual&users%5B0%5D%5Bidnumber%5D=024886573360022&users%5B0%5D%5Blang%5D=en_us&users%5B0%5D%5Bcalendartype%5D=gregorian&users%5B0%5D%5Btheme%5D=aasas&users%5B0%5D%5Btimezone%5D=Sao_Paulo&users%5B0%5D%5Bmailformat%5D=0&users%5B0%5D%5Bdescription%5D=foo+bar&users%5B0%5D%5Bcity%5D=Neil+Koch&users%5B0%5D%5Bcountry%5D=%24lang&users%5B0%5D%5Bfirstnamephonetic%5D=%2Fqwwsss%2F&users%5B0%5D%5Blastnamephonetic%5D=%2Fqwwsss%2F&users%5B0%5D%5Bmiddlename%5D=Yost&users%5B0%5D%5Balternatename%5D=Alva+Bins&users%5B0%5D%5Bcustomfields%5D%5B0%5D%5Btype%5D=brithday&users%5B0%5D%5Bcustomfields%5D%5B0%5D%5Bvalue%5D=533122112&users%5B0%5D%5Bcustomfields%5D%5B1%5D%5Btype%5D=anivers%C3%A1rio&users%5B0%5D%5Bcustomfields%5D%5B1%5D%5Bvalue%5D=100100010&users%5B0%5D%5Bpreferences%5D%5B0%5D%5Btype%5D=maildigest&users%5B0%5D%5Bpreferences%5D%5B0%5D%5Bvalue%5D=1&users%5B0%5D%5Bpreferences%5D%5B1%5D%5Btype%5D=editorformat&users%5B0%5D%5Bpreferences%5D%5B1%5D%5Bvalue%5D=4

		Fixture.of(this.getClass()).addTemplate("MoodleRestCreateUserResponse", new Rule() {
			{
				add("mdlUsers", has(3).of(MoodleUser.class, "MoodleRestCreateUserFunctionUser"));
			}
		});

		Fixture.of(this.getClass()).addTemplate("MoodleRestGetUsersResponse", new Rule() {
			{
				add("mdlUsers", has(3).of(MoodleUser.class, "MoodleRestGetUserFunctionUser"));
				// add("warnings", has(3).of(MoodleUser.Warning.class,
				// "MoodleUserWarnings"));
			}
		});

		Fixture.of(this.getClass()).addTemplate("MoodleRestGetUsersByFieldsResponse", new Rule() {
			{
				add("mdlUsers", has(3).of(MoodleUser.class, "MoodleRestGetUserFunctionUser"));
			}
		});


		Fixture.of(MoodleUser.class).addTemplate("MoodleRestCreateUserFunctionUser", new Rule() {
			{
				add("firstname", firstName());
				add("lastname", lastName());
				add("username", "${firstname}-${lastname}");
				add("email", "${username}@email.test");
				add("password", random("asasa", "awe2", "2332"));
				add("idnumber", regex("\\d{15}"));
				add("lang", random("pt_br", "en_us", "ch"));
				add("theme", random("aasas", "errr", "asa"));
				add("timezone", random("-3", "-2", "Sao_Paulo"));
				add("description", random("lorem ipsum", "ipsum lorem", "foo bar"));
				add("city", name());
				add("country", "${lang}");
				add("firstnamephonetic", regex("[asiuAkIqw]{1}"));
				add("lastnamephonetic", regex("[A-Z]{1}"));
				add("middlename", lastName());
				add("alternatename", name());
				add("preferences", has(2).of(UserPreference.class, "MoodleUserPreferences"));
				add("customfields", has(2).of(UserCustomField.class, "MoodleUserCustomFields"));


			}
		});

	}

	public static Document getGetUsersRespone(List<MoodleUser> mdlUsers, List<MoodleWarning> warnings) throws ParserConfigurationException, SAXException, IOException {

		StringBuffer usrResponse = new StringBuffer();
		usrResponse.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append("<RESPONSE>").append("<SINGLE>")
				.append("<KEY name=\"users\">").append("<MULTIPLE>");

		for (MoodleUser moodleUser : mdlUsers) {
			usrResponse.append("<SINGLE>").append("<KEY name=\"id\">").append("<VALUE>").append(moodleUser.getId()).append("</VALUE></KEY>")
			.append("<KEY name=\"username\">").append("<VALUE>").append(moodleUser.getUsername()).append("</VALUE></KEY>")
			.append("<KEY name=\"firstname\">").append("<VALUE>").append(moodleUser.getFirstname()).append("</VALUE></KEY>")
			.append("<KEY name=\"lastname\">").append("<VALUE>").append(moodleUser.getLastname()).append("</VALUE></KEY>")
			.append("<KEY name=\"fullname\">").append("<VALUE>").append(moodleUser.getFullname()).append("</VALUE></KEY>")
			.append("<KEY name=\"email\">").append("<VALUE>").append(moodleUser.getEmail()).append("</VALUE></KEY>")
			.append("<KEY name=\"address\">").append("<VALUE>").append(moodleUser.getAddress()).append("</VALUE></KEY>")
			.append("<KEY name=\"phone1\">").append("<VALUE>").append(moodleUser.getPhone1()).append("</VALUE></KEY>")
			.append("<KEY name=\"phone2\">").append("<VALUE>").append(moodleUser.getPhone2()).append("</VALUE></KEY>")
			.append("<KEY name=\"icq\">").append("<VALUE>").append(moodleUser.getIcq()).append("</VALUE></KEY>")
			.append("<KEY name=\"skype\">").append("<VALUE>").append(moodleUser.getSkype()).append("</VALUE></KEY>")
			.append("<KEY name=\"yahoo\">").append("<VALUE>").append(moodleUser.getYahoo()).append("</VALUE></KEY>")
			.append("<KEY name=\"aim\">").append("<VALUE>").append(moodleUser.getAim()).append("</VALUE></KEY>")
			.append("<KEY name=\"msn\">").append("<VALUE>").append(moodleUser.getMsn()).append("</VALUE></KEY>")
			.append("<KEY name=\"department\">").append("<VALUE>").append(moodleUser.getDepartment()).append("</VALUE></KEY>")
			.append("<KEY name=\"institution\">").append("<VALUE>").append(moodleUser.getInstitution()).append("</VALUE></KEY>")
			.append("<KEY name=\"idnumber\">").append("<VALUE>").append(moodleUser.getIdnumber()).append("</VALUE></KEY>")
			.append("<KEY name=\"interests\">").append("<VALUE>").append(moodleUser.getInterests()).append("</VALUE></KEY>")
			.append("<KEY name=\"firstaccess\">").append("<VALUE>").append(moodleUser.getFirstaccess().getTime() / 1000l).append("</VALUE></KEY>")
			.append("<KEY name=\"lastaccess\">").append("<VALUE>").append(moodleUser.getLastaccess().getTime() / 1000l).append("</VALUE></KEY>")
			.append("<KEY name=\"auth\">").append("<VALUE>").append(moodleUser.getAuth()).append("</VALUE></KEY>")
			.append("<KEY name=\"confirmed\">").append("<VALUE>").append(moodleUser.isConfirmed() ? "1" : "0").append("</VALUE></KEY>")
			.append("<KEY name=\"lang\">").append("<VALUE>").append(moodleUser.getLang()).append("</VALUE></KEY>")
			.append("<KEY name=\"calendartype\">").append("<VALUE>").append(moodleUser.getCalendartype()).append("</VALUE></KEY>")
			.append("<KEY name=\"theme\">").append("<VALUE>").append(moodleUser.getTheme()).append("</VALUE></KEY>")
			.append("<KEY name=\"timezone\">").append("<VALUE>").append(moodleUser.getTimezone()).append("</VALUE></KEY>")
			.append("<KEY name=\"mailformat\">").append("<VALUE>").append(moodleUser.getMailformat()).append("</VALUE></KEY>")
			.append("<KEY name=\"description\">").append("<VALUE>").append(moodleUser.getDescription()).append("</VALUE></KEY>")
			.append("<KEY name=\"descriptionformat\">").append("<VALUE>").append(moodleUser.getDescriptionformat()).append("</VALUE></KEY>")
			.append("<KEY name=\"city\">").append("<VALUE>").append(moodleUser.getCity()).append("</VALUE></KEY>")
			.append("<KEY name=\"url\">").append("<VALUE>").append(moodleUser.getUrl()).append("</VALUE></KEY>")
			.append("<KEY name=\"country\">").append("<VALUE>").append(moodleUser.getCountry()).append("</VALUE></KEY>")
			.append("<KEY name=\"profileimageurlsmall\">").append("<VALUE>").append(moodleUser.getProfileimageurlsmall()).append("</VALUE></KEY>")
			.append("<KEY name=\"profileimageurl\">").append("<VALUE>").append(moodleUser.getProfileimageurl()).append("</VALUE></KEY>")
			.append("<KEY name=\"firstnamephonetic\">").append("<VALUE>").append(moodleUser.getFirstnamephonetic()).append("</VALUE></KEY>")
			.append("<KEY name=\"lastnamephonetic\">").append("<VALUE>").append(moodleUser.getLastnamephonetic()).append("</VALUE></KEY>")
			.append("<KEY name=\"middlename\">").append("<VALUE>").append(moodleUser.getMiddlename()).append("</VALUE></KEY>")
			.append("<KEY name=\"alternatename\">").append("<VALUE>").append(moodleUser.getAlternatename()).append("</VALUE></KEY>")
			.append("<KEY name=\"customfields\">").append("<MULTIPLE>");

			Set<UserCustomField> customfields = new HashSet<>(Arrays.asList(moodleUser.getCustomfields()));
			for (UserCustomField customField : customfields) {
				usrResponse.append("<SINGLE>").append("<KEY name=\"type\">").append("<VALUE>")
						.append(customField.getType()).append("</VALUE></KEY>").append("<KEY name=\"value\">")
						.append("<VALUE>").append(customField.getValue()).append("</VALUE></KEY>")
						.append("<KEY name=\"name\">").append("<VALUE>").append(customField.getName())
						.append("</VALUE></KEY>").append("<KEY name=\"shortname\">").append("<VALUE>")
						.append(customField.getShortname()).append("</VALUE></KEY>").append("</SINGLE>");
			}
			usrResponse.append("</MULTIPLE></KEY>").append("<KEY name=\"preferences\">").append("<MULTIPLE>");

			Set<UserPreference> preferences = new HashSet<>(Arrays.asList(moodleUser.getPreferences()));
			for (UserPreference preference : preferences) {
				usrResponse.append("<SINGLE>").append("<KEY name=\"name\">").append("<VALUE>")
						.append(preference.getName()).append("</VALUE></KEY>").append("<KEY name=\"value\">")
						.append("<VALUE>").append(preference.getValue()).append("</VALUE></KEY>").append("</SINGLE>");
			}
			usrResponse.append("</MULTIPLE></KEY>").append("</SINGLE>");
		}

		usrResponse.append("</MULTIPLE></KEY>");
		if (warnings!=null && !warnings.isEmpty()) {
			usrResponse.append("<KEY name=\"warnings\">").append("<MULTIPLE>");
			for (MoodleWarning warning : warnings) {
				usrResponse.append("<SINGLE>").append("<KEY name=\"item\">").append("<VALUE>").append(warning.getItem())
						.append("</VALUE></KEY>").append("<KEY name=\"itemid\">").append("<VALUE>")
						.append(warning.getItemId()).append("</VALUE></KEY>").append("<KEY name=\"warningcode\">")
						.append("<VALUE>").append(warning.getWarningCode()).append("</VALUE></KEY>")
						.append("<KEY name=\"message\">").append("<VALUE>").append(warning.getMessage())
						.append("</VALUE></KEY>").append("</SINGLE>");
			}
			usrResponse.append("</MULTIPLE></KEY>");
		}
		usrResponse.append("</SINGLE></RESPONSE>");

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();

		return builder.parse(new ByteArrayInputStream(usrResponse.toString().getBytes()));
	}

	/**
	 * @return the respone
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static Document getAddUsersRespone(Set<MoodleUser> mdlUsers) throws ParserConfigurationException, SAXException, IOException {
		// <?xml version="1.0" encoding="UTF-8" ?>
		// <RESPONSE>
		// <MULTIPLE>
		// <SINGLE>
		// <KEY name="id">
		// <VALUE>int</VALUE>
		// </KEY>
		// <KEY name="username">
		// <VALUE>string</VALUE>
		// </KEY>
		// </SINGLE>
		// </MULTIPLE>
		// </RESPONSE>
		StringBuffer usrResponse = new StringBuffer();
		usrResponse.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append("<RESPONSE>").append("<MULTIPLE>");

		int index = 1;
		for (MoodleUser moodleUser : mdlUsers) {
			usrResponse.append("<SINGLE>").append("<KEY name=\"id\">").append("<VALUE>").append(index)
					.append("</VALUE></KEY>").append("<KEY name=\"username\">").append("<VALUE>")
					.append(moodleUser.getUsername()).append("</VALUE></KEY>").append("</SINGLE>");
			index++;
		}
		usrResponse.append("</MULTIPLE>").append("</RESPONSE>");

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();

		return builder.parse(new ByteArrayInputStream(usrResponse.toString().getBytes()));
	}

	/**
	 * @return the mdlUsers
	 */
	public Set<MoodleUser> getMdlUsers() {
		return mdlUsers;
	}

	/**
	 * @param mdlUsers
	 *            the mdlUsers to set
	 */
	public void setMdlUsers(Set<MoodleUser> mdlUsers) {
		this.mdlUsers = mdlUsers;
	}

	

	

	public Set<String> getFieldValues() {
		Set<String> ids = new HashSet<String>();
		for (MoodleUser moodleUser : mdlUsers) {
			ids.add(String.valueOf(moodleUser.getId()));
		}

		return ids;
	}

	public static Document getGetUsersByIdRespone(Collection<MoodleUser> mdlUsers) throws ParserConfigurationException, SAXException, IOException {
		StringBuffer usrResponse = new StringBuffer();
		usrResponse.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append("<RESPONSE>").append("<MULTIPLE>");

		for (MoodleUser moodleUser : mdlUsers) {
			usrResponse.append("<SINGLE>").append("<KEY name=\"id\">").append("<VALUE>").append(moodleUser.getId()).append("</VALUE></KEY>")
			.append("<KEY name=\"username\">").append("<VALUE>").append(moodleUser.getUsername()).append("</VALUE></KEY>")
			.append("<KEY name=\"firstname\">").append("<VALUE>").append(moodleUser.getFirstname()).append("</VALUE></KEY>")
			.append("<KEY name=\"lastname\">").append("<VALUE>").append(moodleUser.getLastname()).append("</VALUE></KEY>")
			.append("<KEY name=\"fullname\">").append("<VALUE>").append(moodleUser.getFullname()).append("</VALUE></KEY>")
			.append("<KEY name=\"email\">").append("<VALUE>").append(moodleUser.getEmail()).append("</VALUE></KEY>")
			.append("<KEY name=\"address\">").append("<VALUE>").append(moodleUser.getAddress()).append("</VALUE></KEY>")
			.append("<KEY name=\"phone1\">").append("<VALUE>").append(moodleUser.getPhone1()).append("</VALUE></KEY>")
			.append("<KEY name=\"phone2\">").append("<VALUE>").append(moodleUser.getPhone2()).append("</VALUE></KEY>")
			.append("<KEY name=\"icq\">").append("<VALUE>").append(moodleUser.getIcq()).append("</VALUE></KEY>")
			.append("<KEY name=\"skype\">").append("<VALUE>").append(moodleUser.getSkype()).append("</VALUE></KEY>")
			.append("<KEY name=\"yahoo\">").append("<VALUE>").append(moodleUser.getYahoo()).append("</VALUE></KEY>")
			.append("<KEY name=\"aim\">").append("<VALUE>").append(moodleUser.getAim()).append("</VALUE></KEY>")
			.append("<KEY name=\"msn\">").append("<VALUE>").append(moodleUser.getMsn()).append("</VALUE></KEY>")
			.append("<KEY name=\"department\">").append("<VALUE>").append(moodleUser.getDepartment()).append("</VALUE></KEY>")
			.append("<KEY name=\"institution\">").append("<VALUE>").append(moodleUser.getInstitution()).append("</VALUE></KEY>")
			.append("<KEY name=\"idnumber\">").append("<VALUE>").append(moodleUser.getIdnumber()).append("</VALUE></KEY>")
			.append("<KEY name=\"interests\">").append("<VALUE>").append(moodleUser.getInterests()).append("</VALUE></KEY>")
			.append("<KEY name=\"firstaccess\">").append("<VALUE>").append(moodleUser.getFirstaccess().getTime() / 1000l).append("</VALUE></KEY>")
			.append("<KEY name=\"lastaccess\">").append("<VALUE>").append(moodleUser.getLastaccess().getTime() / 1000l).append("</VALUE></KEY>")
			.append("<KEY name=\"auth\">").append("<VALUE>").append(moodleUser.getAuth()).append("</VALUE></KEY>")
			.append("<KEY name=\"confirmed\">").append("<VALUE>").append(moodleUser.isConfirmed() ? "1" : "0").append("</VALUE></KEY>")
			.append("<KEY name=\"lang\">").append("<VALUE>").append(moodleUser.getLang()).append("</VALUE></KEY>")
			.append("<KEY name=\"calendartype\">").append("<VALUE>").append(moodleUser.getCalendartype()).append("</VALUE></KEY>")
			.append("<KEY name=\"theme\">").append("<VALUE>").append(moodleUser.getTheme()).append("</VALUE></KEY>")
			.append("<KEY name=\"timezone\">").append("<VALUE>").append(moodleUser.getTimezone()).append("</VALUE></KEY>")
			.append("<KEY name=\"mailformat\">").append("<VALUE>").append(moodleUser.getMailformat()).append("</VALUE></KEY>")
			.append("<KEY name=\"description\">").append("<VALUE>").append(moodleUser.getDescription()).append("</VALUE></KEY>")
			.append("<KEY name=\"descriptionformat\">").append("<VALUE>").append(moodleUser.getDescriptionformat()).append("</VALUE></KEY>")
			.append("<KEY name=\"city\">").append("<VALUE>").append(moodleUser.getCity()).append("</VALUE></KEY>")
			.append("<KEY name=\"url\">").append("<VALUE>").append(moodleUser.getUrl()).append("</VALUE></KEY>")
			.append("<KEY name=\"country\">").append("<VALUE>").append(moodleUser.getCountry()).append("</VALUE></KEY>")
			.append("<KEY name=\"profileimageurlsmall\">").append("<VALUE>").append(moodleUser.getProfileimageurlsmall()).append("</VALUE></KEY>")
			.append("<KEY name=\"profileimageurl\">").append("<VALUE>").append(moodleUser.getProfileimageurl()).append("</VALUE></KEY>")
			.append("<KEY name=\"customfields\">").append("<MULTIPLE>");

			Set<UserCustomField> customfields = new HashSet<>(Arrays.asList(moodleUser.getCustomfields()));
			for (UserCustomField customField : customfields) {
				usrResponse.append("<SINGLE>").append("<KEY name=\"type\">").append("<VALUE>")
						.append(customField.getType()).append("</VALUE></KEY>").append("<KEY name=\"value\">")
						.append("<VALUE>").append(customField.getValue()).append("</VALUE></KEY>")
						.append("<KEY name=\"name\">").append("<VALUE>").append(customField.getName())
						.append("</VALUE></KEY>").append("<KEY name=\"shortname\">").append("<VALUE>")
						.append(customField.getShortname()).append("</VALUE></KEY>").append("</SINGLE>");
			}
			usrResponse.append("</MULTIPLE></KEY>").append("<KEY name=\"preferences\">").append("<MULTIPLE>");

			Set<UserPreference> preferences = new HashSet<>(Arrays.asList(moodleUser.getPreferences()));
			for (UserPreference preference : preferences) {
				usrResponse.append("<SINGLE>").append("<KEY name=\"name\">").append("<VALUE>")
						.append(preference.getName()).append("</VALUE></KEY>").append("<KEY name=\"value\">")
						.append("<VALUE>").append(preference.getValue()).append("</VALUE></KEY>").append("</SINGLE>");
			}
			usrResponse.append("</MULTIPLE></KEY>").append("<KEY name=\"enrolledcourses\">").append("<MULTIPLE>");

			Set<UserEnrolledCourse> enrollments = new HashSet<>(Arrays.asList(moodleUser.getEnrolledCourses()));
			for (UserEnrolledCourse enrollment : enrollments) {
				usrResponse.append(TestTools.entityToXmlResponse(enrollment));
			}
			usrResponse.append("</MULTIPLE></KEY>").append("</SINGLE>");
		}

		usrResponse.append("</MULTIPLE></RESPONSE>");

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();

		return builder.parse(new ByteArrayInputStream(usrResponse.toString().getBytes()));
	}

	public static Document getGetUsersByFieldsRespone(Collection<MoodleUser> mdlUsers) throws ParserConfigurationException, SAXException, IOException {
		StringBuffer usrResponse = new StringBuffer();
		usrResponse.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append("<RESPONSE>").append("<MULTIPLE>");

		for (MoodleUser moodleUser : mdlUsers) {
			usrResponse.append("<SINGLE>").append("<KEY name=\"id\">").append("<VALUE>").append(moodleUser.getId()).append("</VALUE></KEY>")
			.append("<KEY name=\"username\">").append("<VALUE>").append(moodleUser.getUsername()).append("</VALUE></KEY>")
			.append("<KEY name=\"firstname\">").append("<VALUE>").append(moodleUser.getFirstname()).append("</VALUE></KEY>")
			.append("<KEY name=\"lastname\">").append("<VALUE>").append(moodleUser.getLastname()).append("</VALUE></KEY>")
			.append("<KEY name=\"fullname\">").append("<VALUE>").append(moodleUser.getFullname()).append("</VALUE></KEY>")
			.append("<KEY name=\"email\">").append("<VALUE>").append(moodleUser.getEmail()).append("</VALUE></KEY>")
			.append("<KEY name=\"address\">").append("<VALUE>").append(moodleUser.getAddress()).append("</VALUE></KEY>")
			.append("<KEY name=\"phone1\">").append("<VALUE>").append(moodleUser.getPhone1()).append("</VALUE></KEY>")
			.append("<KEY name=\"phone2\">").append("<VALUE>").append(moodleUser.getPhone2()).append("</VALUE></KEY>")
			.append("<KEY name=\"icq\">").append("<VALUE>").append(moodleUser.getIcq()).append("</VALUE></KEY>")
			.append("<KEY name=\"skype\">").append("<VALUE>").append(moodleUser.getSkype()).append("</VALUE></KEY>")
			.append("<KEY name=\"yahoo\">").append("<VALUE>").append(moodleUser.getYahoo()).append("</VALUE></KEY>")
			.append("<KEY name=\"aim\">").append("<VALUE>").append(moodleUser.getAim()).append("</VALUE></KEY>")
			.append("<KEY name=\"msn\">").append("<VALUE>").append(moodleUser.getMsn()).append("</VALUE></KEY>")
			.append("<KEY name=\"department\">").append("<VALUE>").append(moodleUser.getDepartment()).append("</VALUE></KEY>")
			.append("<KEY name=\"institution\">").append("<VALUE>").append(moodleUser.getInstitution()).append("</VALUE></KEY>")
			.append("<KEY name=\"idnumber\">").append("<VALUE>").append(moodleUser.getIdnumber()).append("</VALUE></KEY>")
			.append("<KEY name=\"interests\">").append("<VALUE>").append(moodleUser.getInterests()).append("</VALUE></KEY>")
			.append("<KEY name=\"firstaccess\">").append("<VALUE>").append(moodleUser.getFirstaccess().getTime() / 1000l).append("</VALUE></KEY>")
			.append("<KEY name=\"lastaccess\">").append("<VALUE>").append(moodleUser.getLastaccess().getTime() / 1000l).append("</VALUE></KEY>")
			.append("<KEY name=\"auth\">").append("<VALUE>").append(moodleUser.getAuth()).append("</VALUE></KEY>")
			.append("<KEY name=\"confirmed\">").append("<VALUE>").append(moodleUser.isConfirmed() ? "1" : "0").append("</VALUE></KEY>")
			.append("<KEY name=\"lang\">").append("<VALUE>").append(moodleUser.getLang()).append("</VALUE></KEY>")
			.append("<KEY name=\"calendartype\">").append("<VALUE>").append(moodleUser.getCalendartype()).append("</VALUE></KEY>")
			.append("<KEY name=\"theme\">").append("<VALUE>").append(moodleUser.getTheme()).append("</VALUE></KEY>")
			.append("<KEY name=\"timezone\">").append("<VALUE>").append(moodleUser.getTimezone()).append("</VALUE></KEY>")
			.append("<KEY name=\"mailformat\">").append("<VALUE>").append(moodleUser.getMailformat()).append("</VALUE></KEY>")
			.append("<KEY name=\"description\">").append("<VALUE>").append(moodleUser.getDescription()).append("</VALUE></KEY>")
			.append("<KEY name=\"descriptionformat\">").append("<VALUE>").append(moodleUser.getDescriptionformat()).append("</VALUE></KEY>")
			.append("<KEY name=\"city\">").append("<VALUE>").append(moodleUser.getCity()).append("</VALUE></KEY>")
			.append("<KEY name=\"url\">").append("<VALUE>").append(moodleUser.getUrl()).append("</VALUE></KEY>")
			.append("<KEY name=\"country\">").append("<VALUE>").append(moodleUser.getCountry()).append("</VALUE></KEY>")
			.append("<KEY name=\"profileimageurlsmall\">").append("<VALUE>").append(moodleUser.getProfileimageurlsmall()).append("</VALUE></KEY>")
			.append("<KEY name=\"profileimageurl\">").append("<VALUE>").append(moodleUser.getProfileimageurl()).append("</VALUE></KEY>")
			.append("<KEY name=\"customfields\">").append("<MULTIPLE>");

			Set<UserCustomField> customfields = new HashSet<>(Arrays.asList(moodleUser.getCustomfields()));
			for (UserCustomField customField : customfields) {
				usrResponse.append("<SINGLE>").append("<KEY name=\"type\">").append("<VALUE>")
						.append(customField.getType()).append("</VALUE></KEY>").append("<KEY name=\"value\">")
						.append("<VALUE>").append(customField.getValue()).append("</VALUE></KEY>")
						.append("<KEY name=\"name\">").append("<VALUE>").append(customField.getName())
						.append("</VALUE></KEY>").append("<KEY name=\"shortname\">").append("<VALUE>")
						.append(customField.getShortname()).append("</VALUE></KEY>").append("</SINGLE>");
			}
			usrResponse.append("</MULTIPLE></KEY>").append("<KEY name=\"preferences\">").append("<MULTIPLE>");

			Set<UserPreference> preferences = new HashSet<>(Arrays.asList(moodleUser.getPreferences()));
			for (UserPreference preference : preferences) {
				usrResponse.append("<SINGLE>").append("<KEY name=\"name\">").append("<VALUE>")
						.append(preference.getName()).append("</VALUE></KEY>").append("<KEY name=\"value\">")
						.append("<VALUE>").append(preference.getValue()).append("</VALUE></KEY>").append("</SINGLE>");
			}
			
			usrResponse.append("</MULTIPLE></KEY>").append("</SINGLE>");
		}

		usrResponse.append("</MULTIPLE></RESPONSE>");

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();

		return builder.parse(new ByteArrayInputStream(usrResponse.toString().getBytes()));
	}

	public Set<Long> getIdsValues() {
		Set<Long> ids= new HashSet<Long>();
		for (MoodleUser moodleUser : mdlUsers) {
			ids.add(moodleUser.getId());
		}
		return ids;
	}

}