package net.foxycorndog.presto2d.util;

public class Intersection
{
	public static float[] rectangles(float x1, float y1, float width1, float height1, float x2, float y2, float width2, float height2)
	{
//		width1 += x1;
//		height1 += y1;
//		width2 += x2;
//		height2 += y2;
//		
//		if (x1      > x2)      x1      = x2;
//		if (y1      > y2)      y1      = y2;
//		if (width1  < width2)  width1  = width2;
//		if (height1 < height2) height1 = height2;
//		
//		width1  -= x1;
//		height1 -= y1;
//		
//		x1 = width1  <= 0 ? 0 : x1;
//		y1 = height1 <= 0 ? 0 : y1;
		
		float x      = 0;
		float y      = 0;
		float width  = 0;
		float height = 0;
		
		x = x1 > x2 ? x1 : x2;
		y = y1 > y2 ? y1 : y2;
		
		width  = x1 > x2 ? x2 + width2  - x1 : x1 + width1  - x2;
		height = y1 > y2 ? y2 + height2 - y1 : y1 + height1 - y2;
		
		return new float[] { x, y, width, height};
	}
}