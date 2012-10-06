/**
 * File:          Rectangle.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 6Sep2012
 * Description:   Class that holds information of a Rectangle.
 */

public class Rectangle
{
	private double x, y;
	private double width, height;

	/**
	 * Instantiates Rectangle with x, y, width, and height.
	 */
	public Rectangle(double x, double y, double width, double height)
	{
		this.x      = x;
		this.y      = y;
		this.width  = width;
		this.height = height;
	}

	/**
	 * Returns whether the rectangles intersect.
	 */
	public boolean intersects(Rectangle r)
	{
		double x1 = x;
		double y1 = y;
		double width1 = width;
		double height1 = height;
		double x2 = r.x;
		double y2 = r.y;
		double width2 = r.width;
		double height2 = r.height;

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