package org.gnubridge.core;

public class North extends Direction {
	private static North instance;

	private North() {
	}
	
    public static North i() {
    	if (instance == null) {
    		instance = new North();
    	} 
    	return instance;
    }
    
	@Override
	public int getValue() {
		return Direction.NORTH_DEPRECATED;
	}
}
