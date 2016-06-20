import acm.program.*;
import acm.graphics.*;

public class brickPyramid extends GraphicsProgram {
	public void run() {
		int BRICK_WIDTH = 30;
		int BRICK_HEIGHT = 12;
		int BRICK_IN_BASE = 14;
		
		int totalLengthOfBase = BRICK_WIDTH * BRICK_IN_BASE;
		double rowStartX = getWidth()/2.0 - totalLengthOfBase/2.0;
		double rowStartY = getHeight()-BRICK_HEIGHT;
		
		for (int i = BRICK_IN_BASE; i > 0; i--) {
			double brickLocX = rowStartX;
			for (int j = 0; j < i; j++) {
				add (new GRect(brickLocX, rowStartY, BRICK_WIDTH, BRICK_HEIGHT));
				brickLocX += BRICK_WIDTH;
			}
			rowStartY -= BRICK_HEIGHT;
			rowStartX += BRICK_WIDTH/2;
		}
		
	}
	
	//private static void layRow(int _rowStartX, int _rowStartY, int numOfBricks, )
}