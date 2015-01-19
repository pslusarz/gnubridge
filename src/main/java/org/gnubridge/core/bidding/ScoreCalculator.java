package org.gnubridge.core.bidding;

import org.gnubridge.core.deck.*;

public class ScoreCalculator {

	private static final int INSULT_BONUS = 50;
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
		declarerPoints = 0;
		defenderPoints = 0;
		if (tricksTakenByDeclarers >= numberTricksNeededByDeclarer) {
			int pointsPerTrick = 30;
			int contractWorth = 0;
			if (highBid.getTrump().equals(Hearts.i()) || highBid.getTrump().equals(Spades.i())) {
				pointsPerTrick = 30;
			} else if (highBid.getTrump().equals(Clubs.i()) || highBid.getTrump().equals(Diamonds.i())) {
				pointsPerTrick = 20;
			} else if (highBid.getTrump().equals(NoTrump.i())) {
				pointsPerTrick = 30;
				/* First trick is worth 30 for no trump */
				contractWorth = 10;
			} else {
				throw new RuntimeException("Unknown trump: " + highBid.getTrump());
			}

			if (highBid.isDoubled()) {
				contractWorth *= 2 + INSULT_BONUS;
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
			if (highBid.isDoubled()) {
				if (vulnerability.isDeclarerVulnerable()) {
					pointsPerTrick = 200;
				} else {
					pointsPerTrick = 100;
				}
			}
			declarerPoints += contractWorth + numOverTricks * pointsPerTrick;
		} else {
			int underTricks = numberTricksNeededByDeclarer - tricksTakenByDeclarers;

			if (vulnerability.isDeclarerVulnerable()) {
				if (highBid.isDoubled()) {
					defenderPoints += 200 + (underTricks - 1) * 300;
				} else {
					defenderPoints += underTricks * 100;
				}
			} else {
				if (highBid.isDoubled()) {
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
