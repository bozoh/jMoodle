/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.beaconhillcott.moodlerest.commons;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class MoodleEnrolmentMethod implements Serializable {

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

	public void setFieldValue(String name, String value) {
		if (value != null) {
			if (name.equals("id") || name.equals("courseid")) {
				if (!value.isEmpty()) {
					if (name.equals("id"))
						setId(Long.parseLong(value));
					if (name.equals("courseid"))
						setCourseId(Long.parseLong(value));
				}
			} else {
				if (name.equals("type") || name.equals("name") || name.equals("status") || name.equals("wsfunction")) {
					if (name.equals("type"))
						setType(value);
					if (name.equals("name"))
						setName(value);
					if (name.equals("status"))
						setStatus(value);
					if (name.equals("wsfunction"))
						setWsFunction(value);
				}
			}
		}
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

}
