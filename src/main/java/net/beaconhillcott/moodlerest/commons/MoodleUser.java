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

package net.beaconhillcott.moodlerest.commons;

import java.io.Serializable;
import java.util.ArrayList;

import net.beaconhillcott.moodlerest.commos.exception.MoodleUserRoleException;
import net.beaconhillcott.moodlerest.rest.MoodleRestUser;

/**
 * Class to hold the state of a MoodleUser object. Used when creating, updating
 * or fetching user information in Moodle.
 * 
 * @see MoodleRestUser
 * @author Bill Antonia
 * @author carlosalexandre
 */
public class MoodleUser implements Serializable, Comparable<MoodleUser> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8465905725413266458L;
	/**
	 *
	 */
	public static final boolean EMAIL_FORMAT_NONE = false;
	/**
	 *
	 */
	public static final boolean EMAIL_FORMAT_HTML = true;

	private Long id = null;
	private String username = null;
	private String password = null;
	private String firstname = null;
	private String lastname = null;
	private String email = null;
	private String auth = null;
	private String idnumber = null;
	private String lang = null;
	private String theme = null;
	private String timezone = null;
	private Boolean mailformat = EMAIL_FORMAT_HTML;
	private String description = null;
	private String city = null;
	private String country = null;
	private String fullname = null;

	private String address = null;
	private String phone1 = null;
	private String phone2 = null;
	private String icq = null;
	private String skype = null;
	private String yahoo = null;
	private String msn = null;
	private String aim = null;
	private String department = null;
	private String institution = null;
	private String interests = null;
	private Long firstaccess = null;
	private Long lastaccess = null;
	private Double confirmed = 0.0;
	private Long descriptionformat = null;
	private String url = null;
	private String profileimageurlsmall = null;
	private String profileimageurl = null;
	private String alternatename = null;
	private String firstnamephonetic = null;
	private String lastnamephonetic = null;
	private ArrayList<UserCustomField> customfields = null;
	private ArrayList<UserGroup> groups = null;
	private ArrayList<UserRole> roles = null;
	private ArrayList<UserPreference> preferences = null;
	private ArrayList<UserEnrolledCourse> enrolledcourses = null;

	/**
	 *
	 */
	public MoodleUser() {
		customfields = new ArrayList<UserCustomField>();
		groups = new ArrayList<UserGroup>();
		roles = new ArrayList<UserRole>();
		preferences = new ArrayList<UserPreference>();
		enrolledcourses = new ArrayList<UserEnrolledCourse>();
	}

	/**
	 *
	 * @param id
	 */
	public MoodleUser(Long id) {
		this.id = id;
		customfields = new ArrayList<UserCustomField>();
		groups = new ArrayList<UserGroup>();
		roles = new ArrayList<UserRole>();
		preferences = new ArrayList<UserPreference>();
		enrolledcourses = new ArrayList<UserEnrolledCourse>();
	}

	/**
	 *
	 * @param username
	 * @param password
	 * @param firstname
	 * @param lastname
	 * @param email
	 */
	public MoodleUser(String username, String password, String firstname, String lastname, String email) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		customfields = new ArrayList<UserCustomField>();
		groups = new ArrayList<UserGroup>();
		roles = new ArrayList<UserRole>();
		preferences = new ArrayList<UserPreference>();
		enrolledcourses = new ArrayList<UserEnrolledCourse>();
	}

	/**
	 *
	 * @param nodeName
	 * @param content
	 */
	public void setMoodleUserField(String nodeName, String content) {
		if (content != null) {
			if (nodeName.equals("id"))
				setId(Long.valueOf(content));
			if (nodeName.equals("mailformat") && (!content.equals(null))) {
				if (!content.equals(""))
					setMailFormat(Integer.parseInt(content) == 0 ? EMAIL_FORMAT_NONE : EMAIL_FORMAT_HTML);
			}
			if (nodeName.equals("descriptionformat")) {
				if (!content.isEmpty()) {
					try {
						setDescriptionFormat(Long.valueOf(content));
					} catch (NumberFormatException ex) {
					}
				}
			}
			if (nodeName.equals("firstaccess") && content != null)
				setFirstAccess(Long.valueOf(content));
			if (nodeName.equals("lastaccess"))
				setLastAccess(Long.valueOf(content));
			if (nodeName.equals("confirmed") && (!content.equals(null))) {
				if (!content.equals(""))
					setConfirmed(Double.valueOf(content));
			}
		}
		if (nodeName.equals("username"))
			setUsername(content);
		if (nodeName.equals("firstname"))
			setFirstname(content);
		if (nodeName.equals("lastname"))
			setLastname(content);
		if (nodeName.equals("email"))
			setEmail(content);
		if (nodeName.equals("auth"))
			setAuth(content);
		if (nodeName.equals("idnumber"))
			setIdNumber(content);
		if (nodeName.equals("lang"))
			setLang(content);
		if (nodeName.equals("theme"))
			setTheme(content);
		if (nodeName.equals("timezone"))
			setTimezone(content);
		if (nodeName.equals("description"))
			setDescription(content);
		if (nodeName.equals("city"))
			setCity(content);
		if (nodeName.equals("country"))
			setCountry(content);
		if (nodeName.equals("fullname")) {
			setFullname(content);
		}
		if (nodeName.equals("address"))
			setAddress(content);
		if (nodeName.equals("phone1"))
			setPhone1(content);
		if (nodeName.equals("phone2"))
			setPhone2(content);
		if (nodeName.equals("icq"))
			setICQ(content);
		if (nodeName.equals("skype"))
			setSkype(content);
		if (nodeName.equals("yahoo"))
			setYahoo(content);
		if (nodeName.equals("msn"))
			setMSN(content);
		if (nodeName.equals("aim"))
			setAim(content);
		if (nodeName.equals("department"))
			setDepartment(content);
		if (nodeName.equals("institution"))
			setInstitution(content);
		if (nodeName.equals("interests"))
			setInterests(content);
		if (nodeName.equals("url"))
			setURL(content);
		if (nodeName.equals("profileimageurlsmall"))
			setProfileImageURLSmall(content);
		if (nodeName.equals("profileimageurl"))
			setProfileImageURL(content);
		if (nodeName.equals("alternatename"))
			setAlternatename(content);
		if (nodeName.equals("lastnamephonetic"))
			setLastnamephonetic(content);
		if (nodeName.equals("firstnamephonetic"))
			setFirstnamephonetic(content);
	}

	public String getAlternatename() {
		return alternatename;
	}

	public void setAlternatename(String alternatename) {
		this.alternatename = alternatename;
	}

	public String getFirstnamephonetic() {
		return firstnamephonetic;
	}

	public void setFirstnamephonetic(String firstnamephonetic) {
		this.firstnamephonetic = firstnamephonetic;
	}

	public String getLastnamephonetic() {
		return lastnamephonetic;
	}

	public void setLastnamephonetic(String lastnamephonetic) {
		this.lastnamephonetic = lastnamephonetic;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 *
	 * @param type
	 * @param value
	 * @param name
	 * @param shortname
	 */
	public void addCustomField(String type, String value, String name, String shortname) {
		if (customfields == null)
			customfields = new ArrayList();
		UserCustomField field = new UserCustomField(type, value, name, shortname);
		customfields.add(field);
	}

	/**
	 *
	 * @param field
	 */
	public void addCustomField(UserCustomField field) {
		if (customfields == null)
			customfields = new ArrayList();
		customfields.add(field);
	}

	/**
	 *
	 * @param id
	 * @param name
	 * @param description
	 */
	public void addGroup(Long id, String name, String description) {
		if (groups == null)
			groups = new ArrayList();
		UserGroup group = new UserGroup(id, name, description);
		groups.add(group);
	}

	/**
	 *
	 * @param group
	 */
	public void addGroup(UserGroup group) {
		if (groups == null)
			groups = new ArrayList();
		groups.add(group);
	}

	/**
	 *
	 * @param roleid
	 * @param name
	 * @param shortname
	 * @param sortorder
	 */
	public void addRole(Long roleid, String name, String shortname, Integer sortorder) throws MoodleUserRoleException {
		if (roles == null) {
			roles = new ArrayList();
		}
		UserRole role = new UserRole(roleid, name, shortname, sortorder);
		roles.add(role);
	}

	public void addRole(Role role, String name, String shortname, Integer sortorder) throws MoodleUserRoleException {
		if (roles == null) {
			roles = new ArrayList();
		}
		UserRole roleN = new UserRole(role, name, shortname, sortorder);
		roles.add(roleN);
	}

	/**
	 *
	 * @param role
	 */
	public void addRole(UserRole role) {
		if (roles == null) {
			roles = new ArrayList();
		}
		roles.add(role);
	}

	/**
	 *
	 * @param name
	 * @param value
	 */
	public void addPreference(String name, String value) {
		if (preferences == null)
			preferences = new ArrayList();
		UserPreference preference = new UserPreference(name, value);
		preferences.add(preference);
	}

	/**
	 *
	 * @param preference
	 */
	public void addPreference(UserPreference preference) {
		if (preferences == null)
			preferences = new ArrayList();
		preferences.add(preference);
	}

	/**
	 *
	 * @param id
	 * @param fullname
	 * @param shortname
	 */
	public void addEnrolledCourse(Long id, String fullname, String shortname) {
		if (enrolledcourses == null)
			enrolledcourses = new ArrayList();
		UserEnrolledCourse course = new UserEnrolledCourse(id, fullname, shortname);
		enrolledcourses.add(course);
	}

	/**
	 *
	 * @param course
	 */
	public void addEnrolledCourse(UserEnrolledCourse course) {
		if (enrolledcourses == null)
			enrolledcourses = new ArrayList();
		enrolledcourses.add(course);
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<UserCustomField> getCustomFields() {
		return customfields;
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<UserGroup> getGroups() {
		return groups;
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<UserRole> getRoles() {
		return roles;
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<UserPreference> getPreference() {
		return preferences;
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<UserEnrolledCourse> getEnrolledCourses() {
		return enrolledcourses;
	}

	/**
	 *
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 *
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 *
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 *
	 * @return
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 *
	 * @return
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 *
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 *
	 * @return
	 */
	public String getAuth() {
		return auth;
	}

	/**
	 *
	 * @return
	 */
	public String getIdNumber() {
		return idnumber;
	}

	/**
	 *
	 * @return
	 */
	public String getLang() {
		return lang;
	}

	/**
	 *
	 * @return
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 *
	 * @return
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 *
	 * @return
	 */
	public Boolean getMailFormat() {
		return mailformat;
	}

	/**
	 *
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 *
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 *
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 *
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 *
	 * @return
	 */
	public String getPhone1() {
		return phone1;
	}

	/**
	 *
	 * @return
	 */
	public String getPhone2() {
		return phone2;
	}

	/**
	 *
	 * @return
	 */
	public String getICQ() {
		return icq;
	}

	/**
	 * 
	 * @return
	 */
	public String getSkype() {
		return skype;
	}

	/**
	 *
	 * @return
	 */
	public String getYahoo() {
		return yahoo;
	}

	/**
	 *
	 * @return
	 */
	public String getMSN() {
		return msn;
	}

	/**
	 *
	 * @return
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 *
	 * @return
	 */
	public String getInstitution() {
		return institution;
	}

	/**
	 *
	 * @return
	 */
	public String getInterests() {
		return interests;
	}

	/**
	 *
	 * @return
	 */
	public String getURL() {
		return url;
	}

	/**
	 *
	 * @return
	 */
	public Long getFirstAccess() {
		return firstaccess;
	}

	/**
	 *
	 * @return
	 */
	public Long getLastAccess() {
		return lastaccess;
	}

	/**
	 *
	 * @return
	 */
	public String getProfileImageURLSmall() {
		return profileimageurlsmall;
	}

	/**
	 *
	 * @return
	 */
	public String getProfileImageURL() {
		return profileimageurl;
	}

	/**
	 * 
	 * @return
	 */
	public Double getConfirmed() {
		return confirmed;
	}

	/**
	 *
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 *
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 *
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 *
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 *
	 * @param lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 *
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 *
	 * @param auth
	 */
	public void setAuth(String auth) {
		this.auth = auth;
	}

	/**
	 *
	 * @param idnumber
	 */
	public void setIdNumber(String idnumber) {
		this.idnumber = idnumber;
	}

	/**
	 *
	 * @param lang
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 *
	 * @param theme
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 *
	 * @param timezone
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
	 *
	 * @param mailformat
	 */
	public void setMailFormat(Boolean mailformat) {
		this.mailformat = mailformat;
	}

	/**
	 *
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 *
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 *
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 *
	 * @return
	 */
	public boolean isEmailHTMLFormat() {
		return mailformat;
	}

	/**
	 *
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 *
	 * @param phone1
	 */
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	/**
	 *
	 * @param phone2
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	/**
	 *
	 * @param icq
	 */
	public void setICQ(String icq) {
		this.icq = icq;
	}

	/**
	 *
	 * @param yahoo
	 */
	public void setYahoo(String yahoo) {
		this.yahoo = yahoo;
	}

	/**
	 *
	 * @param msn
	 */
	public void setMSN(String msn) {
		this.msn = msn;
	}

	/**
	 *
	 * @param skype
	 */
	public void setSkype(String skype) {
		this.skype = skype;
	}

	/**
	 *
	 * @param department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 *
	 * @param interests
	 */
	public void setInterests(String interests) {
		this.interests = interests;
	}

	/**
	 *
	 * @param institution
	 */
	public void setInstitution(String institution) {
		this.institution = institution;
	}

	/**
	 *
	 * @param url
	 */
	public void setURL(String url) {
		this.url = url;
	}

	/**
	 *
	 * @param firstaccess
	 */
	public void setFirstAccess(Long firstaccess) {
		this.firstaccess = firstaccess;
	}

	/**
	 *
	 * @param lastaccess
	 */
	public void setLastAccess(Long lastaccess) {
		this.lastaccess = lastaccess;
	}

	/**
	 * 
	 * @param confirmed
	 */
	public void setConfirmed(Double confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 *
	 * @param profileimageurlsmall
	 */
	public void setProfileImageURLSmall(String profileimageurlsmall) {
		this.profileimageurlsmall = profileimageurlsmall;
	}

	/**
	 *
	 * @param profileimageurl
	 */
	public void setProfileImageURL(String profileimageurl) {
		this.profileimageurl = profileimageurl;
	}

	/**
	 * 
	 * @param descriptionformat
	 */
	public void setDescriptionFormat(Long descriptionformat) {
		this.descriptionformat = descriptionformat;
	}

	/**
	 * 
	 * @return
	 */
	public long getDescriptionFormat() {
		return descriptionformat;
	}

	public String getAim() {
		return aim;
	}

	public void setAim(String aim) {
		this.aim = aim;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((aim == null) ? 0 : aim.hashCode());
		result = prime * result + ((alternatename == null) ? 0 : alternatename.hashCode());
		result = prime * result + ((auth == null) ? 0 : auth.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((confirmed == null) ? 0 : confirmed.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((customfields == null) ? 0 : customfields.hashCode());
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((descriptionformat == null) ? 0 : descriptionformat.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((enrolledcourses == null) ? 0 : enrolledcourses.hashCode());
		result = prime * result + ((firstaccess == null) ? 0 : firstaccess.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((firstnamephonetic == null) ? 0 : firstnamephonetic.hashCode());
		result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((icq == null) ? 0 : icq.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idnumber == null) ? 0 : idnumber.hashCode());
		result = prime * result + ((institution == null) ? 0 : institution.hashCode());
		result = prime * result + ((interests == null) ? 0 : interests.hashCode());
		result = prime * result + ((lang == null) ? 0 : lang.hashCode());
		result = prime * result + ((lastaccess == null) ? 0 : lastaccess.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((lastnamephonetic == null) ? 0 : lastnamephonetic.hashCode());
		result = prime * result + ((mailformat == null) ? 0 : mailformat.hashCode());
		result = prime * result + ((msn == null) ? 0 : msn.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone1 == null) ? 0 : phone1.hashCode());
		result = prime * result + ((phone2 == null) ? 0 : phone2.hashCode());
		result = prime * result + ((preferences == null) ? 0 : preferences.hashCode());
		result = prime * result + ((profileimageurl == null) ? 0 : profileimageurl.hashCode());
		result = prime * result + ((profileimageurlsmall == null) ? 0 : profileimageurlsmall.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((skype == null) ? 0 : skype.hashCode());
		result = prime * result + ((theme == null) ? 0 : theme.hashCode());
		result = prime * result + ((timezone == null) ? 0 : timezone.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((yahoo == null) ? 0 : yahoo.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MoodleUser other = (MoodleUser) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (aim == null) {
			if (other.aim != null)
				return false;
		} else if (!aim.equals(other.aim))
			return false;
		if (alternatename == null) {
			if (other.alternatename != null)
				return false;
		} else if (!alternatename.equals(other.alternatename))
			return false;
		if (auth == null) {
			if (other.auth != null)
				return false;
		} else if (!auth.equals(other.auth))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (confirmed == null) {
			if (other.confirmed != null)
				return false;
		} else if (!confirmed.equals(other.confirmed))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (customfields == null) {
			if (other.customfields != null)
				return false;
		} else if (!customfields.equals(other.customfields))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (descriptionformat == null) {
			if (other.descriptionformat != null)
				return false;
		} else if (!descriptionformat.equals(other.descriptionformat))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enrolledcourses == null) {
			if (other.enrolledcourses != null)
				return false;
		} else if (!enrolledcourses.equals(other.enrolledcourses))
			return false;
		if (firstaccess == null) {
			if (other.firstaccess != null)
				return false;
		} else if (!firstaccess.equals(other.firstaccess))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (firstnamephonetic == null) {
			if (other.firstnamephonetic != null)
				return false;
		} else if (!firstnamephonetic.equals(other.firstnamephonetic))
			return false;
		if (fullname == null) {
			if (other.fullname != null)
				return false;
		} else if (!fullname.equals(other.fullname))
			return false;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (icq == null) {
			if (other.icq != null)
				return false;
		} else if (!icq.equals(other.icq))
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
		if (institution == null) {
			if (other.institution != null)
				return false;
		} else if (!institution.equals(other.institution))
			return false;
		if (interests == null) {
			if (other.interests != null)
				return false;
		} else if (!interests.equals(other.interests))
			return false;
		if (lang == null) {
			if (other.lang != null)
				return false;
		} else if (!lang.equals(other.lang))
			return false;
		if (lastaccess == null) {
			if (other.lastaccess != null)
				return false;
		} else if (!lastaccess.equals(other.lastaccess))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (lastnamephonetic == null) {
			if (other.lastnamephonetic != null)
				return false;
		} else if (!lastnamephonetic.equals(other.lastnamephonetic))
			return false;
		if (mailformat == null) {
			if (other.mailformat != null)
				return false;
		} else if (!mailformat.equals(other.mailformat))
			return false;
		if (msn == null) {
			if (other.msn != null)
				return false;
		} else if (!msn.equals(other.msn))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone1 == null) {
			if (other.phone1 != null)
				return false;
		} else if (!phone1.equals(other.phone1))
			return false;
		if (phone2 == null) {
			if (other.phone2 != null)
				return false;
		} else if (!phone2.equals(other.phone2))
			return false;
		if (preferences == null) {
			if (other.preferences != null)
				return false;
		} else if (!preferences.equals(other.preferences))
			return false;
		if (profileimageurl == null) {
			if (other.profileimageurl != null)
				return false;
		} else if (!profileimageurl.equals(other.profileimageurl))
			return false;
		if (profileimageurlsmall == null) {
			if (other.profileimageurlsmall != null)
				return false;
		} else if (!profileimageurlsmall.equals(other.profileimageurlsmall))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (skype == null) {
			if (other.skype != null)
				return false;
		} else if (!skype.equals(other.skype))
			return false;
		if (theme == null) {
			if (other.theme != null)
				return false;
		} else if (!theme.equals(other.theme))
			return false;
		if (timezone == null) {
			if (other.timezone != null)
				return false;
		} else if (!timezone.equals(other.timezone))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (yahoo == null) {
			if (other.yahoo != null)
				return false;
		} else if (!yahoo.equals(other.yahoo))
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
		builder.append("MoodleUser [id=").append(id).append(", username=").append(username)
				.append(", password=<THIS IS SECRET>").append(", firstname=").append(firstname).append(", lastname=")
				.append(lastname).append(", email=").append(email).append(", auth=").append(auth).append(", idnumber=")
				.append(idnumber).append(", lang=").append(lang).append(", theme=").append(theme).append(", timezone=")
				.append(timezone).append(", mailformat=").append(mailformat).append(", description=")
				.append(description).append(", city=").append(city).append(", country=").append(country)
				.append(", fullname=").append(fullname).append(", address=").append(address).append(", phone1=")
				.append(phone1).append(", phone2=").append(phone2).append(", icq=").append(icq).append(", skype=")
				.append(skype).append(", yahoo=").append(yahoo).append(", msn=").append(msn).append(", aim=")
				.append(aim).append(", department=").append(department).append(", institution=").append(institution)
				.append(", interests=").append(interests).append(", firstaccess=").append(firstaccess)
				.append(", lastaccess=").append(lastaccess).append(", confirmed=").append(confirmed)
				.append(", descriptionformat=").append(descriptionformat).append(", url=").append(url)
				.append(", profileimageurlsmall=").append(profileimageurlsmall).append(", profileimageurl=")
				.append(profileimageurl).append(", alternatename=").append(alternatename).append(", firstnamephonetic=")
				.append(firstnamephonetic).append(", lastnamephonetic=").append(lastnamephonetic)
				.append(", customfields=").append(customfields).append(", groups=").append(groups).append(", roles=")
				.append(roles).append(", preferences=").append(preferences).append(", enrolledcourses=")
				.append(enrolledcourses).append("]");
		return builder.toString();
	}

	/**
	 * Default comparable
	 */
	public int compareTo(MoodleUser o) {
		return this.id.intValue() - o.getId().intValue();
	}

}
