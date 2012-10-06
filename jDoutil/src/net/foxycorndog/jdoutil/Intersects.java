package net.foxycorndog.jdoutil;

public class Intersects
{
	public static boolean rectangles(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2)
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
}