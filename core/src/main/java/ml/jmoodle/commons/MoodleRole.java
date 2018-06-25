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
 * @author Carlos Alexandre S. da Fonseca
 */
@MoodleConverter
public final class MoodleRole implements Serializable {
  
  private static final long serialVersionUID = 195374818236206288L;

  private Long roleid=null;
  private String name=null;
  private String shortname=null;
  private Integer sortorder=null;
  
  /**
   *
   */
  public MoodleRole() {}
  
  /**
   *
   * @param roleid
   * @param name
   * @param shortname
   * @param sortorder
   */
  public MoodleRole(long roleid, String name, String shortname, int sortorder) {
    this.roleid = roleid;
    //this.roleid=roleid;
    this.name=name;
    this.shortname=shortname;
    this.sortorder=sortorder;
  }
   
  
  /**
   *
   * @return Long
   */
  public Long getRoleId() {
    return this.roleid;
    //return roleid;
  }
  
  /**
   * 
   * @return String
   */
  public String getName() {
    return name;
  }
  
  /**
   *
   * @return String
   */
  public String getShortName() {
    return shortname;
  }
  
  /**
   *
   * @return Integer
   */
  public Integer getSortOrder() {
    return sortorder;
  }
  
  /**
   *
   * @param roleid
 * @throws MoodleUserRoleException 
   */
  public void setRoleId(Long roleid) {
    this.roleid = roleid;
  }
  
  /**
   *
   * @param name
   */
  public void setName(String name) {
    this.name=name;
  }
  
  /**
   *
   * @param shortname
   */
  public void setShortName(String shortname) {
    this.shortname=shortname;
  }
  
  /**
   *
   * @param sortorder
   */
  public void setSortOrder(Integer sortorder) {
    this.sortorder=sortorder;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof MoodleRole))
      return false;
		MoodleRole other = (MoodleRole) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (shortname == null) {
			if (other.shortname != null)
				return false;
		} else if (!shortname.equals(other.shortname))
      return false;
    if (roleid == null) {
      if (other.roleid != null)
        return false;
    } else if (!roleid.equals(other.roleid))
      return false;
    return true;
  }

}
