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
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers]; //start from 0
		totalScores = new int[nPlayers + 1];//since players start at 1. leave empty element at 0
		upperScores = new int[nPlayers + 1];
		lowerScores = new int[nPlayers + 1];
		
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
					for (int nthDice = 0; nthDice < N_DICE; nthDice++) {
						if (display.isDieSelected(nthDice)) {
							dice[nthDice] = rgen.nextInt(5) + 1;
						}
					}
					display.displayDice(dice);
				}
				
				display.printMessage("Pick a category.");
				int category = display.waitForPlayerToSelectCategory();
				
				int points = checkPoints(category, dice);
				updateScores(playerNumber, points, category);
				
				
			}
		}
	}
	
	private void updateScores(int playerNumber, int points, int category) {
		display.updateScorecard(category, playerNumber, points);
		
		totalScores[playerNumber] += points;
		display.updateScorecard(TOTAL, playerNumber, totalScores[playerNumber]);
		
		if (category < UPPER_SCORE) {
			upperScores[playerNumber] += points;
			display.updateScorecard(UPPER_SCORE, playerNumber, upperScores[playerNumber]);
		}
		else {
			lowerScores[playerNumber] += points;
			display.updateScorecard(LOWER_SCORE, playerNumber, lowerScores[playerNumber]);
		}		
	}
	
	private void rollAllDice(int[] dice) {
		for (int i = 0; i < dice.length; i++) {
			dice[i] = rgen.nextInt(5) + 1;
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
			if (checkThreeOfAKind(dice)) {
				for (int i = 0; i < N_DICE; i++) {
					points += dice[i];
				}
			}
			return points;
		case FOUR_OF_A_KIND:
			if (checkFourOfAKind(dice)) {
				for (int i = 0; i < N_DICE; i++) {
					points += dice[i];
				}
			}
			return points;
		case FULL_HOUSE:
			
		case SMALL_STRAIGHT:
			
		case LARGE_STRAIGHT:
			
		case YAHTZEE:
			
		case CHANCE:
			
		default:
			return points;
		}	
	}
	
	private boolean checkThreeOfAKind(int[] dice) {
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
	
	private boolean checkFourOfAKind(int[] dice) {
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
	
		
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private int[] totalScores;
	private int[] lowerScores;
	private int[] upperScores;
	private int[] dice;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int playerTurn = 1;

}