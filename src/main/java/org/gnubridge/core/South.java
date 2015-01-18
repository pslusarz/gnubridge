package org.gnubridge.core;

public class South extends Direction {
	private static South instance;

	private South() {
	}
	
    public static South i() {
    	if (instance == null) {
    		instance = new South();
    	} 
    	return instance;
    }
    
	@Override
	public int getValue() {
		return Direction.SOUTH_DEPRECATED;
	}
}
