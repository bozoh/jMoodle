/*
 *  Copyright (C) 2012 Bill Antonia
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package ml.jmoodle.commons;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import ml.jmoodle.annotations.MoodleConverter;
import ml.jmoodle.commons.UserCustomField.CustomFieldType;

/**
 * 
 * @author Carlos Alexandre S. da Fonseca
 * 
 *
 */
@MoodleConverter
public class MoodleUser implements Serializable, Comparable<MoodleUser> {

	private static final long serialVersionUID = 8465905725413266458L;

	/**
	 * Defaulsts values
	 */
	public static final boolean EMAIL_FORMAT_NONE = false;
	public static final boolean EMAIL_FORMAT_HTML = true;

	public enum EmailFormat {
		// (1 = HTML, 0 = PLAIN )

		PLAIN(0), HTML(1);

		int value;

		private EmailFormat(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private Long id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String fullname;
	private String firstnamephonetic;
	private String lastnamephonetic;
	private String middlename;
	private String alternatename;
	private String email;
	private String address;
	private String phone1;
	private String phone2;
	private String icq;
	private String skype;
	private String yahoo;
	private String aim;
	private String msn;
	private String department;
	private String institution;
	private String idnumber;
	private String interests;
	private Long firstaccess;
	private Long lastaccess;
	private String auth;
	private boolean confirmed;
	private String lang;
	private String calendartype;
	private String theme;
	private String timezone;
	private int mailformat;
	private String description;
	private int descriptionformat;
	private String city;
	private String url;
	private String country;
	private String profileimageurlsmall;
	private String profileimageurl;

	private Set<UserCustomField> customfields;
	private Set<UserPreference> preferences;
	private Set<MoodleCourse> enrolledcourses;
	private Set<MoodleGroup> groups;
	private Set<MoodleRole> roles;

	// This is not part of moodle entity, but to get some WS functions
	// easier to implement
	private Boolean createpassword;
	

	public MoodleUser() {
		
		// Defualt values
		this.createpassword = new Boolean(false);
		this.calendartype = "gregorian";
		this.lang = "en";
		this.auth = "manual";
	}

	public MoodleUser(Long id) {
		this();
		this.id = id;

	}

	public MoodleUser(String username, String password, String firstname, String lastname, String email) {
		this();
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * @param fullname
	 *            the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * @return the firstnamephonetic
	 */
	public String getFirstnamephonetic() {
		return firstnamephonetic;
	}

	/**
	 * @param firstnamephonetic
	 *            the firstnamephonetic to set
	 */
	public void setFirstnamephonetic(String firstnamephonetic) {
		this.firstnamephonetic = firstnamephonetic;
	}

	/**
	 * @return the lastnamephonetic
	 */
	public String getLastnamephonetic() {
		return lastnamephonetic;
	}

	/**
	 * @param lastnamephonetic
	 *            the lastnamephonetic to set
	 */
	public void setLastnamephonetic(String lastnamephonetic) {
		this.lastnamephonetic = lastnamephonetic;
	}

	/**
	 * @return the middlename
	 */
	public String getMiddlename() {
		return middlename;
	}

	/**
	 * @param middlename
	 *            the middlename to set
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	/**
	 * @return the alternatename
	 */
	public String getAlternatename() {
		return alternatename;
	}

	/**
	 * @param alternatename
	 *            the alternatename to set
	 */
	public void setAlternatename(String alternatename) {
		this.alternatename = alternatename;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the phone1
	 */
	public String getPhone1() {
		return phone1;
	}

	/**
	 * @param phone1
	 *            the phone1 to set
	 */
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	/**
	 * @return the phone2
	 */
	public String getPhone2() {
		return phone2;
	}

	/**
	 * @param phone2
	 *            the phone2 to set
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	/**
	 * @return the icq
	 */
	public String getIcq() {
		return icq;
	}

	/**
	 * @param icq
	 *            the icq to set
	 */
	public void setIcq(String icq) {
		this.icq = icq;
	}

	/**
	 * @return the skype
	 */
	public String getSkype() {
		return skype;
	}

	/**
	 * @param skype
	 *            the skype to set
	 */
	public void setSkype(String skype) {
		this.skype = skype;
	}

	/**
	 * @return the yahoo
	 */
	public String getYahoo() {
		return yahoo;
	}

	/**
	 * @param yahoo
	 *            the yahoo to set
	 */
	public void setYahoo(String yahoo) {
		this.yahoo = yahoo;
	}

	/**
	 * @return the aim
	 */
	public String getAim() {
		return aim;
	}

	/**
	 * @param aim
	 *            the aim to set
	 */
	public void setAim(String aim) {
		this.aim = aim;
	}

	/**
	 * @return the msn
	 */
	public String getMsn() {
		return msn;
	}

	/**
	 * @param msn
	 *            the msn to set
	 */
	public void setMsn(String msn) {
		this.msn = msn;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the institution
	 */
	public String getInstitution() {
		return institution;
	}

	/**
	 * @param institution
	 *            the institution to set
	 */
	public void setInstitution(String institution) {
		this.institution = institution;
	}

	/**
	 * @return the idnumber
	 */
	public String getIdnumber() {
		return idnumber;
	}

	/**
	 * @param idnumber
	 *            the idnumber to set
	 */
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	/**
	 * @return the interests
	 */
	public String getInterests() {
		return interests;
	}

	/**
	 * @param interests
	 *            the interests to set
	 */
	public void setInterests(String interests) {
		this.interests = interests;
	}

	/**
	 * @return the firstaccess
	 */
	public Long getFirstaccess() {
		return firstaccess;
	}

	/**
	 * @param firstaccess
	 *            the firstaccess to set
	 */
	public void setFirstaccess(Long firstaccess) {
		this.firstaccess = firstaccess;
	}

	
	/**
	 * @return the lastaccess
	 */
	public Long getLastaccess() {
		return lastaccess;
	}

	/**
	 * @param lastaccess
	 *            the lastaccess to set
	 */
	public void setLastaccess(Long lastaccess) {
		this.lastaccess = lastaccess;
	}

	/**
	 * @return the auth
	 */
	public String getAuth() {
		return auth;
	}

	/**
	 * @param auth
	 *            the auth to set
	 */
	public void setAuth(String auth) {
		this.auth = auth;
	}

	/**
	 * @return the confirmed
	 */
	public boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * @param confirmed
	 *            the confirmed to set
	 */
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang
	 *            the calendar type to set
	 */
	public void setCalendartype(String calendartype) {
		this.calendartype = calendartype;
	}

	/**
	 * @return the calendar type
	 */
	public String getCalendartype() {
		return calendartype;
	}

	/**
	 * @param lang
	 *            the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the theme
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * @param theme
	 *            the theme to set
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone
	 *            the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
	 * @return the mailformat
	 */
	public int getMailformat() {
		return mailformat;
	}

	/**
	 * @param mailformat
	 *            the mailformat to set
	 */
	public void setMailformat(int mailformat) {
		this.mailformat = mailformat;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the descriptionformat
	 */
	public int getDescriptionformat() {
		return descriptionformat;
	}

	/**
	 * @param descriptionformat
	 *            the descriptionformat to set
	 */
	public void setDescriptionformat(int descriptionformat) {
		this.descriptionformat = descriptionformat;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the profileimageurlsmall
	 */
	public String getProfileimageurlsmall() {
		return profileimageurlsmall;
	}

	/**
	 * @param profileimageurlsmall
	 *            the profileimageurlsmall to set
	 */
	public void setProfileimageurlsmall(String profileimageurlsmall) {
		this.profileimageurlsmall = profileimageurlsmall;
	}

	/**
	 * @return the profileimageurl
	 */
	public String getProfileimageurl() {
		return profileimageurl;
	}

	/**
	 * @param profileimageurl
	 *            the profileimageurl to set
	 */
	public void setProfileimageurl(String profileimageurl) {
		this.profileimageurl = profileimageurl;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean isCreatePassword() {
		return this.createpassword;
	}

	/**
	 * Set a random password genereation
	 * 
	 * @param createPassword
	 */
	public void setCreatePassword(boolean createPassword) {
		this.createpassword = new Boolean(createPassword);
	}

	/**
	 * @return the customfields
	 */
	public UserCustomField[] getCustomfields() {
		UserCustomField[] results = null;

		if (customfields != null) {
			if (customfields.size() > 0) {
				results = new UserCustomField[customfields.size()];
				customfields.toArray(results);
			}
		}
		return results;
	}


	/**
	 * 
	 * @param type
	 * @param value
	 * @param name
	 * @param shortname
	 */
	public void addCustomfield(String type, String name, String value, String shortname) {
		this.addCustomfield(new UserCustomField(type, value, name, shortname));
	}

	/**
	 * 
	 * @param type
	 * @param value
	 * @param name
	 * @param shortname
	 */
	public void addCustomfield(CustomFieldType type, String name, String value, String shortname) {
		this.addCustomfield(new UserCustomField(type, value, name, shortname));
	}

	/**
	 * 
	 * @param value
	 * @param name
	 * @param shortname
	 */
	public void addCustomfield(Date value, String name, String shortname) {
		this.addCustomfield(new UserCustomField(
			CustomFieldType.DATETIME, 
			name, 
			String.valueOf(value.getTime()/1000), 
			shortname));
	}

	public void addCustomfield(UserCustomField customField) {
		if (this.customfields == null)
			this.customfields = new LinkedHashSet<UserCustomField>();
		this.customfields.add(customField);
	}


	
	/**
	 * @return the users enrollment
	 */
	public MoodleCourse[] getEnrolledCourses() {
		MoodleCourse[] results = null;
		if (this.enrolledcourses != null && !this.enrolledcourses.isEmpty()) {
			results = new MoodleCourse[this.enrolledcourses.size()];
			this.enrolledcourses.toArray(results);
		}
		return results;
	}
	
	public MoodleGroup[] getGroups() {
		MoodleGroup[] results = null;
		if (this.groups != null && !this.groups.isEmpty()) {
			results = new MoodleGroup[this.groups.size()];
			this.groups.toArray(results);
		}
		return results;
	}

	public void addGroup(MoodleGroup group) {
		if (this.groups == null)
			this.groups = new LinkedHashSet<>();
		this.groups.add(group);
	}

	public void addGroup(Long courseid, String name, String description) {
		this.addGroup(new MoodleGroup(courseid, name, description));
	}

	public MoodleRole[] getRoles() {
		MoodleRole[] results = null;
		if (this.roles != null && !this.roles.isEmpty()) {
			results = new MoodleRole[this.roles.size()];
			this.roles.toArray(results);
		}
		return results;
	}
	public void addRole(MoodleRole role) {
		if (this.roles == null)
			this.roles = new LinkedHashSet<>();
		this.roles.add(role);
	}

	public void addRole(Long roleid, String name, String shortname, Integer sortorder) {
		this.roles.add(new MoodleRole(roleid, name, shortname, sortorder));
	}

	/**
	 * @param id
	 * @param fullname
	 * @param shortname
	 */
	public void addEnrolledCourse(Long id, String fullname, String shortname) {
		this.addEnrolledCourse(new MoodleCourse(id, fullname, shortname));
	}

	/**
	 * @param enrolledCourse
	 */
	public void addEnrolledCourse(MoodleCourse enrolledCourse) {
		if (this.enrolledcourses == null)
			this.enrolledcourses = new LinkedHashSet<MoodleCourse>();
		this.enrolledcourses.add(enrolledCourse);
	}

	/**
	 * @return the preferences
	 */
	public UserPreference[] getPreferences() {
		UserPreference[] results = null;
		if (preferences != null) {
			if (preferences.size() > 0) {
				results = new UserPreference[preferences.size()];
				preferences.toArray(results);
			}
		}
		return results;
	}

	/**
	 * 
	 * @param name
	 * @param value
	 */
	public void addPreference(String name, String value) {
		this.addPreference(new UserPreference(name, value));
	}

	public void addPreference(UserPreference preference) {
		if (this.preferences == null)
			this.preferences = new LinkedHashSet<UserPreference>();
		this.preferences.add(preference);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idnumber == null) ? 0 : idnumber.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MoodleUser))
			return false;
		MoodleUser other = (MoodleUser) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idnumber == null) {
			if (other.idnumber != null)
				return false;
		} else if (!idnumber.equals(other.idnumber))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MoodleUser [id=").append(id).append(", username=").append(username).append(", password=")
				.append("<SECRET>").append(", firstname=").append(firstname).append(", lastname=").append(lastname)
				.append(", fullname=").append(fullname).append(", firstnamephonetic=").append(firstnamephonetic)
				.append(", lastnamephonetic=").append(lastnamephonetic).append(", middlename=").append(middlename)
				.append(", alternatename=").append(alternatename).append(", email=").append(email).append(", address=")
				.append(address).append(", phone1=").append(phone1).append(", phone2=").append(phone2).append(", icq=")
				.append(icq).append(", skype=").append(skype).append(", yahoo=").append(yahoo).append(", aim=")
				.append(aim).append(", msn=").append(msn).append(", department=").append(department)
				.append(", institution=").append(institution).append(", idnumber=").append(idnumber)
				.append(", interests=").append(interests).append(", firstaccess=").append(firstaccess)
				.append(", lastaccess=").append(lastaccess).append(", auth=").append(auth).append(", confirmed=")
				.append(confirmed).append(", lang=").append(lang).append(", theme=").append(theme).append(", timezone=")
				.append(timezone).append(", mailformat=").append(mailformat).append(", description=")
				.append(description).append(", descriptionformat=").append(descriptionformat).append(", city=")
				.append(city).append(", url=").append(url).append(", country=").append(country)
				.append(", profileimageurlsmall=").append(profileimageurlsmall).append(", profileimageurl=")
				.append(profileimageurl).append(", customfields=").append(customfields).append(", preferences=")
				.append(preferences).append("]");
		return builder.toString();
	}

	/**
	 * Default comparable
	 */
	public int compareTo(MoodleUser o) {
		return this.id.intValue() - o.getId().intValue();
	}

	
	

}
