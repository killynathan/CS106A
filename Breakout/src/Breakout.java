/*
 * File: Breakout.java
 * -------------------
 * This file will eventually implement the game of Breakout.
 */
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Breakout extends GraphicsProgram {
	
	/** CLASS VARIABLES */
	
	/** Animation delay */
	private static final int DELAY = 10;
	
	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;
	
	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;
	
	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;
	
	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;
	
	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;
	
	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;
	
	/** Separation between bricks */
	private static final int BRICK_SEP = 4;
	
	/** Width of a brick */
	private static final int BRICK_WIDTH = 
	(WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
	
	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;
	
	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;
	
	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;
	
	/** Number of turns */
	private static final int NTURNS = 3;
	
	/** Random */
	private static Random rand = new Random();
	
	/** ------------------------------------------------------------------------------------------ */
	
	
	/** INSTANCE VARIABLES */
	
	/** Paddle */
	private GRect paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
	
	/** Ball */
	private GOval ball = new GOval(BALL_RADIUS, BALL_RADIUS);
	
	/** Ball velocities */
	private double dx, dy;
	
	/** Game condition */
	private boolean isGameRunning = true;
	
	/** Win or lose */
	private boolean isWin = false;
	
	/** Start the game! */
	public void run() {
		//setting the size of the game 
		setSize(WIDTH,HEIGHT);
		pause(10);
		
		//set up the objects in the game
		setup();
		addMouseListeners();
		
		//run game
		runGame();
	}
	
	private void setup() {
		setupBricks();
		setupPaddle();
		setupBall();
	}
	
	private void setupBricks() {
		int currentX = 0;
		int currentY = BRICK_Y_OFFSET;
		
		for (int i = 0; i < NBRICK_ROWS; i++) {
			for (int j = 0; j < NBRICKS_PER_ROW; j++) {
				GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				
				if (i < 2) {
					brick.setColor(Color.red);
				}
				else if (i < 4) {
					brick.setColor(Color.orange);
				}
				else if (i < 6) {
					brick.setColor(Color.yellow);
				}
				else if (i < 8) {
					brick.setColor(Color.green);
				}
				else {
					brick.setColor(Color.cyan);
				}
				
				add(brick, currentX, currentY);
				currentX += BRICK_WIDTH + BRICK_SEP;
			}
			currentY += BRICK_HEIGHT + BRICK_SEP;
			currentX = 0;
		}
	}
	
	private void setupPaddle() {
		double paddleXPos = (WIDTH - PADDLE_WIDTH)/2.0;
		double paddleYPos = HEIGHT - PADDLE_Y_OFFSET;
			
		paddle.setFilled(true);
		add (paddle, paddleXPos, paddleYPos);
	}
	
	private void setupBall() {
		int ballXPos = (WIDTH - BALL_RADIUS)/2;
		int ballYPos = (BRICK_HEIGHT + BRICK_SEP) * NBRICK_ROWS + BRICK_Y_OFFSET;
		
		ball.setFilled(true);
		add (ball, ballXPos, ballYPos);
		
		dx = rand.nextDouble() * 2 + 1;
		if (rand.nextBoolean()) {
			dx = -dx;
		}
		dy = 3;
	}
	
	private void runGame() {
		while (isGameRunning) {
			moveBall();
			checkForCollision();
			checkForWin();
			pause(DELAY);
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		int xLoc; 
		
		if (e.getX() > WIDTH - PADDLE_WIDTH) {
			xLoc = WIDTH - PADDLE_WIDTH;
		}
		else {
			xLoc = e.getX();
		}
		paddle.setLocation(xLoc, HEIGHT - PADDLE_Y_OFFSET);
	}
	
	private void moveBall() {
		ball.move(dx, dy);
	}
	
	private void checkForCollision() {
		//walls
		checkForWalls();
		
		//paddle and bricks
		checkForPaddleOrBrick();
		
	}
	
	private void checkForWalls() {
		//right wall
		if (ball.getX() > (WIDTH - BALL_RADIUS)) {
			dx = -dx;
		}
		//top wall
		if (ball.getY() < 0) {
			dy = -dy;
		}
		//left wall
		if (ball.getX() < 0) {
			dx = -dx;
		}
		//bottom
		if (ball.getY() > HEIGHT - BALL_RADIUS) {
			isWin = false;
			isGameRunning = false;
		}
	}
	
	private void checkForPaddleOrBrick() {
		if (getElementAt(ball.getX(), ball.getY() + BALL_RADIUS) != null) { 
			dy = -dy;
			
			GObject obj = getElementAt(ball.getX(), ball.getY() + BALL_RADIUS);
			if (obj != paddle) {
				remove(obj);
			}
		}
		else if (getElementAt(ball.getX() + BALL_RADIUS, ball.getY() + BALL_RADIUS) != null) {
			dy = -dy;
			
			GObject obj = getElementAt(ball.getX() + BALL_RADIUS, ball.getY() + BALL_RADIUS);
			if (obj != paddle) {
				remove(obj);
			}
		}
		else if (getElementAt(ball.getX(), ball.getY()) != null) {
			dy = -dy;
			
			GObject obj = getElementAt(ball.getX(), ball.getY());
			if (obj != paddle) {
				remove(obj);
			}
		}
		else if (getElementAt(ball.getX() + BALL_RADIUS, ball.getY()) != null) {
			dy = -dy;
			
			GObject obj = getElementAt(ball.getX() + BALL_RADIUS, ball.getY());
			if (obj != paddle) {
				remove(obj);
			}
		
		}
		
	}
	
	/** check for end game */
	private void checkForWin() {
		if (getElementCount() < 3) {
			isGameRunning = false;
			isWin = true;
		}
		
		if  (!isGameRunning) {
			if (isWin) {
				GLabel loseLabel = new GLabel("You Win");
				add(loseLabel, (WIDTH - loseLabel.getWidth()) / 2, (HEIGHT - loseLabel.getHeight()) / 2);
			}
			else {
				GLabel loseLabel = new GLabel("You Lose");
				add(loseLabel, (WIDTH - loseLabel.getWidth()) / 2, (HEIGHT - loseLabel.getHeight()) / 2);
			}
		}
	}
} 