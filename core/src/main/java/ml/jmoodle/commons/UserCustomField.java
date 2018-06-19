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
public class UserCustomField implements Serializable {
  
  private static final long serialVersionUID = 5195135695368910138L;


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
  
  private CustomFieldType type=null;
  private String value=null;
  private String name=null;
  private String shortname=null;
  
  /**
   *
   */
  public UserCustomField() {}
  
  /**
   *
   * @param type
   * @param name
   * @param value
   * @param shortname
   */
  public UserCustomField(String type,  String name, String value, String shortname) {
    setType(type);
    this.value=value;
    this.name=name;
    this.shortname=shortname;
  }

  public UserCustomField(CustomFieldType type, String name, String value, String shortname) {
    this.type = type;
    this.value=value;
    this.name=name;
    this.shortname=shortname;
  }
  
  /**
   *
   * @return
   */
  public CustomFieldType getType() {
    return type;
  }
  
  /**
   *
   * @return
   */
  public String getValue() {
    return value;
  }
  
  /**
   *
   * @return
   */
  public String getName() {
    return name;
  }
  
  /**
   *
   * @return
   */
  public String getShortname() {
    return shortname;
  }
  
  /**
   * 
   * @param type
   */
  public void setType(String type) {
    if (type.equalsIgnoreCase(CustomFieldType.CHECKBOX.getValue()))
      setType(CustomFieldType.CHECKBOX);
    else if (type.equalsIgnoreCase(CustomFieldType.DATETIME.getValue()))
      setType(CustomFieldType.DATETIME);
    else if (type.equalsIgnoreCase(CustomFieldType.MENU.getValue()))
      setType(CustomFieldType.MENU);
    else if (type.equalsIgnoreCase(CustomFieldType.TEXT.getValue()))
      setType(CustomFieldType.TEXT);
    else if (type.equalsIgnoreCase(CustomFieldType.TEXTAREA.getValue()))
      setType(CustomFieldType.TEXTAREA);
  }

  public void setType(CustomFieldType type) {
    this.type=type;
  }
  
  
  /**
   *
   * @param value
   */
  public void setValue(String value) {
    this.value=value;
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
  public void setShortname(String shortname) {
    this.shortname=shortname;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof UserCustomField))
      return false;
    UserCustomField other = (UserCustomField) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (type != other.type)
      return false;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    if (shortname == null) {
      if (other.shortname != null)
        return false;
    } else if (!shortname.equals(other.shortname))
      return false;

    return true;
  }
 
}
