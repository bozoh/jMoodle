/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ml.jmoodle.commons;

import java.io.Serializable;

import ml.jmoodle.annotations.MoodleConverter;

/**
 *
 * @author Carlos Alexandre S. da Fonsca
 * 
 */
@MoodleConverter
public class MoodleEnrolmentMethod implements Serializable {

	private static final long serialVersionUID = -2181748793893707876L;

	private Long id = null;
	private Long courseId = null;
	private String type = null;
	private String name = null;
	private String status = null;
	private String wsFunction = null;


	
	public MoodleEnrolmentMethod() {
	}
	
	public MoodleEnrolmentMethod(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCourseId() {
		return courseId;
	}
	
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getWsFunction() {
		return wsFunction;
	}
	
	public void setWsFunction(String wsFunction) {
		this.wsFunction = wsFunction;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MoodleEnrolmentMethod))
			return false;
		MoodleEnrolmentMethod other = (MoodleEnrolmentMethod) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
	
		if (courseId == null) {
			if (other.courseId != null)
				return false;
		} else if (!courseId.equals(other.courseId))
			return false;
	
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
	
		if (wsFunction == null) {
			if (other.wsFunction != null)
				return false;
		} else if (!wsFunction.equals(other.wsFunction))
			return false;
	
		return true;
	}
	
}
