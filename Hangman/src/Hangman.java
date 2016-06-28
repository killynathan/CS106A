import acm.program.*;
import acm.io.*;
import java.util.Scanner;

public class Hangman extends ConsoleProgram{
	
	private static Scanner scan = new Scanner(System.in);
	
	private static HangmanLexicon lex = new HangmanLexicon();
	
	public void run() {
		String answer = lex.getWord();
		int numOfGuesses = 8;
		int length = answer.length();
		String currentWord = generateDashes(length);
		char guess;
		
		String test = "asdf";
		println(test.indexOf('l'));
		
		println(answer);
		println(currentWord);
		println("You have " + numOfGuesses + " left");
		guess = readGuess();
		println("Your guess was incorrect");
		
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
					return guess;
				}
				
			}
			println("Please enter a letter.");
			
		}
	}
	
	
}
