package net.foxycorndog.presto2d.util;

import java.awt.Point;

public class ArrayUtil
{
	public static boolean equals(float array1[], float array2[])
	{
		if (array1.length != array2.length) return false;
		
		for (int i = 0; i < array1.length; i ++)
		{
			if (array1[i] != array2[i]) return false;
		}
		
		return true;
	}
	
	public static void setData(int offset, float src[], float dst[])
	{
		for (int i = offset; i < src.length; i ++)
		{
			dst[i + offset] = src[i];
		}
	}
	
	public static boolean contains(float array[], float value)
	{
		for (int i = 0; i < array.length; i ++)
		{
			if (array[i] == value)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean contains(short array[], short value)
	{
		for (int i = 0; i < array.length; i ++)
		{
			if (array[i] == value)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean contains(Point array[], Point value)
	{
		for (int i = 0; i < array.length; i ++)
		{
			if (array[i].equals(value))
			{
				return true;
			}
		}
		
		return false;
	}
}