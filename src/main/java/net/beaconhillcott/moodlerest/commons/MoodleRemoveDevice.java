/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.beaconhillcott.moodlerest.commons;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class MoodleRemoveDevice extends MoodleWarnings implements Serializable {

	private Boolean removed;

	public MoodleRemoveDevice() {
		this.removed = null;
	}

	public MoodleRemoveDevice(Boolean removed) {
		this.removed = removed;
	}

	public Boolean isRemoved() {
		return removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}
}
