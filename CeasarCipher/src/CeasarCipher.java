import java.util.Scanner;
import java.util.Random;

/* Ceasar Cipher encoder. Works for any character. 
 * Can shift any integer value
 */

public class CeasarCipher {
	static Random rand = new Random();
	
	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		String str;
		int shift;
		
		System.out.print("Enter a message to encode using the Ceasar Cipher: ");
		str = scan.next();
		System.out.print("Enter the number of positions to shift the message: ");
		shift = scan.nextInt();
		System.out.println(encodeString(str,shift));
	}
	
	private static String encodeString(String str, int shift) {
		String result = "";
		
		if (shift < 0) {
			shift = shift%26 + 26;
		}
		
		for (int i = 0; i < str.length(); i++) {
			char currentChar = str.charAt(i);
			
			if (Character.isLetter(currentChar)) {
				if (Character.isLowerCase(currentChar)) {
					result += (char)((((currentChar + shift) % 'a') % 26) + 'a');
				}
				else if (Character.isUpperCase(currentChar)) {
					result += (char)((((currentChar + shift) % 'A') % 26) + 'A');
				}
			}
			else {
				result += currentChar;
			}
	
		}
		
		return result;
	}	
	 	
}