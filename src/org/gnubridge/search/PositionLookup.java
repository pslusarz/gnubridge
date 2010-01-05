package org.gnubridge.search;

import java.util.Map;
import java.util.WeakHashMap;

import org.gnubridge.core.Deal;

public class PositionLookup {

	Map<String, byte[]> positions;
	private Deal lastGameLookedUp;
	private byte[] lastNode;

	public PositionLookup() {
		try {
			positions = new WeakHashMap<String, byte[]>(20000000, 0.5f);
		} catch (OutOfMemoryError e) {
			positions = new WeakHashMap<String, byte[]>();
		}
	}

	public boolean positionEncountered(Deal g, byte[] bs) {
		if (g.getCurrentTrick().getHighestCard() != null) {
			return false;
		}
		byte[] valueToReturn = getNode(g);
		if (valueToReturn == null) {
			putNode(g, bs);
			return false;
		}
		return true;
	}

	public byte[] getNode(Deal g) {
		if (g == lastGameLookedUp) {
			return lastNode;
		}
		byte[] result = positions.get(g.getKeyForWeakHashMap());
		if (result != null) {
			lastGameLookedUp = g;
			lastNode = result;
		}
		return result;
	}

	private void putNode(Deal g, byte[] value) {
		positions.put(g.getKeyForWeakHashMap(), value);
	}

}
