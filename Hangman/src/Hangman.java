import acm.program.*;
import acm.io.*;
import java.util.Scanner;

public class Hangman extends ConsoleProgram{
	
	private static Scanner scan = new Scanner(System.in);
	
	private HangmanLexicon lex = new HangmanLexicon();
	
	private HangmanCanvas canvas;
	
	private boolean stillPlaying = true;
	
	public void init() {
		setSize(800,600);
		canvas = new HangmanCanvas();
		add(canvas);
	}
	
	public void run() {
		
		while (stillPlaying) {
			
			String answer = lex.getWord();
			int numOfGuesses = 8;
			int length = answer.length();
			String currentState = generateDashes(length);
			char guess;
			
			canvas.reset();
			canvas.displayWord(currentState);
			
			while (true) {
				println(answer);
				println("         " + currentState);
				println("You have " + numOfGuesses + " tries left");
				guess = readGuess();
				if (guessIsCorrect(guess, answer)) {
					println("Your guess was correct!");
					currentState = updateCurrentState(guess, answer, currentState);
					canvas.displayWord(currentState);
					if (won(currentState, answer)) {
						println("         " + currentState);
						println("You Win!");
						break;
					}
				}
				else {
					println("Your guess was incorrect.");
					numOfGuesses--;
					canvas.noteIncorrectGuess(guess, numOfGuesses);
					if (numOfGuesses <= 0) {
						println("You Lose.");
						break;
					}
				}
			}
			
			stillPlaying = readBoolean("Do you want to play another game (true/false)?");
			if (stillPlaying) {
				println("");
				println("");
				println("");
				println("");
				println("");
			}
			else {
				println("Thanks for playing!");
			}
		}
			
	}
	
	private String generateDashes(int length) {
		String result = "";
		
		for (int i = 0; i < length; i++) {
			result += "-";
		}
		
		return result;
	}
	
	private char readGuess() {
		char guess;
		while (true) {
			String str = readLine("Guess a letter: ");
			if (str.length() == 1) {
				guess = str.charAt(0);
				if (Character.isLetter(guess)) {
					return Character.toUpperCase(guess);
				}
				
			}
			println("Please enter a letter.");
			
		}
	}
	
	private boolean guessIsCorrect(char guess, String answer) {
		if (answer.indexOf(guess) != -1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private String updateCurrentState(char ch, String answer, String currentState) {
		int index = answer.indexOf(ch);
		String result = "";
		
		for (int i = 0; i < answer.length(); i++) {
			if (answer.charAt(i) == ch) {
				result += answer.charAt(i);
			}
			else {
				result += currentState.charAt(i);
			}
		}
		
		return result;
	}
	
	
	private boolean won(String currentState, String answer) {
		if (currentState.equals(answer)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
}
