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

/**
 *
 * @author Bill Antonia
 * @author Carlos Alexandre S. da Fonseca
 */
@MoodleConverter
public class UserEnrolledCourse implements Serializable {
  
  private static final long serialVersionUID = 1204568095030999599L;
  private Long id=null;
  private String fullname=null;
  private String shortname=null;
  
  public UserEnrolledCourse() {}
  
  public UserEnrolledCourse(Long id) {
    this.id=id;
  }
  
  public UserEnrolledCourse(Long id, String fullname, String shortname) {
    this.id=id;
    this.fullname=fullname;
    this.shortname=shortname;
  }
  
  public Long getId() {
    return id;
  }
  
  public String getFullName() {
    return fullname;
  }
  
  public String getShortName() {
    return shortname;
  }
  
  public void setId(Long id) {
    this.id=id;
  }
  
  public void setFullName(String fullname) {
    this.fullname=fullname;
  }
  
  public void setShortName(String shortname) {
    this.shortname=shortname;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof UserEnrolledCourse))
      return false;
		UserEnrolledCourse other = (UserEnrolledCourse) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (fullname == null) {
			if (other.fullname != null)
				return false;
		} else if (!fullname.equals(other.fullname))
      return false;
    if (shortname == null) {
      if (other.shortname != null)
        return false;
    } else if (!shortname.equals(other.shortname))
      return false;
    return true;
  }
  
}
