import java.awt.Robot;
import java.awt.AWTException;
import java.awt.Point;

public class MouseController {
	static final int SLOW = 15;
	public static final int MEDIUM = 10;
	public static final int FAST = 5;
	
	public void linearMove(Point start, Point end) {
		try {
			Robot robot = new Robot();
			float xLength;
			float yLength;
			float xStep;
			float yStep;
			
			//Calculate the distances between the points
			if (start.x > end.x) {
				xLength = start.x - end.x;
			}
			else {
				xLength = end.x - start.x;
			}
			
			if (start.y > end.y) {
				yLength = start.y - end.y;
			}
			else {
				yLength = end.y - start.y;
			}
			
			//Calculate the size of the step that needs to be taken for X and Y
			if (xLength > yLength) {
				yStep = yLength / xLength;
				xStep = 1;
			}
			else {
				xStep = xLength / yLength;
				yStep = 1;
			}
			
			takeLinearStep(start, end, xStep, yStep, robot);
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void takeLinearStep(Point start, Point end, float xStep, float yStep, Robot robot) {
		Point move = new Point();
		
		float unroundedX = (float) start.x;
		float unroundedY = (float) start.y;
		xStep = xStep / MEDIUM;
		yStep = yStep /  MEDIUM;
		double startingDistance = calculateDistance(start, end);
		boolean isCloseToEnd = true;
		
		if (start.x >= end.x && start.y >= end.y) {
			while (move.x != end.x || move.y != end.y) {
				unroundedX = unroundedX - xStep;
				unroundedY = unroundedY - yStep;
				
				move.x = Math.round(unroundedX);
				move.y = Math.round(unroundedY);
				
				
				if (calculateDistance(move, end) / startingDistance < .05 && isCloseToEnd == true) {
					xStep = xStep / SLOW;
					yStep = yStep /  SLOW;
					isCloseToEnd = false;
				}

				robot.mouseMove(move.x, move.y);
			}
		}
		else if (start.x <= end.x && start.y <= end.y) {
			while (move.x != end.x || move.y != end.y) {
				unroundedX = unroundedX + xStep;
				unroundedY = unroundedY + yStep;
				
				move.x = Math.round(unroundedX);
				move.y = Math.round(unroundedY);
				
				if (calculateDistance(move, end) / startingDistance < .05 && isCloseToEnd == true) {
					xStep = xStep / SLOW;
					yStep = yStep /  SLOW;
					isCloseToEnd = false;
				}

				robot.mouseMove(move.x, move.y);
			}
		}
		else if (start.x <= end.x && start.y >= end.y) {
			while (move.x != end.x || move.y != end.y) {
				unroundedX = unroundedX + xStep;
				unroundedY = unroundedY - yStep;
				
				move.x = Math.round(unroundedX);
				move.y = Math.round(unroundedY);
				
				if (calculateDistance(move, end) / startingDistance < .05 && isCloseToEnd == true) {
					xStep = xStep / SLOW;
					yStep = yStep /  SLOW;
					isCloseToEnd = false;
				}

				robot.mouseMove(move.x, move.y);
			}
		}
		else if (start.x >= end.x && start.y <= end.y) {
			while (move.x != end.x || move.y != end.y) {
				unroundedX = unroundedX - xStep;
				unroundedY = unroundedY + yStep;
				
				move.x = Math.round(unroundedX);
				move.y = Math.round(unroundedY);
				
				if (calculateDistance(move, end) / startingDistance < .05 && isCloseToEnd == true) {
					xStep = xStep / SLOW;
					yStep = yStep /  SLOW;
					isCloseToEnd = false;
				}
				
				robot.mouseMove(move.x, move.y);
			}
		}
	}
	
	public double calculateDistance(Point start, Point end) {
		int xLength;
		int yLength;
		if (start.x > end.x) {
			xLength = start.x - end.x;
		}
		else {
			xLength = end.x - start.x;
		}
		
		if (start.y > end.y) {
			yLength = start.y - end.y;
		}
		else {
			yLength = end.y - start.y;
		}
		double distanceFromDestination = Math.sqrt(Math.pow(xLength, 2) + Math.pow(yLength, 2));
		return distanceFromDestination;
	}
}
