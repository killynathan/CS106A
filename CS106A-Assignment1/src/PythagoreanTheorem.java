import java.util.Scanner;
import java.lang.Math;

public class PythagoreanTheorem {
	public static void main (String[] args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter values to calculate hypotenuse of a triangle.");
		double hyp;
		
		System.out.print("a: ");
		int a = scan.nextInt();
		System.out.print("b: ");
		int b = scan.nextInt();
		
		hyp = Math.sqrt(Math.pow(a, 2)+Math.pow(b, 2));
		
		System.out.println("The hypetenuse of the triangle is " + hyp + " units long.");
	}
}
