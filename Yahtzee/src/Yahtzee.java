/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public void init() {
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
	}
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		while (true) {
			nPlayers = dialog.readInt("Enter number of players");
			if (nPlayers <= 4 && nPlayers >=1) {
				break;
			}
		}
		playerNames = new String[nPlayers]; //start from 0
		totalScores = new int[nPlayers + 1];//since players start at 1. leave empty element at 0
		upperScores = new int[nPlayers + 1];
		lowerScores = new int[nPlayers + 1];
		isFilled = new boolean[nPlayers + 1][N_SCORING_CATEGORIES + 1];
		
		dice = new int[N_DICE];
		
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		
		playGame();
	}

	private void playGame() {
		for (int roundNumber = 1; roundNumber <= N_SCORING_CATEGORIES; roundNumber++) {
			for (int playerNumber = 1; playerNumber <= nPlayers; playerNumber++) {
				display.printMessage("round: " + roundNumber + ", " + 
									playerNames[playerNumber - 1] + "\'s turn.");
				display.waitForPlayerToClickRoll(playerNumber);
				rollAllDice(dice);
				display.displayDice(dice);
				display.printMessage(playerNames[playerNumber - 1] + " rolled.");
				
				for (int i = 0; i < 2; i++) {
					display.printMessage(playerNames[playerNumber - 1] + "\'s " + (i + 2) + " roll.");
					display.waitForPlayerToSelectDice();
					rollSomeDice(dice);
					display.displayDice(dice);
				}
				
				display.printMessage("Pick a category.");
				int category = getCategory(playerNumber);
				
				int points = checkPoints(category, dice);
				updateScores(playerNumber, points, category);
				
				
			}
		}
		gameOver();
	}
	
	private int getCategory (int playerNumber) {
		int category = display.waitForPlayerToSelectCategory();
		while (true) {
			if (!isFilled[playerNumber][category]) {
				isFilled[playerNumber][category] = true;
				break;
			}
			category = display.waitForPlayerToSelectCategory();
		}
		return category;
	}
	
	
	private void gameOver() {
		int index = findIndexOfHighestScore();
		display.printMessage("Game Over. " + playerNames[index - 1] + " wins.");	
	}
	
	private int findIndexOfHighestScore() {
		int index = 1;
		for (int i = 2; i <= nPlayers; i++) {
			if (totalScores[i] > totalScores[index]) {
				index = i;
			}
		}
		return index;
	}
	
	private void updateScores(int playerNumber, int points, int category) {
		display.updateScorecard(category, playerNumber, points);
		
		totalScores[playerNumber] += points;
		display.updateScorecard(TOTAL, playerNumber, totalScores[playerNumber]);
		
		if (category < UPPER_SCORE) {
			upperScores[playerNumber] += points;
			display.updateScorecard(UPPER_SCORE, playerNumber, upperScores[playerNumber]);
			if (hasBonus(playerNumber)) {
				display.updateScorecard(UPPER_BONUS, playerNumber, 35);
				display.updateScorecard(TOTAL, playerNumber, totalScores[playerNumber] + 35);
			}
		}
		else {
			lowerScores[playerNumber] += points;
			display.updateScorecard(LOWER_SCORE, playerNumber, lowerScores[playerNumber]);
		}	
	}
	
	private boolean hasBonus(int playerNumber) {
		if (upperScores[playerNumber] >= 63) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private void rollAllDice(int[] dice) {
		for (int i = 0; i < dice.length; i++) {
			dice[i] = rgen.nextInt(1,6);
		}
	}
	
	private void rollSomeDice(int[] dice) {
		for (int nthDice = 0; nthDice < N_DICE; nthDice++) {
			if (display.isDieSelected(nthDice)) {
				dice[nthDice] = rgen.nextInt(1,6);
			}
		}
	}
	
	private int checkPoints(int category, int[] dice) {
		int points = 0;
		switch(category) {
		case ONES: 
			for (int i = 0; i < N_DICE; i++) {
				if (dice[i] == 1) {
					points += 1;
				}
			}
			return points;
		case TWOS: 
			for (int i = 0; i < N_DICE; i++) {
				if (dice[i] == TWOS) {
					points += TWOS;
				}
			}
			return points;
		case THREES: 
			for (int i = 0; i < N_DICE; i++) {
				if (dice[i] == THREES) {
					points += THREES;
				}
			}
			return points;
		case FOURS: 
			for (int i = 0; i < N_DICE; i++) {
				if (dice[i] == FOURS) {
					points += FOURS;
				}
			}
			return points;
		case FIVES: 
			for (int i = 0; i < N_DICE; i++) {
				if (dice[i] == FIVES) {
					points += FIVES;
				}
			}
			return points;
		case SIXES: 
			for (int i = 0; i < N_DICE; i++) {
				if (dice[i] == SIXES) {
					points += SIXES;
				}
			}
			return points;
		case THREE_OF_A_KIND:
			if (isThreeOfAKind(dice)) {
				for (int i = 0; i < N_DICE; i++) {
					points += dice[i];
				}
			}
			return points;
		case FOUR_OF_A_KIND:
			if (isFourOfAKind(dice)) {
				for (int i = 0; i < N_DICE; i++) {
					points += dice[i];
				}
			}
			return points;
		case FULL_HOUSE:
			if (isFullHouse(dice)) {
				points += 25;
			}
			return points;
		case SMALL_STRAIGHT:
			if (isSmallStraight(dice)) {
				points += 30;
			}
			return points;
		case LARGE_STRAIGHT:
			if (isLargeStraight(dice)) {
				points += 40;
			}
			return points;
		case YAHTZEE:
			if (isYahtzee(dice)) {
				points += 50;
			}
			return points;
		case CHANCE:
			for (int i = 0; i < N_DICE; i++) {
				points += dice[i];
			}
			return points;		
		default:
			return points;
		}	
	}
	
	private boolean isThreeOfAKind(int[] dice) {
		int count = 0;
		for (int i = 0; i < N_DICE; i++) {
			for (int j = 0; j < N_DICE; j++) {
				if (dice[i] == dice[j]) {
					count++;
				}
			}
			if (count >= 3) {
				return true;
			}
			count = 0;
		}
		return false;
	}
	
	private boolean isFourOfAKind(int[] dice) {
		int count = 0;
		for (int i = 0; i < N_DICE; i++) {
			for (int j = 0; j < N_DICE; j++) {
				if (dice[i] == dice[j]) {
					count++;
				}
			}
			if (count >= 4) {
				return true;
			}
			count = 0;
		}
		return false;
	}
	
	private boolean isFullHouse(int[] dice) {
		int c1 = 0;
		int c2 = 0;
		int indexOfSecondNumber = -1;
		for (int i = 0; i < N_DICE; i++) {
			if (dice[i] == dice[0]) {
				c1++;
			}
		}
		for (int i = 1; i < N_DICE; i++) {
			if (dice[i] != dice[0]) {
				indexOfSecondNumber = i;
				break;
			}
		}
		if (indexOfSecondNumber == -1) {
			return false;
		}
		for (int i = 0; i < N_DICE; i++) {
			if (dice[i] == dice[indexOfSecondNumber]) {
				c2++;
			}
		}
		if ((c1 == 2 && c2 == 3) || (c1 == 3 && c2 == 2)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean isSmallStraight(int[] dice) {
		boolean hasOne = false;
		boolean hasTwo = false;
		boolean hasThree = false;
		boolean hasFour = false;
		boolean hasFive = false;
		boolean hasSix = false;
		
		for (int i = 0; i < N_DICE; i++) {
			switch(dice[i]) {
			case 1:
				hasOne = true;
				break;
			case 2:
				hasTwo = true;
				break;
			case 3:
				hasThree = true;
				break;
			case 4:
				hasFour = true;
				break;
			case 5:
				hasFive = true;
				break;
			case 6:
				hasSix = true;
				break;
			}
		}
		if (hasOne && hasTwo && hasThree && hasFour) {
			return true;
		}
		else if (hasTwo && hasThree && hasFour && hasFive) {
			return true;
		}
		else if (hasThree && hasFour && hasFive && hasSix) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean isLargeStraight(int[] dice) {
		boolean hasOne = false;
		boolean hasTwo = false;
		boolean hasThree = false;
		boolean hasFour = false;
		boolean hasFive = false;
		boolean hasSix = false;
		
		for (int i = 0; i < N_DICE; i++) {
			switch(dice[i]) {
			case 1:
				hasOne = true;
				break;
			case 2:
				hasTwo = true;
				break;
			case 3:
				hasThree = true;
				break;
			case 4:
				hasFour = true;
				break;
			case 5:
				hasFive = true;
				break;
			case 6:
				hasSix = true;
				break;
			}
		}
		if (hasOne && hasTwo && hasThree && hasFour && hasFive) {
			return true;
		}
		else if (hasTwo && hasThree && hasFour && hasFive && hasSix) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean isYahtzee(int[] dice) {
		for (int i = 1; i < N_DICE; i++) {
			if (dice[i] != dice[0]) {
				return false;
			}
		}
		return true;
	}
	
		
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private int[] totalScores;
	private int[] lowerScores;
	private int[] upperScores;
	private int[] dice;
	private boolean[][] isFilled;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int playerTurn = 1;

}