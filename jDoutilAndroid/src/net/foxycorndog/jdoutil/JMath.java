package net.foxycorndog.jdoutil;

public class JMath
{
	/**
	* Get the distance to and from two points.
	* 
	* @param x1 The x coordinate of the first point.
	* @param y1 The y coordinate of the first point.
	* @param x2 The x coordinate of the second point.
	* @param y2 The y coordinate of the second point.
	* @return The distance from each of the points.
	*/
	public static float distanceTo(float x1, float y1, float x2, float y2)
	{
		return (float)(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
	}
}