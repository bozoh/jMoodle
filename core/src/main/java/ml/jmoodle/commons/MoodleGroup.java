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

import ml.jmoodle.annotations.MoodleConverter;
import ml.jmoodle.tools.MoodleTools;
import net.beaconhillcott.moodlerest.rest.MoodleRestGroup;

/**
 * Class to create and hold the status of a MoodleGroup object. Used in the
 * process of group creation and manipulation for courses.
 * 
 * @author Bill Antonia
 * @author Carlos Alexandre S. da Fonseca
 * @see MoodleRestGroup
 */
@MoodleConverter
public class MoodleGroup implements Serializable {

	private static final long serialVersionUID = -6398966712128615285L;

	private Long id = null;
	private Long courseid = null;
	private String name = null;
	private String description = null;
	private DescriptionFormat descriptionFormat = null;
	private String enrolmentkey = null;

	/**
	 * Constructor for bean requirements
	 */
	public MoodleGroup() {
	}

	/**
	 * <p>
	 * Constructor to create a MoodleGroup object.
	 * </p>
	 * <p>
	 * Setter method calls are needed to set other attributes of the object.
	 * </p>
	 * 
	 * @param id
	 *            Long
	 */
	public MoodleGroup(Long id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Constructor to build a basic MoodleGroup object ready to create a Moodle
	 * group.
	 * </p>
	 *
	 * @param courseid
	 *            Long
	 * @param name
	 *            String
	 */
	public MoodleGroup(Long courseid, String name) {
		this.courseid = courseid;
		this.name = name;
		this.description = "";
		this.enrolmentkey = "";
	}

	/**
	 * <p>
	 * Constructor to build a MoodleGroup object ready to create a Moodle group.
	 * </p>
	 *
	 * @param courseid
	 *            Long
	 * @param name
	 *            String
	 * @param description
	 *            String
	 */
	public MoodleGroup(Long courseid, String name, String description) {
		this.courseid = courseid;
		this.name = name;
		this.description = description;
		this.enrolmentkey = "";
	}

	/**
	 * <p>
	 * Constructor to build a MoodleGroup object ready to create a Moodle group.
	 * </p>
	 *
	 * @param courseid
	 *            Long
	 * @param name
	 *            String
	 * @param description
	 *            String
	 * @param enrolmentkey
	 *            String
	 */
	public MoodleGroup(Long courseid, String name, String description, String enrolmentkey) {
		this.courseid = courseid;
		this.name = name;
		this.description = description;
		this.enrolmentkey = enrolmentkey;
	}

	/**
	 * <p>
	 * Constructor to build a MoodleGroup object ready to create a Moodle group.
	 * <br />
	 * Probably will never get used as it needs the id of the Moodle group.
	 * </p>
	 *
	 * @param id
	 *            Long
	 * @param courseid
	 *            Long
	 * @param name
	 *            String
	 * @param description
	 *            String
	 * @param enrolmentkey
	 *            String
	 */
	public MoodleGroup(Long id, Long courseid, String name, String description, String enrolmentkey) {
		this.id = id;
		this.courseid = courseid;
		this.name = name;
		this.description = description;
		this.enrolmentkey = enrolmentkey;
	}

	/**
	 * <p>
	 * Method to set the id attribute of a MoodleGroup object.
	 * </p>
	 * <p>
	 * Probably will never get used but present due to bean requirements.
	 * </p>
	 *
	 * @param id
	 *            Long
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Method to set the courseid attribute of a MoodleGroup object.</ p>
	 * 
	 * @param courseid
	 *            Long
	 */
	public void setCourseId(Long courseid) {
		this.courseid = courseid;
	}

	/**
	 * <p>
	 * Method to set the name attribute of a MoodleGroup object.
	 * </p>
	 *
	 * @param name
	 *            String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>
	 * Method to set the description attribute of a MoodleGroup object.
	 * </p>
	 *
	 * @param description
	 *            String
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescriptionFormat(Integer descriptionFormat) {
		this.setDescriptionFormat(MoodleTools.getDescriptionFormat(descriptionFormat));
	}

	public void setDescriptionFormat(DescriptionFormat descriptionFormat) {
		this.descriptionFormat = descriptionFormat;
	}
	/**
	 * <p>
	 * Method to set the enrolmentkey attribute of a MoodleGroup object.
	 * </p>
	 *
	 * @param enrolmentkey
	 *            String
	 */
	public void setEnrolmentKey(String enrolmentkey) {
		this.enrolmentkey = enrolmentkey;
	}

	/**
	 * <p>
	 * Method to get the id attribute of a MoodleGroup object.
	 * </p>
	 *
	 * @return id Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * <p>
	 * Method to get the courseid attribute of a MoodleGroup object.
	 * </p>
	 *
	 * @return courseid Long
	 */
	public Long getCourseId() {
		return courseid;
	}

	/**
	 * <p>
	 * Method to get the name attribute of a MoodleGroup object.
	 * </p>
	 *
	 * @return name String
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>
	 * Method to get the description attribute of a MoodleGroup object.
	 * </p>
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	public DescriptionFormat getDescriptionFormat() {
		return this.descriptionFormat;
	}

	/**
	 * <p>
	 * Method to get the enrolmentkey attribute of a MoodleGroup object.
	 * </p>
	 *
	 * @return enrolmentkey String
	 */
	public String getEnrolmentKey() {
		return enrolmentkey;
	}

	@Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof MoodleGroup))
      return false;
	MoodleGroup other = (MoodleGroup) obj;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
    if (courseid == null) {
      if (other.courseid != null)
        return false;
    } else if (!courseid.equals(other.courseid))
	  return false;
	if (description == null) {
		if (other.description != null)
		  return false;
	  } else if (!description.equals(other.description))
		return false;
	if (enrolmentkey == null) {
		if (other.enrolmentkey != null)
			return false;
		} else if (!enrolmentkey.equals(other.enrolmentkey))
			return false;
	if (descriptionFormat == null) {
		if (other.descriptionFormat != null)
			return false;
		} else if (descriptionFormat != other.descriptionFormat)
			return false;
    return true;
  }
	
}
