import acm.program.*;
import acm.graphics.*;
import java.awt.Color;

public class Target extends GraphicsProgram {
	public void run() {
		double centerX = getWidth()/2.0;
		double centerY = getHeight()/2.0;
		
		double bigCircleRadius = 72;
		double medCircleRadius = 72 * 0.65;
		double smlCircleRadius = 72 * 0.3;
		
		GOval bigCircle = new GOval(centerX - bigCircleRadius, centerY - bigCircleRadius, 
									bigCircleRadius * 2, bigCircleRadius * 2);
		GOval medCircle = new GOval(centerX - medCircleRadius, centerY - medCircleRadius, 
									medCircleRadius * 2, medCircleRadius * 2);
		GOval smlCircle = new GOval(centerX - smlCircleRadius, centerY - smlCircleRadius, 
									smlCircleRadius * 2, smlCircleRadius * 2);
		
		bigCircle.setFilled(true);
		bigCircle.setColor(Color.red);
		medCircle.setFilled(true);
		medCircle.setColor(Color.white);
		smlCircle.setFilled(true);
		smlCircle.setColor(Color.red);
		
		add (bigCircle);
		add (medCircle);
		add (smlCircle);
	}
}
