package ml.jmoodle.functions.rest.tools;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashSet;
import java.util.Set;

import ml.jmoodle.commons.MoodleUser;
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

		// Extra Param for create
		// users[0][createpassword]= int

		//Removing the lats char (&)
		return returnData.substring(0, returnData.length() - 1);
	}

	

}
