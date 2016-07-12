/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.beaconhillcott.moodlerest.rest.exception;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class MoodleRestCalendarException extends MoodleRestException implements Serializable {

	public static final String NO_LEGACY_CALL = "No legacy call";
	public static final String INCONSISTENT_DATA_PARSE = "Returned data not in correct order";

	public MoodleRestCalendarException() {
	}

	public MoodleRestCalendarException(String msg) {
		super(msg);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
