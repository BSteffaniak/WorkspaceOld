package net.foxycorndog.jdoutil;

public class Distance
{
	public static float points(float x1, float y1, float x2, float y2)
	{
		return (float)Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
	}
}