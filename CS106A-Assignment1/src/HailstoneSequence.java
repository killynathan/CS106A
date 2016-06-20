import java.util.Scanner;

public class HailstoneSequence {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int n;
		int counter = 0;
		
		System.out.print("Enter a number to be sent into the Hailstone Sequence: ");
		n = scan.nextInt();
		
		while (n != 1) {
			if (n % 2 == 0) {
				int temp = n;
				n /= 2;
				System.out.println(temp + " is even so it is halved: " + n);
				counter++;
			}
			else {
				int temp = n;
				n = 3 * n + 1;
				System.out.println(temp + " is odd so it is tripled and incremented by 1: " + n);
				counter++;
			}
		}
		
		System.out.println("The process took " + counter + " steps to reach one.");
	}
}
