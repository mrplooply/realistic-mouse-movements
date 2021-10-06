import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.concurrent.TimeUnit;


public class BezierCurves {
	
	Robot robot;
	
	public BezierCurves() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			System.err.println("Could not create robot.");
			e.printStackTrace();
		}
	}
	
 	public void cubicCurve(Point start, Point end) {
		try {
			Robot robot = new Robot();
			
			Point point2 = new Point(856, 768);
			Point point3 = new Point(376, 273);
			float i = 0;
			
			while (i <= 1) {
				double finalX = Math.pow((1 - i), 3) * start.x + Math.pow((1 - i), 2) *
		                	 	3 * i * point2.x + (1 - i) * 3 * Math.pow(i, 2) * point3.x +
		                	 	Math.pow(i, 3) * end.x;
				double finalY = Math.pow((1 - i), 3) * start.y + Math.pow((1 - i), 2) *
	               	 			3 * i * point2.y + (1 - i) * 3 * Math.pow(i, 2) * point3.y +
	               	 			Math.pow(i, 3) * end.y;
				int x = (int)Math.round(finalX);
				int y = (int)Math.round(finalY);
				robot.mouseMove(x, y);
				i += 0.0001;
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void multiCurve(Point[] points) {
		int midx, midy;
		Point p0, p1;
		
		for (int i = 1; i < points.length - 2; i++) {
			p0 = points[i];
			p1 = points[i + 1];
			
			midx = (p0.x + p1.x) / 2;
			midy = (p0.y + p1.y) / 2;
			
			
			quadraticCurve(p0.x, p0.y, midx, midy, robot);
		}
		p0 = points[points.length - 2];
		p1 = points[points.length - 1];
		
		if (Main.atomicBool == true) {
			return;
		}
		
		quadraticCurve(p0.x, p0.y, p1.x, p1.y, robot);
	}
	
	public void quadraticCurve(int p1X, int p1Y, int p2X, int p2Y, Robot robot) {
		double t = 0;
		
		Point p0 = MouseInfo.getPointerInfo().getLocation();
		while (t <= 1) {
			double finalX = Math.pow((1 - t), 2) * p0.x + 2 * (1 - t) * t * p1X +
							Math.pow(t, 2) * p2X;
	
			double finalY = Math.pow((1 - t), 2) * p0.y + 2 * (1 - t) * t * p1Y +
							Math.pow(t, 2) * p2Y;
			
			int x = (int)Math.round(finalX);
			int y = (int)Math.round(finalY);
			
			int randomWiggleX = randomRange(-10, 10);
			if (randomWiggleX <= 2 && randomWiggleX >= -2) {
				x = x + randomRange(-2, 2);
			}
			int randomWiggleY = randomRange(-10, 10);
			if (randomWiggleY <= 2 && randomWiggleY >= -2) {
				x = x + randomRange(-2, 2);
			}
			
			if (t < .0009 && t > .0001) {
				if (randomRange(0, 10) <= 4) {
					if (randomRange(0, 1) < 3) {
						t += 0.0001;
					}
					else {
						t -= 0.0001;
					}
				}
			}
			if (Main.atomicBool == true) {
				return;
			}
			System.out.println(Main.atomicBool);
			robot.mouseMove(x, y);
			t += 0.0005;
		}
		
	}
	
	public int chooseNumberOfPoints(int distance) {
		
		if (distance > 300 && distance < 700) {
			return 2;
		}
		else if (distance > 700 ) {
			return 3;
		}
		else {
			return 1;
		}
	}
	
	public Point[] makePoints(int numberOfPoints, Point startPos, Point endPos) {
		
		switch (numberOfPoints) {
		case 1:
			
			Point[] point4 = new Point[4];
			if (startPos.x >= endPos.x && startPos.y >= endPos.y) {
				
				int midX = (startPos.x - endPos.x) / 2 + endPos.x;
				int midY = (startPos.y - endPos.y) / 2 + endPos.y;
				
				point4[0] = startPos;
				point4[1] = new Point(findRandomMidPoint(midX, midY));		
				point4[2] = new Point(findRandomEndPoint(endPos.x, endPos.y));
				point4[3] = endPos;
				
				return point4;
			}
			else if (startPos.x >= endPos.x && startPos.y <= endPos.y) {
				
				int midX = (startPos.x - endPos.x) / 2 + endPos.x;
				int midY = (endPos.y - startPos.y) / 2 + startPos.y;
				
				point4[0] = startPos;
				point4[1] = new Point(findRandomMidPoint(midX, midY));		
				point4[2] = new Point(findRandomEndPoint(endPos.x, endPos.y));
				point4[3] = endPos;
				
				return point4;
			}
			else if (startPos.x <= endPos.x && startPos.y <= endPos.y) {
				
				int midX = (endPos.x - startPos.x) / 2 + startPos.x;
				int midY = (endPos.y - startPos.y) / 2 + startPos.y;
				
				point4[0] = startPos;
				point4[1] = new Point(findRandomMidPoint(midX, midY));		
				point4[2] = new Point(findRandomEndPoint(endPos.x, endPos.y));
				point4[3] = endPos;
				
				return point4;
			}
			else if (startPos.x <= endPos.x && startPos.y >= endPos.y) {
				
				int midX = (endPos.x - startPos.x) / 2 + startPos.x;
				int midY = (startPos.y - endPos.y) / 2 + endPos.y;
				
				point4[0] = startPos;
				point4[1] = new Point(findRandomMidPoint(midX, midY));		
				point4[2] = new Point(findRandomEndPoint(endPos.x, endPos.y));
				point4[3] = endPos;
				
				return point4;
			}
			break;
			
		case 2:
			
			Point[] point5 = new Point[5];
			if (startPos.x >= endPos.x && startPos.y >= endPos.y) {
				
				int firstMidX = startPos.x - (startPos.x - endPos.x) / 3;
				int firstMidY = ((startPos.y - endPos.y) / 3) * 2 + endPos.y;
				int secondMidX = ((startPos.x - endPos.x) / 3) + endPos.x;
				int secondMidY = ((startPos.y - endPos.y) / 3) + endPos.y;
				
				point5[0] = startPos;
				point5[1] = new Point(findRandomMidPoint(firstMidX, firstMidY));
				point5[2] = new Point(findRandomMidPoint(secondMidX, secondMidY));
				point5[3] = new Point(findRandomEndPoint(endPos.x, endPos.y));
				point5[4] = endPos;
				
				return point5;
			}
			else if (startPos.x >= endPos.x && startPos.y <= endPos.y) {
				
				int firstMidX = startPos.x - (startPos.x - endPos.x) / 3;
				int firstMidY = (endPos.y - startPos.y) / 3 + startPos.y;
				int secondMidX = ((startPos.x - endPos.x) / 3) + endPos.x;
				int secondMidY = ((endPos.y - startPos.y) / 3) * 2 + startPos.y;
				
				point5[0] = startPos;
				point5[1] = new Point(findRandomMidPoint(firstMidX, firstMidY));
				point5[2] = new Point(findRandomMidPoint(secondMidX, secondMidY));
				point5[3] = new Point(findRandomEndPoint(endPos.x, endPos.y));
				point5[4] = endPos;
				
				return point5;
			}
			else if (startPos.x <= endPos.x && startPos.y <= endPos.y) {
				
				int firstMidX = (endPos.x - startPos.x) / 3 + startPos.x;
				int firstMidY = (endPos.y - startPos.y) / 3 + startPos.y;
				int secondMidX = ((endPos.x - startPos.x) / 3) * 2 + startPos.x;
				int secondMidY = ((endPos.y - startPos.y) / 3) * 2 + startPos.y;
				
				point5[0] = startPos;
				point5[1] = new Point(findRandomMidPoint(firstMidX, firstMidY));
				point5[2] = new Point(findRandomMidPoint(secondMidX, secondMidY));
				point5[3] = new Point(findRandomEndPoint(endPos.x, endPos.y));
				point5[4] = endPos;
				
				return point5;
			}
			else if (startPos.x <= endPos.x && startPos.y >= endPos.y) {
				
				int firstMidX = (endPos.x - startPos.x) / 3 + startPos.x;
				int firstMidY = ((startPos.y - endPos.y) / 3 * 2) + endPos.y;
				int secondMidX = ((endPos.x - startPos.x) / 3) * 2 + startPos.x;
				int secondMidY = ((startPos.y - endPos.y) / 3) + endPos.y;
				
				point5[0] = startPos;
				point5[2] = new Point(findRandomMidPoint(secondMidX, secondMidY));
				point5[1] = new Point(findRandomMidPoint(firstMidX, firstMidY));
				point5[3] = new Point(findRandomEndPoint(endPos.x, endPos.y));
				point5[4] = endPos;
				
				return point5;
			}
			break;
		case 3:
			Point[] point6 = new Point[6];
			if (startPos.x >= endPos.x && startPos.y >= endPos.y) {
				
				int firstMidX = startPos.x - (startPos.x - endPos.x) / 4;
				int firstMidY = startPos.y - (startPos.y - endPos.y) / 4;
				int secondMidX = startPos.x - ((startPos.x - endPos.x) / 4) * 2;
				int secondMidY = startPos.y - ((startPos.y - endPos.y) / 4) * 2;
				int thirdMidX = startPos.x - ((startPos.x - endPos.x) / 4) * 3;
				int thirdMidY = startPos.y - ((startPos.y - endPos.y) / 4) * 3;
				
				point6[0] = startPos;
				point6[1] = new Point(findRandomMidPoint(firstMidX, firstMidY));
				point6[2] = new Point(findRandomMidPoint(secondMidX, secondMidY));
				point6[3] = new Point(findRandomMidPoint(thirdMidX, thirdMidY));
				point6[4] = new Point(findRandomEndPoint(endPos.x, endPos.y));
				point6[5] = endPos;
				
				return point6;
			}
			else if (startPos.x >= endPos.x && startPos.y <= endPos.y) {
				
				int firstMidX = startPos.x - (startPos.x - endPos.x) / 4;
				int firstMidY = startPos.y + (endPos.y - startPos.y) / 4;
				int secondMidX = startPos.x - ((startPos.x - endPos.x) / 4) * 2;
				int secondMidY = startPos.y + ((endPos.y - startPos.y) / 4) * 2;
				int thirdMidX = startPos.x - ((startPos.x - endPos.x) / 4) * 3;
				int thirdMidY = startPos.y + ((endPos.y - startPos.y) / 4) * 3;
				
				point6[0] = startPos;
				point6[1] = new Point(findRandomMidPoint(firstMidX, firstMidY));
				point6[2] = new Point(findRandomMidPoint(secondMidX, secondMidY));
				point6[3] = new Point(findRandomMidPoint(thirdMidX, thirdMidY));
				point6[4] = new Point(findRandomEndPoint(endPos.x, endPos.y));
				point6[5] = endPos;
				
				return point6;
			}
			else if (startPos.x <= endPos.x && startPos.y <= endPos.y) {
				
				int firstMidX = startPos.x - (startPos.x - endPos.x) / 4;
				int firstMidY = startPos.y + (endPos.y - startPos.y) / 4;
				int secondMidX = startPos.x - ((startPos.x - endPos.x) / 4) * 2;
				int secondMidY = startPos.y + ((endPos.y - startPos.y) / 4) * 2;
				int thirdMidX = startPos.x - ((startPos.x - endPos.x) / 4) * 3;
				int thirdMidY = startPos.y + ((endPos.y - startPos.y) / 4) * 3;
				
				point6[0] = startPos;
				point6[1] = new Point(findRandomMidPoint(firstMidX, firstMidY));
				point6[2] = new Point(findRandomMidPoint(secondMidX, secondMidY));
				point6[3] = new Point(findRandomMidPoint(thirdMidX, thirdMidY));
				point6[4] = new Point(findRandomEndPoint(endPos.x, endPos.y));
				point6[5] = endPos;
				
				return point6;
			}
			else if (startPos.x <= endPos.x && startPos.y >= endPos.y) {
				
				int firstMidX = startPos.x - (startPos.x - endPos.x) / 4;
				int firstMidY = startPos.y + (endPos.y - startPos.y) / 4;
				int secondMidX = startPos.x - ((startPos.x - endPos.x) / 4) * 2;
				int secondMidY = startPos.y + ((endPos.y - startPos.y) / 4) * 2;
				int thirdMidX = startPos.x - ((startPos.x - endPos.x) / 4) * 3;
				int thirdMidY = startPos.y + ((endPos.y - startPos.y) / 4) * 3;
				
				point6[0] = startPos;
				point6[1] = new Point(findRandomMidPoint(firstMidX, firstMidY));
				point6[2] = new Point(findRandomMidPoint(secondMidX, secondMidY));
				point6[3] = new Point(findRandomMidPoint(thirdMidX, thirdMidY));
				point6[4] = new Point(findRandomEndPoint(endPos.x, endPos.y));
				point6[5] = endPos;
				
				return point6;
			}
			break;

		default:
			System.err.println("Failed to create mid points");
			return new Point[0];
		}
		System.err.println("Failed to create mid points");
		return new Point[0];
	}
	
	public Point findRandomMidPoint(int midX, int midY) {
		int middlePointsSquareX = 35;
		
		int midMinX = midX - middlePointsSquareX;
		int midMaxX = midX + middlePointsSquareX;
		int midMinY = midY - middlePointsSquareX;
		int midMaxY = midY + middlePointsSquareX;
		
		int randX = (int)(Math.random() * (midMaxX - midMinX) + (midMinX));
		int randY = (int)(Math.random() * (midMaxY - midMinY) + (midMinY));
		
		return new Point(randX, randY);
	}
	
	public Point findRandomEndPoint(int finalX, int finalY) {
		int finalPointSquareX = 20;
		
		int finalMinX = finalX - finalPointSquareX;
		int finalMaxX = finalX + finalPointSquareX;
		int finalMinY = finalY - finalPointSquareX;
		int finalMaxY = finalY + finalPointSquareX;
		
		int randX = (int)(Math.random() * (finalMaxX - finalMinX) + (finalMinX));
		int randY = (int)(Math.random() * (finalMaxY - finalMinY) + (finalMinY));
		
		return new Point(randX, randY);
	}
	
	public void moveMouseTo(Point finalPoint) {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Point currentPos = MouseInfo.getPointerInfo().getLocation();
		
		int distance = calculateDistance(currentPos, finalPoint);
		int numOfPoints = chooseNumberOfPoints(distance);
		Point[] points = makePoints(numOfPoints, currentPos, finalPoint);
		multiCurve(points);
		
	}
	
	public int calculateDistance(Point start, Point end) {
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
		int distance = (int)Math.round(distanceFromDestination);
		return distance;
	}
	
	public int randomRange(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

}
