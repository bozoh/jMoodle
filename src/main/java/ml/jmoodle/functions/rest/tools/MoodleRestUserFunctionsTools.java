package ml.jmoodle.functions.rest.tools;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.commons.MoodleUser.CustomFieldType;
import ml.jmoodle.functions.rest.MoodleRestGetUsers;
import ml.jmoodle.functions.rest.MoodleRestGetUsersByFields.Field;
//import ml.jmoodle.functions.rest.MoodleRestGetUsers.Criteria;
import ml.jmoodle.tools.MoodleTools;

public class MoodleRestUserFunctionsTools {

	public String serializeUser(MoodleUser moodleUser) throws UnsupportedEncodingException {

		return serliazeUsers(new MoodleUser[] { moodleUser });
	}

	public String serliazeUsers(Set<MoodleUser> moodleUsers) throws UnsupportedEncodingException {
		return serliazeUsers(moodleUsers.toArray(new MoodleUser[moodleUsers.size()]));
	}

	private String serliazeUsers(MoodleUser[] moodleUsers) throws UnsupportedEncodingException {
		StringBuilder returnData = new StringBuilder();
		for (int i = 0; i < moodleUsers.length; i++) {
			if (moodleUsers[i].getId() != null && moodleUsers[i].getId().longValue() != 0l) {
				returnData.append(MoodleTools.encode("users[" + i + "][id]")).append("=")
						.append(MoodleTools.encode(String.valueOf(moodleUsers[i].getId()))).append("&");
			}
			if (moodleUsers[i].getUsername() != null && !moodleUsers[i].getUsername().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][username]")).append("=")
						.append(MoodleTools.encode(String.valueOf(moodleUsers[i].getUsername()))).append("&");
			}
			if (moodleUsers[i].getPassword() != null && !moodleUsers[i].getPassword().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][password]")).append("=")
						.append(MoodleTools.encode(String.valueOf(moodleUsers[i].getPassword()))).append("&");
			}
			if (moodleUsers[i].getFirstname() != null && !moodleUsers[i].getFirstname().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][firstname]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getFirstname())).append("&");
			}

			if (moodleUsers[i].getLastname() != null && !moodleUsers[i].getLastname().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][lastname]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getLastname())).append("&");
			}

			if (moodleUsers[i].getEmail() != null && !moodleUsers[i].getEmail().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][email]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getEmail())).append("&");
			}

			if (moodleUsers[i].getAuth() != null && !moodleUsers[i].getAuth().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][auth]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getAuth())).append("&");
			}

			if (moodleUsers[i].getIdnumber() != null && !moodleUsers[i].getIdnumber().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][idnumber]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getIdnumber())).append("&");
			}

			if (moodleUsers[i].getLang() != null && !moodleUsers[i].getLang().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][lang]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getLang())).append("&");
			}

			if (moodleUsers[i].getCalendartype() != null && !moodleUsers[i].getCalendartype().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][calendartype]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getCalendartype())).append("&");
			}

			if (moodleUsers[i].getTheme() != null && !moodleUsers[i].getTheme().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][theme]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getTheme())).append("&");
			}
			if (moodleUsers[i].getTimezone() != null && !moodleUsers[i].getTimezone().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][timezone]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getTimezone())).append("&");
			}

			returnData.append(MoodleTools.encode("users[" + i + "][mailformat]")).append("=")
					.append(MoodleTools.encode(String.valueOf(moodleUsers[i].getMailformat()))).append("&");

			if (moodleUsers[i].getDescription() != null && !moodleUsers[i].getDescription().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][description]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getDescription())).append("&");
			}

			if (moodleUsers[i].getCity() != null && !moodleUsers[i].getCity().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][city]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getCity())).append("&");
			}

			if (moodleUsers[i].getCountry() != null && !moodleUsers[i].getCountry().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][country]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getCountry())).append("&");
			}

			if (moodleUsers[i].getFirstnamephonetic() != null
					&& !moodleUsers[i].getFirstnamephonetic().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][firstnamephonetic]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getFirstnamephonetic())).append("&");
			}

			if (moodleUsers[i].getLastnamephonetic() != null
					&& !moodleUsers[i].getLastnamephonetic().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][lastnamephonetic]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getLastnamephonetic())).append("&");
			}

			if (moodleUsers[i].getMiddlename() != null && !moodleUsers[i].getMiddlename().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][middlename]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getMiddlename())).append("&");
			}

			if (moodleUsers[i].getAlternatename() != null && !moodleUsers[i].getAlternatename().trim().isEmpty()) {
				returnData.append(MoodleTools.encode("users[" + i + "][alternatename]")).append("=")
						.append(MoodleTools.encode(moodleUsers[i].getAlternatename())).append("&");
			}

			// Extra Param for create, not part of MoodleUser Entity
			if (moodleUsers[i].isCreatePassword().booleanValue()) {
				returnData.append(MoodleTools.encode("users[" + i + "][createpassword]")).append("=")
						.append(MoodleTools.encode("1")).append("&");
			}

			Set<MoodleUser.CustomField> customFields = moodleUsers[i].getCustomfields();
			if (customFields != null && !customFields.isEmpty()) {
				MoodleUser.CustomField[] fields = customFields.toArray(new MoodleUser.CustomField[customFields.size()]);
				for (int j = 0; j < fields.length; j++) {
					returnData.append(MoodleTools.encode("users[" + i + "][customfields][" + j + "][type]")).append("=")
							.append(MoodleTools.encode(fields[j].getName())).append("&")
							.append(MoodleTools.encode("users[" + i + "][customfields][" + j + "][value]")).append("=")
							.append(MoodleTools.encode(fields[j].getValue())).append("&");
				}
			}

			Set<MoodleUser.Preference> preferences = moodleUsers[i].getPreferences();
			if (preferences != null && !preferences.isEmpty()) {
				MoodleUser.Preference[] prefs = preferences.toArray(new MoodleUser.Preference[preferences.size()]);
				for (int j = 0; j < prefs.length; j++) {
					returnData.append(MoodleTools.encode("users[" + i + "][preferences][" + j + "][type]")).append("=")
							.append(MoodleTools.encode(prefs[j].getName())).append("&")
							.append(MoodleTools.encode("users[" + i + "][preferences][" + j + "][value]")).append("=")
							.append(MoodleTools.encode(prefs[j].getValue())).append("&");
				}
			}

		}

		// Removing the lats char (&)
		return returnData.substring(0, returnData.length() - 1);
	}

	public Set<MoodleUser> unSerializeUsers(NodeList nodeList) throws XPathExpressionException {
		Set<MoodleUser> result = new LinkedHashSet<MoodleUser>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node singleNode = nodeList.item(i);
			Map<String, Object> singleValuesMap = MoodleRestFunctionTools.getSingleAttributes(singleNode);
			result.add(map2user(singleValuesMap));
		}

		// TODO Process warnings, but not in this method
		return result;
	}

	@SuppressWarnings("unchecked")
	private MoodleUser map2user(Map<String, Object> valuesMap) {
		if (valuesMap == null || valuesMap.isEmpty())
			return null;

		MoodleUser user = new MoodleUser();
		// Must Have attributes
		user.setId(Long.valueOf((String) valuesMap.get("id")));
		user.setUsername((String) valuesMap.get("username"));
		user.setFirstname((String) valuesMap.get("firstname"));
		user.setLastname((String) valuesMap.get("lastname"));
		user.setEmail((String) valuesMap.get("email"));

		// Optional Values
		if (valuesMap.containsKey("address"))
			user.setAddress((String) valuesMap.get("address"));

		if (valuesMap.containsKey("aim"))
			user.setAim((String) valuesMap.get("aim"));

		if (valuesMap.containsKey("alternatename"))
			user.setAlternatename((String) valuesMap.get("alternatename"));

		if (valuesMap.containsKey("auth"))
			user.setAuth((String) valuesMap.get("auth"));
		else
			user.setAuth("manual");

		if (valuesMap.containsKey("calendartype"))
			user.setCalendartype((String) valuesMap.get("calendartype"));

		if (valuesMap.containsKey("city"))
			user.setCity((String) valuesMap.get("city"));
		if (valuesMap.containsKey("confirmed"))
			user.setConfirmed(((String) valuesMap.get("confirmed")).equals("1"));
		else
			user.setConfirmed(new Boolean(true));

		if (valuesMap.containsKey("country"))
			user.setCountry((String) valuesMap.get("country"));
		if (valuesMap.containsKey("department"))
			user.setDepartment((String) valuesMap.get("department"));
		if (valuesMap.containsKey("description"))
			user.setDescription((String) valuesMap.get("description"));
		if (valuesMap.containsKey("descriptionformat"))
			user.setDescriptionformat(Integer.parseInt((String) valuesMap.get("descriptionformat")));
		if (valuesMap.containsKey("firstaccess"))
			user.setFirstaccess(Long.valueOf((String) valuesMap.get("firstaccess")) * 1000l);

		if (valuesMap.containsKey("firstnamephonetic"))
			user.setFirstnamephonetic((String) valuesMap.get("firstnamephonetic"));
		if (valuesMap.containsKey("fullname"))
			user.setFullname((String) valuesMap.get("fullname"));
		else
			user.setFullname(user.getFirstname() + " " + user.getLastname());

		if (valuesMap.containsKey("icq"))
			user.setIcq((String) valuesMap.get("icq"));
		if (valuesMap.containsKey("idnumber"))
			user.setIdnumber((String) valuesMap.get("idnumber"));
		if (valuesMap.containsKey("institution"))
			user.setInstitution((String) valuesMap.get("institution"));
		if (valuesMap.containsKey("interests"))
			user.setInterests((String) valuesMap.get("interests"));
		if (valuesMap.containsKey("lang"))
			user.setLang((String) valuesMap.get("lang"));
		else
			user.setLang("en");

		if (valuesMap.containsKey("lastaccess"))
			user.setLastaccess(Long.valueOf((String) valuesMap.get("lastaccess")) * 1000l);
		if (valuesMap.containsKey("lastnamephonetic"))
			user.setLastnamephonetic((String) valuesMap.get("lastnamephonetic"));
		if (valuesMap.containsKey("mailformat"))
			user.setMailformat(Integer.parseInt((String) valuesMap.get("mailformat")));
		if (valuesMap.containsKey("middlename"))
			user.setMiddlename((String) valuesMap.get("middlename"));
		if (valuesMap.containsKey("msn"))
			user.setMsn((String) valuesMap.get("msn"));
		if (valuesMap.containsKey("password"))
			user.setPassword((String) valuesMap.get("password"));
		if (valuesMap.containsKey("phone1"))
			user.setPhone1((String) valuesMap.get("phone1"));
		if (valuesMap.containsKey("phone2"))
			user.setPhone2((String) valuesMap.get("phone2"));
		if (valuesMap.containsKey("profileimageurl"))
			user.setProfileimageurl((String) valuesMap.get("profileimageurl"));
		if (valuesMap.containsKey("profileimageurlsmall"))
			user.setProfileimageurlsmall((String) valuesMap.get("profileimageurlsmall"));
		if (valuesMap.containsKey("skype"))
			user.setSkype((String) valuesMap.get("skype"));
		if (valuesMap.containsKey("theme"))
			user.setTheme((String) valuesMap.get("theme"));
		if (valuesMap.containsKey("timezone"))
			user.setTimezone((String) valuesMap.get("timezone"));
		if (valuesMap.containsKey("url"))
			user.setUrl((String) valuesMap.get("url"));
		if (valuesMap.containsKey("yahoo"))
			user.setYahoo((String) valuesMap.get("yahoo"));

		/// The Multiple values
		if (valuesMap.containsKey("preferences")) {
			Set<Map<String, Object>> preferences = (Set<Map<String, Object>>) valuesMap.get("preferences");
			for (Map<String, Object> preference : preferences) {
				user.addPreferences((String) preference.get("name"), (String) preference.get("value"));
			}
		}
		if (valuesMap.containsKey("customfields")) {
			Set<Map<String, Object>> customfields = (Set<Map<String, Object>>) valuesMap.get("customfields");
			for (Map<String, Object> customfield : customfields) {
				String type = (String) customfield.get("type");
				user.addCustomfields(CustomFieldType.valueOf(type.toUpperCase()), (String) customfield.get("value"),
						(String) customfield.get("name"), (String) customfield.get("shortname"));
			}
		}

		return user;
	}

	public String serliazeCriterias(Set<MoodleRestGetUsers.Criteria> criterias) throws UnsupportedEncodingException {
		return serliazeCriterias(criterias.toArray(new MoodleRestGetUsers.Criteria[criterias.size()]));

	}

	// criteria[0][key]= string
	// criteria[0][value]= string
	private String serliazeCriterias(MoodleRestGetUsers.Criteria[] criterias) throws UnsupportedEncodingException {
		StringBuilder returnData = new StringBuilder();
		for (int i = 0; i < criterias.length; i++) {
			returnData.append(MoodleTools.encode("criteria[")).append(i).append(MoodleTools.encode("][key]"))
					.append("=").append(MoodleTools.encode(criterias[i].getName())).append("&")
					.append(MoodleTools.encode("criteria[")).append(i).append(MoodleTools.encode("][value]"))
					.append("=").append(MoodleTools.encode(criterias[i].getValue())).append("&");
		}

		return returnData.substring(0, returnData.length() - 1);
	}

	


	public String serliazeFields(Field field, Set<String> values) throws UnsupportedEncodingException {
		return serliazeFields(field, values.toArray(new String[values.size()]));

	}

	private String serliazeFields(Field field, String[] values) throws UnsupportedEncodingException {
		StringBuilder returnData = new StringBuilder();
		returnData.append(MoodleTools.encode("field")).append("=").append(MoodleTools.encode(field.toString()))
				.append("&");
		for (int j = 0; j < values.length; j++) {
			returnData.append(MoodleTools.encode("values[")).append(j).append(MoodleTools.encode("]")).append("=")
					.append(MoodleTools.encode(values[j])).append("&");
		}
		return returnData.substring(0, returnData.length() - 1);
	}

	public String serliazeUsersIds(Set<Long> ids) throws UnsupportedEncodingException {
		return serliazeUsersIds(ids.toArray(new Long[ids.size()]));

	}
	
	public String serliazeMoodleUsersIds(Set<MoodleUser> users) throws UnsupportedEncodingException {
		Long[] ids = new Long[users.size()];
		int i = 0;

		for (MoodleUser moodleUser : users) {
			ids[i] = moodleUser.getId();
			i++;
		}
		return serliazeUsersIds(ids);
	}

	private String serliazeUsersIds(Long[] ids) throws UnsupportedEncodingException {
		// userids[0]=
		StringBuilder returnData = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			returnData.append(MoodleTools.encode("userids[")).append(i).append(MoodleTools.encode("]")).append("=")
					.append(ids[i].longValue()).append("&");
		}
		return returnData.substring(0, returnData.length() - 1);
	}

}
