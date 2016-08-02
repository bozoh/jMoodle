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

/**
 * 
 * @author Carlos Alexandre S. da Fonseca
 * 
 *
 */
public class MoodleUser implements Serializable, Comparable<MoodleUser> {

	private static final long serialVersionUID = 8465905725413266458L;
	/**
	 * Defaulsts values
	 */
	public static final boolean EMAIL_FORMAT_NONE = false;
	public static final boolean EMAIL_FORMAT_HTML = true;

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
	private Date firstaccess;
	private Date lastaccess;
	private String auth = "manual";
	private boolean confirmed;
	private String lang = "en";
	private String calendartype = "gregorian";
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
	private Set<CustomField> customfields;
	private Set<Preference> preferences;
	private Set<Warning> warnings;

	public MoodleUser() {
		this.customfields = new LinkedHashSet<CustomField>();
		this.preferences = new LinkedHashSet<Preference>();
		this.warnings = new LinkedHashSet<Warning>();
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
	public Date getFirstaccess() {
		return firstaccess;
	}

	/**
	 * @param firstaccess
	 *            the firstaccess to set
	 */
	public void setFirstaccess(Date firstaccess) {
		this.firstaccess = firstaccess;
	}

	/**
	 * @param firstaccess
	 *            the firstaccess to set
	 */
	public void setFirstaccess(Long firstaccess) {
		this.firstaccess = new Date(firstaccess.longValue());
	}

	/**
	 * @return the lastaccess
	 */
	public Date getLastaccess() {
		return lastaccess;
	}

	/**
	 * @param lastaccess
	 *            the lastaccess to set
	 */
	public void setLastaccess(Date lastaccess) {
		this.lastaccess = lastaccess;
	}

	/**
	 * @param lastaccess
	 *            the lastaccess to set
	 */
	public void setLastaccess(Long lastaccess) {
		this.lastaccess = new Date(lastaccess.longValue());
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
	 * @return the customfields
	 */
	public Set<CustomField> getCustomfields() {
		return customfields;
	}

	/**
	 * 
	 * @param type
	 * @param value
	 * @param name
	 * @param shortname
	 */
	public void addCustomfields(CustomFieldType type, String value, String name, String shortname) {
		this.customfields.add(new CustomField(type.getValue(), value, name, shortname));
	}
	
	/**
	 * 
	 * @param value
	 * @param name
	 * @param shortname
	 */
	public void addCustomfields(Date value, String name, String shortname) {
		this.customfields.add(new CustomField(value, name, shortname));
	}

	/**
	 * @return the preferences
	 */
	public Set<Preference> getPreferences() {
		return preferences;
	}

	/**
	 * 
	 * @param name
	 * @param value
	 */
	public void addPreferences(String name, String value) {
		this.preferences.add(new Preference(name, value));
	}

	/**
	 * @return the warnings
	 */
	public Set<Warning> getWarnings() {
		return warnings;
	}

	/**
	 * @param warnings
	 *            the warnings to set
	 */
	public void addWarnings(String item, Long itemid, String warningcode, String message) {
		this.warnings.add(new Warning(item, itemid, warningcode, message));
	}

	public boolean hasWarnings() {
		return !this.warnings.isEmpty();
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
		result = prime * result + (confirmed ? 1231 : 1237);
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((customfields == null) ? 0 : customfields.hashCode());
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + descriptionformat;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstaccess == null) ? 0 : firstaccess.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((firstnamephonetic == null) ? 0 : firstnamephonetic.hashCode());
		result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
		result = prime * result + ((icq == null) ? 0 : icq.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idnumber == null) ? 0 : idnumber.hashCode());
		result = prime * result + ((institution == null) ? 0 : institution.hashCode());
		result = prime * result + ((interests == null) ? 0 : interests.hashCode());
		result = prime * result + ((lang == null) ? 0 : lang.hashCode());
		result = prime * result + ((lastaccess == null) ? 0 : lastaccess.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((lastnamephonetic == null) ? 0 : lastnamephonetic.hashCode());
		result = prime * result + mailformat;
		result = prime * result + ((middlename == null) ? 0 : middlename.hashCode());
		result = prime * result + ((msn == null) ? 0 : msn.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone1 == null) ? 0 : phone1.hashCode());
		result = prime * result + ((phone2 == null) ? 0 : phone2.hashCode());
		result = prime * result + ((preferences == null) ? 0 : preferences.hashCode());
		result = prime * result + ((profileimageurl == null) ? 0 : profileimageurl.hashCode());
		result = prime * result + ((profileimageurlsmall == null) ? 0 : profileimageurlsmall.hashCode());
		result = prime * result + ((skype == null) ? 0 : skype.hashCode());
		result = prime * result + ((theme == null) ? 0 : theme.hashCode());
		result = prime * result + ((timezone == null) ? 0 : timezone.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((warnings == null) ? 0 : warnings.hashCode());
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
		if (confirmed != other.confirmed)
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
		if (descriptionformat != other.descriptionformat)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		if (mailformat != other.mailformat)
			return false;
		if (middlename == null) {
			if (other.middlename != null)
				return false;
		} else if (!middlename.equals(other.middlename))
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
		if (warnings == null) {
			if (other.warnings != null)
				return false;
		} else if (!warnings.equals(other.warnings))
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
				.append(preferences).append(", warnings=").append(warnings).append("]");
		return builder.toString();
	}

	/**
	 * Default comparable
	 */
	public int compareTo(MoodleUser o) {
		return this.id.intValue() - o.getId().intValue();
	}

	public class CustomField {
		private String type;
		private String value;
		private String name;
		private String shortname;

		public CustomField(String type, String value, String name, String shortname) {
			this.type = type;
			this.value = value;
			this.name = name;
			this.shortname = shortname;
		}

		public CustomField(Date value, String name, String shortname) {
			this.type = CustomFieldType.DATETIME.getValue();
			this.value = String.valueOf(value.getTime() / 1000l);
			this.name = name;
			this.shortname = shortname;
		}

		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the shortname
		 */
		public String getShortname() {
			return shortname;
		}

	}

	public class Preference {
		private String name;
		private String value;

		public Preference(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

	}

	public class Warning {
		private String item;
		private Long itemid;
		private String warningcode;
		private String message;

		public Warning(String item, Long itemid, String warningcode, String message) {
			super();
			this.item = item;
			this.itemid = itemid;
			this.warningcode = warningcode;
			this.message = message;
		}

		/**
		 * @return the item
		 */
		public String getItem() {
			return item;
		}

		/**
		 * @return the itemid
		 */
		public Long getItemid() {
			return itemid;
		}

		/**
		 * @return the warningcode
		 */
		public String getWarningcode() {
			return warningcode;
		}

		/**
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}
	}

	public enum CustomFieldType {
		// checkbox, text, datetime, menu, textarea

		CHECKBOX("checkbox"), TEXT("text"), DATETIME("datetime"), MENU("menu"), TEXTAREA("textarea");

		String value;

		private CustomFieldType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public enum DescriptionFormat {
		// (1 = HTML, 0 = MOODLE, 2 = PLAIN or 4 = MARKDOWN)

		MOODLE(0), HTML(1), PLAIN(2), MARKDOWN(4);

		int value;

		private DescriptionFormat(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

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

}
