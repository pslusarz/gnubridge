package org.gnubridge.core;

public abstract class Direction {

	public abstract int getValue();

	public static Direction instance(int direction) {
		Direction result = null;
		if (West.i().getValue() == direction) {
			result = West.i();
		} else if (North.i().getValue() == direction) {
			result = North.i();
		} else if (East.i().getValue() == direction) {
			result = East.i();
		} else if (South.i().getValue() == direction) {
			result = South.i();
		}
		return result;
	}

}
