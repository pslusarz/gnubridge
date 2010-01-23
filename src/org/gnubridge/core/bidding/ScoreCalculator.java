package org.gnubridge.core.bidding;

import static org.gnubridge.core.bidding.Bid.*;
import static org.gnubridge.core.deck.Trump.*;

public class ScoreCalculator {

	private int defenderPoints;
	private int declarerPoints;
	private final Bid highBid;
	private final int tricksTakenByDeclarers;
	private final Vulnerability vulnerability;

	public ScoreCalculator(Bid highBid, int tricksTakenByDeclarers, Vulnerability vulnerability) {
		this.highBid = highBid;
		this.tricksTakenByDeclarers = tricksTakenByDeclarers;
		this.vulnerability = vulnerability;
		calculateScore();
	}

	private void calculateScore() {
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
			} else if (highBid.getTrump().equals(CLUBS) || highBid.getTrump().equals(DIAMONDS)) {
				pointsPerTrick = 20;
			} else if (highBid.getTrump().equals(NOTRUMP)) {
				pointsPerTrick = 30;
				/* First trick is worth 30 for no trump */
				contractWorth = 10;
			} else {
				throw new RuntimeException("Unknown trump: " + highBid.getTrump());
			}

			if (highBid.equals(DOUBLE)) {
				contractWorth *= 2 + 50; /* +50 for the "insult" */
				pointsPerTrick *= 2;
			}

			contractWorth += bidValue * pointsPerTrick;
			int numOverTricks = tricksTakenByDeclarers - numberTricksNeededByDeclarer;
			if (numberTricksNeededByDeclarer == 13) {
				/* grand slam */
				if (vulnerability.isDeclarerVulnerable()) {
					declarerPoints = 1500;
				} else {
					declarerPoints = 1000;
				}
			} else if (numberTricksNeededByDeclarer == 12) {
				/* small slam */
				if (vulnerability.isDeclarerVulnerable()) {
					declarerPoints = 750;
				} else {
					declarerPoints = 500;
				}
			} else if (contractWorth >= 100) {
				if (vulnerability.isDeclarerVulnerable()) {
					declarerPoints = 500;
				} else {
					declarerPoints = 300;
				}
			} else {
				/* partscore */
				declarerPoints = 50;
			}

			/* Here we calculate the value of over-tricks
			 * 
			 * Vulnerability only helps the declarer if he scores over-tricks AND 
			 * was doubled
			 */
			if (highBid.equals(DOUBLE)) {
				if (vulnerability.isDeclarerVulnerable()) {
					pointsPerTrick = 200;
				} else {
					pointsPerTrick = 100;
				}
			}
			declarerPoints += contractWorth + numOverTricks * pointsPerTrick;
		} else {
			int underTricks = numberTricksNeededByDeclarer - tricksTakenByDeclarers;

			/* This will be more complicated once doubling is possibile */
			if (vulnerability.isDeclarerVulnerable()) {
				if (highBid.equals(DOUBLE)) {
					defenderPoints += 200 + (underTricks - 1) * 300;
				} else {
					defenderPoints += underTricks * 100;
				}
			} else {
				if (highBid.equals(DOUBLE)) {
					defenderPoints += 100;
					if (underTricks > 1) {
						defenderPoints += 200 + (underTricks - 2) * 300;
					}
				} else {
					defenderPoints += underTricks * 50;
				}
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
