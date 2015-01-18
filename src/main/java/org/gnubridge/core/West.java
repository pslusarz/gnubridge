package org.gnubridge.core;

public class West extends Direction {

	private static West instance;

	private West() {
	}
	
    public static West i() {
    	if (instance == null) {
    		instance = new West();
    	} 
    	return instance;
    }

	@Override
	public int getValue() {
		return Direction.WEST_DEPRECATED;
	}
}
