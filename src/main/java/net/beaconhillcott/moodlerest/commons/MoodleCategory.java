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

/**
 *
 * @author Bill Antonia
 * @author carlosalexandre
 */
public class MoodleCategory implements Serializable, Comparable<MoodleCategory> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3105407598742391169L;
	private Long id = null;
	private String name = null;
	private Long parent = null;
	private String idnumber = null;
	private String description = null;
	private String theme = null;

	private Integer sortorder = null;
	private Integer coursecount = null;
	private Boolean visible = null;
	private Boolean visibleold = null;
	private Long timemodified = null;
	private Integer depth = null;
	private String path = null;

	/*
	 * The following do not contain category data. These are here to enable the
	 * category deletion routine. See the Moodle webservices API for details.
	 */
	private Long newParent = null;
	private Boolean recursive = false;

	public MoodleCategory() {
	}

	public MoodleCategory(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public MoodleCategory(String name, Long parent) {
		this.name = name;
		this.parent = parent;
	}

	public MoodleCategory(String name, Long parent, String idnumber, String description, String theme) {
		this.name = name;
		this.parent = parent;
		this.idnumber = idnumber;
		this.description = description;
		this.theme = theme;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Long getParent() {
		return parent;
	}

	public String getIdNumber() {
		return idnumber;
	}

	public String getDescription() {
		return description;
	}

	public String getTheme() {
		return theme;
	}

	public Integer getSortOrder() {
		return sortorder;
	}

	public Integer getCourseCount() {
		return coursecount;
	}

	public Boolean getVisible() {
		return visible;
	}

	public Boolean getVisibleOld() {
		return visibleold;
	}

	public Long getTimeModified() {
		return timemodified;
	}

	public Integer getDepth() {
		return depth;
	}

	public String getPath() {
		return path;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public void setIdNumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public void setSortOrder(Integer sortorder) {
		this.sortorder = sortorder;
	}

	public void setCourseCount(Integer coursecount) {
		this.coursecount = coursecount;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public void setVisibleOld(Boolean visibleold) {
		this.visibleold = visibleold;
	}

	public void setTimeModified(Long timemodified) {
		this.timemodified = timemodified;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setField(String field, String value) {
		if (field != null && !field.isEmpty()) {
			if (field.equals("id"))
				setId(Long.parseLong(value));
			if (field.equals("name"))
				setName(value);
			if (field.equals("parent"))
				setParent(Long.parseLong(value));
			if (field.equals("idnumber"))
				setIdNumber(value);
			if (field.equals("description"))
				setDescription(value);
			if (field.equals("theme"))
				setTheme(value);
			if (field.equals("sortorder"))
				setSortOrder(Integer.parseInt(value));
			if (field.equals("coursecount"))
				setCourseCount(Integer.parseInt(value));
			if (field.equals("visible"))
				setVisible(value.equals("0") ? false : true);
			if (field.equals("visibleold"))
				setVisibleOld(value.equals("0") ? false : true);
			if (field.equals("timemodified"))
				setTimeModified(Long.parseLong(value));
			if (field.equals("depth"))
				setDepth(Integer.parseInt(value));
			if (field.equals("path"))
				setPath(value);

		}
	}

	public String getFieldAsString(String field) {
		if (field != null && !field.isEmpty()) {
			if (field.equals("id"))
				return Long.toString(getId());
			if (field.equals("name"))
				return getName();
			if (field.equals("parent"))
				return Long.toString(getParent());
			if (field.equals("idnumber"))
				return getIdNumber();
			if (field.equals("description"))
				return getDescription();
			if (field.equals("theme"))
				return getTheme();
			if (field.equals("sortorder"))
				return Integer.toString(getSortOrder());
			if (field.equals("coursecount"))
				return Integer.toString(getCourseCount());
			if (field.equals("visible"))
				return getVisible() ? "1" : "0";
			if (field.equals("visibleold"))
				return getVisibleOld() ? "1" : "0";
			if (field.equals("timemodified"))
				return Long.toString(getTimeModified());
			if (field.equals("depth"))
				return Integer.toString(getDepth());
			if (field.equals("path"))
				return getPath();
		}
		return null;
	}

	public Long getNewParent() {
		return newParent;
	}

	public void setNewParent(Long newParent) {
		this.newParent = newParent;
	}

	public Boolean isRecursive() {
		return recursive;
	}

	public void setRecursive(Boolean recursive) {
		this.recursive = recursive;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MoodleCategory [id=").append(id).append(", name=").append(name).append(", parent=")
				.append(parent).append(", idnumber=").append(idnumber).append(", description=").append(description)
				.append(", theme=").append(theme).append(", sortorder=").append(sortorder).append(", coursecount=")
				.append(coursecount).append(", visible=").append(visible).append(", visibleold=").append(visibleold)
				.append(", timemodified=").append(timemodified).append(", depth=").append(depth).append(", path=")
				.append(path).append(", newParent=").append(newParent).append(", recursive=").append(recursive)
				.append("]");
		return builder.toString();
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
		result = prime * result + ((coursecount == null) ? 0 : coursecount.hashCode());
		result = prime * result + ((depth == null) ? 0 : depth.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idnumber == null) ? 0 : idnumber.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((newParent == null) ? 0 : newParent.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((recursive == null) ? 0 : recursive.hashCode());
		result = prime * result + ((sortorder == null) ? 0 : sortorder.hashCode());
		result = prime * result + ((theme == null) ? 0 : theme.hashCode());
		result = prime * result + ((timemodified == null) ? 0 : timemodified.hashCode());
		result = prime * result + ((visible == null) ? 0 : visible.hashCode());
		result = prime * result + ((visibleold == null) ? 0 : visibleold.hashCode());
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
		MoodleCategory other = (MoodleCategory) obj;
		if (coursecount == null) {
			if (other.coursecount != null)
				return false;
		} else if (!coursecount.equals(other.coursecount))
			return false;
		if (depth == null) {
			if (other.depth != null)
				return false;
		} else if (!depth.equals(other.depth))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (newParent == null) {
			if (other.newParent != null)
				return false;
		} else if (!newParent.equals(other.newParent))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (recursive == null) {
			if (other.recursive != null)
				return false;
		} else if (!recursive.equals(other.recursive))
			return false;
		if (sortorder == null) {
			if (other.sortorder != null)
				return false;
		} else if (!sortorder.equals(other.sortorder))
			return false;
		if (theme == null) {
			if (other.theme != null)
				return false;
		} else if (!theme.equals(other.theme))
			return false;
		if (timemodified == null) {
			if (other.timemodified != null)
				return false;
		} else if (!timemodified.equals(other.timemodified))
			return false;
		if (visible == null) {
			if (other.visible != null)
				return false;
		} else if (!visible.equals(other.visible))
			return false;
		if (visibleold == null) {
			if (other.visibleold != null)
				return false;
		} else if (!visibleold.equals(other.visibleold))
			return false;
		return true;
	}

	/**
	 * Default Comparable
	 */
	public int compareTo(MoodleCategory o) {
		// First by depth, lower depth comes first
		int depth = this.depth - o.getDepth().intValue();

		if (depth == 0) {
			// Same depth
			// Then by parent,
			int parent = this.parent.intValue() - o.getParent().intValue();
			if (parent == 0) {
				// Same Parent, so by id
				return this.id.intValue() - o.getId().intValue();
			}
			return parent;
		} else {
			return depth;
		}
	}

}
