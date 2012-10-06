package net.foxycorndog.jdoutilandroid;

import java.util.ArrayList;

public class ArrayUtil
{
	public static <E> boolean contains(E array[], Object value)
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
	
	public static boolean containsInteger(int array[], int value)
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
	
	public static <E> boolean contains(GeneralCollection<E> array, Object value)
	{
		ArrayList<E> elements = array.getElements();
		
		for (int i = 0; i < elements.size(); i ++)
		{
			if (elements.get(i).equals(value))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static <E> boolean contains(ArrayList<E> array, Object value)
	{
		for (int i = 0; i < array.size(); i ++)
		{
			if (array.get(i).equals(value))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static String[] toStringArray(ArrayList<String> array)
	{
		String arr[] = new String[array.size()];
		
		for (int i = 0; i < array.size(); i ++)
		{
			arr[i] = array.get(i);
		}
		
		return arr;
	}
	
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
	
	public static void setData(int offset, double src[], double dst[])
	{
		for (int i = offset; i < src.length; i ++)
		{
			dst[i + offset] = src[i];
		}
	}
	
//	public static boolean contains(float array[], float value)
//	{
//		for (int i = 0; i < array.length; i ++)
//		{
//			if (array[i] == value)
//			{
//				return true;
//			}
//		}
//		
//		return false;
//	}
//	
//	public static boolean contains(short array[], short value)
//	{
//		for (int i = 0; i < array.length; i ++)
//		{
//			if (array[i] == value)
//			{
//				return true;
//			}
//		}
//		
//		return false;
//	}
//	
//	public static boolean contains(Point array[], Point value)
//	{
//		for (int i = 0; i < array.length; i ++)
//		{
//			if (array[i].equals(value))
//			{
//				return true;
//			}
//		}
//		
//		return false;
//	}
	
	public static float[] toFloatArray(ArrayList<Float> floats)
	{
		float returner[] = new float[floats.size()];
		
		for (int i = 0; i < returner.length; i ++)
		{
			returner[i] = floats.get(i);
		}
		
		return returner;
	}
	
	
}
