/*
 *  Copyright (C) 2018 Carlos Alexandre S. da Fonseca
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

import ml.jmoodle.annotations.MoodleConverter;

/**
 * <p>
 * Class to relate a user to a role within a particular context.
 * </p>
 * <p>
 * Problem here as of 2011-02-13 there is no way of obtaining the contextid or
 * roleid programatically through the standard web services interface.
 * </p>
 * 
 * @author Carlos Alexandre S. da Fonseca
 */
@MoodleConverter
public class MoodleUserRoleContext implements Serializable {

	private static final long serialVersionUID = -6780029389011807941L;

	public enum ContextLevel implements Serializable {
		SYSTEM("system"), COURSE_CATEGORY("coursecat"),
		COURSE("course"), BLOCK("block"),
		USER("user"), MODULE("module");
		
		private String contextlevel;

		private ContextLevel(String contextlevel) {
			this.contextlevel = contextlevel;
		}
		
		public String getValue() {
			return contextlevel;
		}

		@Override
		public String toString() {
			return this.getValue();
		}
	}

	private Long roleid = null;
	private Long userid = null;
	private Long contextid = null;
	private ContextLevel contextlevel = null;
	private Long instanceid = null;

	/**
	 * <p>
	 * Constructor for bean requirements.
	 * </p>
	 */
	public MoodleUserRoleContext() {
	}

	/**
	 * <p>
	 * Constructor to create a MoodleEnrolUser object with known roleid, userid
	 * and contextid.
	 * </p>
	 *
	 * @param roleid
	 *            Long
	 * @param userid
	 *            Long
	 * @param contextid
	 *            Long
	 */
	public MoodleUserRoleContext(Long roleid, Long userid, Long contextid) {
		this.roleid = roleid;
		this.userid = userid;
		this.contextid = contextid;
	}

	public MoodleUserRoleContext(Long roleid, Long userid, Long contextid, ContextLevel contextlevel, Long instanceid) {
		this.roleid = roleid;
		this.userid = userid;
		this.contextid = contextid;
		this.contextlevel = contextlevel;
		this.instanceid = instanceid;
	}

	public ContextLevel getContextLevel() {
		return contextlevel;
	}

	public void setContextLevel(ContextLevel contextlevel) {
		this.contextlevel = contextlevel;
	}

	public void setContextLevel(String contextlevel) {
		if (contextlevel.equalsIgnoreCase(ContextLevel.SYSTEM.toString())){
			this.contextlevel = ContextLevel.SYSTEM;
		} else if (contextlevel.equalsIgnoreCase(ContextLevel.COURSE_CATEGORY.toString())){
			this.contextlevel = ContextLevel.COURSE_CATEGORY;
		} else if (contextlevel.equalsIgnoreCase(ContextLevel.COURSE.toString())){
			this.contextlevel = ContextLevel.COURSE;
		} else if (contextlevel.equalsIgnoreCase(ContextLevel.USER.toString())){
			this.contextlevel = ContextLevel.USER;
		} else if (contextlevel.equalsIgnoreCase(ContextLevel.MODULE.toString())){
			this.contextlevel = ContextLevel.MODULE;
		} else if (contextlevel.equalsIgnoreCase(ContextLevel.BLOCK.toString())){
			this.contextlevel = ContextLevel.BLOCK;
		}
	}

	public Long getInstanceId() {
		return instanceid;
	}

	public void setInstanceId(Long instanceid) {
		this.instanceid = instanceid;
	}

	/**
	 * <p>
	 * Method to set the roleid attribute of a MoodleEnrolUser object.
	 * </p>
	 * <p>
	 * The roleid currently cannot be retrieved through the standard web
	 * services interfaces.
	 * </p>
	 *
	 * @param roleid
	 *            Long
	 */
	public void setRoleId(Long roleid) {
		this.roleid = roleid;
	}

	/**
	 * <p>
	 * Method to set the userid attribute of a MoodleEnrolUser object.
	 * </p>
	 * 
	 * @param userid
	 *            Long
	 */
	public void setUserId(Long userid) {
		this.userid = userid;
	}

	/**
	 * <p>
	 * Method to set the contextid attribute of a MoodleEnrolUser object.
	 * </p>
	 * <p>
	 * The contextid currently cannot be retrieved through the standard web
	 * services interfaces.
	 * </p>
	 *
	 * @param contextid
	 *            Long
	 */
	public void setContextId(Long contextid) {
		this.contextid = contextid;
	}

	/**
	 * <p>
	 * Method to get the roleid attribute of a MoodleEnrolUser object.
	 * </p>
	 * <p>
	 * The roleid currently cannot be retrieved through the standard web
	 * services interfaces.
	 * </p>
	 * 
	 * @return roleid Long
	 */
	public Long getRoleId() {
		return roleid;
	}

	/**
	 * <p>
	 * Method to get the userid attribute of a MoodleEnrolUser object.
	 * </p>
	 *
	 * @return userid Long
	 */
	public Long getUserId() {
		return userid;
	}

	/**
	 * <p>
	 * Method to get the contextid attribute of a MoodleEnrolUser object.
	 * </p>
	 * <p>
	 * The contextid currently cannot be retrieved through the standard web
	 * services interfaces.
	 * </p>
	 *
	 * @return contextid Long
	 */
	public Long getContextId() {
		return contextid;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
		return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MoodleUserRoleContext))
			return false;

		MoodleUserRoleContext other = (MoodleUserRoleContext) obj;

		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;

		if (roleid == null) {
			if (other.roleid != null)
				return false;
		} else if (!roleid.equals(other.roleid))
			return false;

		if (contextid == null) {
			if (other.contextid != null)
				return false;
		} else if (!contextid.equals(other.contextid))
			return false;
		
		if (instanceid == null) {
			if (other.instanceid != null)
				return false;
		} else if (!instanceid.equals(other.instanceid))
			return false;

		if (contextlevel == null) {
			if (other.contextlevel != null)
				return false;
		} else if (!contextlevel.equals(other.contextlevel))
			return false;
		
		return true;
	}
}
