/*
* File: HangmanCanvas.java
* ------------------------
* This file keeps track of the Hangman display.
*/
import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	
	private String badGuesses = "";
	
	/** Resets the display so that only the scaffold appears */
	public void reset() {
		/* You fill this in */
		removeAll();
		addScaffold();
		badGuesses = "";
	}
	
	private void addScaffold() {
		add(new GLine(85, 100, 85, 460));
		add(new GLine(85, 100, 229, 100));
		add(new GLine(229, 100, 229, 118));
	}
	
	/**
	* Updates the word on the screen to correspond to the current
	* state of the game. The argument string shows what letters have
	* been guessed so far; unguessed letters are indicated by hyphens.
	*/
	public void displayWord(String word) {
		/* You fill this in */
		if (getElementAt(85, 550) != null) {
			remove(getElementAt(85, 550));
		}
		GLabel label = new GLabel(word, 85, 550);
		label.setFont("*-*-30");
		add(label);
	}
	
	/**
	* Updates the display to correspond to an incorrect guess by the
	* user. Calling this method causes the next body part to appear
	* on the scaffold and adds the letter to the list of incorrect
	* guesses that appears at the bottom of the window.
	*/
	public void noteIncorrectGuess(char letter, int numOfGuesses) {
		//System.out.println(badGuesses.indexOf(letter));
		//if (badGuesses.indexOf(letter) > -1) {
			badGuesses += letter;
		//}
		if (getElementAt(85, 570) != null) {
			remove(getElementAt(85, 570));
		}
		GLabel label = new GLabel(badGuesses, 87, 570);
		label.setFont("*-*-15");
		add(label);
		
		switch (numOfGuesses) {
		case 7: add(new GOval(72, 72), 193, 118); //head
				break;
		case 6: add(new GLine(229, 190, 229, 334)); //body
				break;
		case 5: add(new GLine(227, 218, 155, 218)); //left arm
				add(new GLine(155, 218, 155, 262));
				break;
		case 4: add(new GLine(229, 218, 301, 218)); //right arm
				add(new GLine(301, 218, 301, 262));
				break;
		case 3: add(new GLine(229, 334, 193, 334)); //left hip
				add(new GLine(193, 334, 193, 442)); //left leg
				break;
		case 2: add(new GLine(227, 334, 263, 334)); //right hip
				add(new GLine(263, 334, 263, 442)); //right leg
				break;
		case 1: add(new GLine(193, 442, 165, 442)); //left foot
				break;
		case 0: add(new GLine(263, 442, 291, 442)); //right foot
				break;
		}
	}
	
	/* Constants for the simple version of the picture (in pixels) */
	private double WIDTH = this.getWidth();
	private double HEIGHT = this.getHeight();
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	}
