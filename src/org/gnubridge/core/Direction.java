package org.gnubridge.core;

public abstract class Direction {

	public static final int WEST = 0;
	public static final int NORTH = 1;
	public static final int EAST = 2;
	public static final int SOUTH = 3;

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

	@Override
	public String toString() {
		switch (getValue()) {
		case WEST:
			return "West";
		case NORTH:
			return "North";
		case EAST:
			return "East";
		case SOUTH:
			return "South";
		default:
			throw new RuntimeException("Uninitialized direction");
		}
	}

}
