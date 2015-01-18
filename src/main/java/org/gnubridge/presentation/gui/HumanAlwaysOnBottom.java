package org.gnubridge.presentation.gui;

import org.gnubridge.core.Direction;
import org.gnubridge.core.South;

public class HumanAlwaysOnBottom {

	private Direction humanBase;

	public HumanAlwaysOnBottom(Direction human) {
		humanBase = human;
	}

	public Direction mapRelativeTo(Direction d) {
		Direction rotation = South.i();
		Direction slot = South.i();

		while (!rotation.equals(d)) {
			slot = slot.clockwise();
			rotation = rotation.clockwise();
		}

		Direction humanOffset = humanBase;
		while (!humanOffset.equals(South.i())) {
			slot = slot.clockwise();
			humanOffset = humanOffset.clockwise();
		}
		return slot;
	}

}
