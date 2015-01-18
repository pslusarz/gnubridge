package org.gnubridge.core;

public abstract class Direction {

	public static final int WEST_DEPRECATED = 0;
	public static final int NORTH_DEPRECATED = 1;
	public static final int EAST_DEPRECATED = 2;
	public static final int SOUTH_DEPRECATED = 3;

	public static final Direction WEST = West.i();
	public static final Direction NORTH = North.i();
	public static final Direction EAST = East.i();
	public static final Direction SOUTH = South.i();

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
		case WEST_DEPRECATED:
			return "West";
		case NORTH_DEPRECATED:
			return "North";
		case EAST_DEPRECATED:
			return "East";
		case SOUTH_DEPRECATED:
			return "South";
		default:
			throw new RuntimeException("Uninitialized direction");
		}
	}

	public Direction clockwise() {
		return Direction.clockwise(getValue());
	}

	private static Direction clockwise(int value) {
		switch (value) {
		case WEST_DEPRECATED:
			return North.i();
		case NORTH_DEPRECATED:
			return East.i();
		case EAST_DEPRECATED:
			return South.i();
		case SOUTH_DEPRECATED:
			return West.i();
		default:
			throw new RuntimeException("Uninitialized direction");
		}
	}

	public Direction opposite() {
		return clockwise().clockwise();
	}

}
