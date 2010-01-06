package org.gnubridge.core.bidding;

import static org.gnubridge.core.deck.Trump.CLUBS;
import static org.gnubridge.core.deck.Trump.DIAMONDS;
import static org.gnubridge.core.deck.Trump.HEARTS;
import static org.gnubridge.core.deck.Trump.NOTRUMP;
import static org.gnubridge.core.deck.Trump.SPADES;

public class ScoreCalculator {
	
	private int defenderPoints;
	private int declarerPoints;

	public ScoreCalculator(Bid highBid, int tricksTakenByDeclarers, boolean isDeclarerVulnerable) {
		calculateScore(highBid, tricksTakenByDeclarers, isDeclarerVulnerable);
	}

	public void calculateScore(Bid highBid, int tricksTakenByDeclarers, boolean isDeclarerVulnerable) {
		int bidValue = highBid.getValue();
		int numberTricksNeededByDeclarer = bidValue + 6;
		
		/* We calculate how many points the declarer won, or alternatively
		 * 	how much the defenders won if the declarer did not meet his contract
		 * 
		 * This will be more complicated once the game supports doubles, but it
		 * should be pretty straight forward for now
		 * 
		 * TODO: Support doubles 
		 */
		declarerPoints = 0;
		defenderPoints = 0;
		if (tricksTakenByDeclarers >= numberTricksNeededByDeclarer) {
			int pointsPerTrick = 30;
			int contractWorth = 0;
			if (highBid.getTrump().equals(HEARTS) || highBid.getTrump().equals(SPADES)) {
				pointsPerTrick = 30;
			}
			else if (highBid.getTrump().equals(CLUBS) || highBid.getTrump().equals(DIAMONDS)) {
				pointsPerTrick = 20;
			}
			else if (highBid.getTrump().equals(NOTRUMP)) {
				pointsPerTrick = 30;
				/* First trick is worth 30 for no trump */
				contractWorth = 10;
			}
			else {
				throw new RuntimeException("Unknown trump: " + highBid.getTrump());
			}
			contractWorth += bidValue * pointsPerTrick;
			int numOverTricks = tricksTakenByDeclarers - numberTricksNeededByDeclarer;
			if (numberTricksNeededByDeclarer == 13) {
				/* grand slam */
				declarerPoints = 1000;
			}
			else if (numberTricksNeededByDeclarer == 12) {
				/* small slam */
				declarerPoints = 500;
			}
			else if (contractWorth >= 100) {
				declarerPoints = 300;
			}
			
			/* Here we calculate the value of over-tricks
			 * 
			 * Vulnerability only helps the declarer if he scores over-tricks AND 
			 * was doubled - since we do not yet support doubling, we can ignore
			 * vulnerability here for now
			 */
			declarerPoints += contractWorth + numOverTricks * pointsPerTrick;
		}
		else {
			int underTricks = numberTricksNeededByDeclarer - tricksTakenByDeclarers;
			
			/* This will be more complicated once doubling is possibile */
			if (isDeclarerVulnerable) {
				defenderPoints += underTricks * 100;
			}
			else {
				defenderPoints += underTricks * 50;
			}
		}
	}
	
	public int getDeclarerScore() {
		return declarerPoints;
	}
	
	public int getDefenderScore() {
		return defenderPoints;
	}
}
