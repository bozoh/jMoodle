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

public class MoodleUserEnrolment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4977383120625721566L;

	private Long roleId = null;
	private Long userId = null;
	private Long courseId = null;
	private Long timeStart = null;
	private Long timeEnd = null;
	private Integer suspend = null;
 
	public MoodleUserEnrolment() {
	}

	public MoodleUserEnrolment(Long roleId, Long userId, Long courseId) {
		this.roleId = roleId;
		this.userId = userId;
		this.courseId = courseId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer getSuspend() {
		return suspend;
	}

	public void setSuspend(Integer suspend) {
		this.suspend = suspend;
	}

	public Long getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Long timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Long getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Long timeStart) {
		this.timeStart = timeStart;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MoodleUserEnrolment [roleId=").append(roleId).append(", userId=").append(userId)
				.append(", courseId=").append(courseId).append(", timeStart=").append(timeStart).append(", timeEnd=")
				.append(timeEnd).append(", suspend=").append(suspend).append("]");
		return builder.toString();
	}

}
