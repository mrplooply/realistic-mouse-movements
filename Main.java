import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Scanner;

/**
 * 
 * @author Nick
 * 
 * This application tries to mimic realistic mouse movement using
 * bezier curves.
 * 
 * TODO:
 * 	1. Make GUI
 * 		Center window
 * 		Create start delay timer
 * 		Input field for final mouse POS
 * 
 * 	2. Moving functionality
 * 		Allow more than 1 travel point
 * 		Add option to go between points continuously
 *		Allow user to take back control of the mouse
 */

public class Main {
	static volatile boolean atomicBool = false;

	public static void main(String[] args) {
		
		MouseMovingThread t1 = new MouseMovingThread();
		t1.start();	
		
		GUI gUI = new GUI();
		gUI.setUpWindow();
	}
	
	public static class MouseMovingThread extends Thread {
		@Override
		public void run() {
			BezierCurves curveController = new BezierCurves();
			curveController.moveMouseTo(new Point(1000, 800));
		}
	}
}
