package net.foxycorndog.jfoxylib.util;

public class Intersects
{
	public static boolean rectangles(long x1, long y1, long width1, long height1, long x2, long y2, long width2, long height2)
	{
		if (width2 <= 0 || height2 <= 0 || width1 <= 0 || height1 <= 0)
		{
			return false;
		}
		
		width2  += x2;
		height2 += y2;
		width1  += x1;
		height1 += y1;
		
		return ((width2  < x2 || width2  > x1) &&
				(height2 < y2 || height2 > y1) &&
				(width1  < x1 || width1  > x2) &&
				(height1 < y1 || height1 > y2));
	}
	
	public static boolean rectangles(double x1, double y1, double width1, double height1, double x2, double y2, double width2, double height2)
	{
		if (width2 <= 0 || height2 <= 0 || width1 <= 0 || height1 <= 0)
		{
			return false;
		}
		
		width2  += x2;
		height2 += y2;
		width1  += x1;
		height1 += y1;
		
		return ((width2  < x2 || width2  > x1) &&
				(height2 < y2 || height2 > y1) &&
				(width1  < x1 || width1  > x2) &&
				(height1 < y1 || height1 > y2));
	}
	
	public static boolean cubes(double x1, double y1, double z1, double width1, double height1, double depth1, double x2, double y2, double z2, double width2, double height2, double depth2)
	{
		width2  += x2;
		height2 += y2;
		depth2  += z2;
		width1  += x1;
		height1 += y1;
		depth1  += z1;
		
		return (width1 >= x2 && x1 <= width2) && (height1 >= y2 && y1 <= height2) && (depth1 >= z2 && z1 <= depth2);
	}
}