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
public class MoodleRestCohortException extends MoodleRestException implements Serializable {

	public static final String INCONSISTENT_DATA_PARSE = "Returned data not in correct order";

	public MoodleRestCohortException(String string) {
	}

	public MoodleRestCohortException() {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

}
