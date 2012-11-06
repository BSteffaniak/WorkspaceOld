import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * File:          ArrayThing.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 6Nov2012
 * Description:   Class that tests the time of big O of n.
 */

public class ArrayThing
{
	private int randomArray[];
	
	private int target;
	
	/**
	 * The main method that performs the tests.
	 * @param args
	 */
	public static void main(String args[])
	{
		ArrayThing thang;
		
		long nanos = 0;
		
		// Point 1
		{
			nanos = System.nanoTime();
			
			thang = new ArrayThing();
			
			System.out.println(System.nanoTime() - nanos);
		}
		
		// Point 2
		{
			nanos = System.nanoTime();
		
			thang.sort();
			
			System.out.println(System.nanoTime() - nanos);
		}
		
		thang.setTarget();

		// Point 3
		{
			nanos = System.nanoTime();
		
			thang.targetInArray();
			
			System.out.println(System.nanoTime() - nanos);
		}
	}
	
	/**
	 * Creates a random array of however many integers.
	 */
	public ArrayThing()
	{
		randomArray = new int[10000000];
		
		for (int i = 0; i < randomArray.length; i ++)
		{
			randomArray[i] = (int)(Math.random() * randomArray.length);
		}
	}
	
	/**
	 * Sorts the random array from least to greatest.
	 */
	public void sort()
	{
		// loops through all of the values of the array
		for (int j = 0; j < randomArray.length; j ++)
		{
			// loops through all of the values of the array
			// to see if it is greater than the current value
			for (int a = 0; a < randomArray.length - j; a ++)
			{
				// checks whether the first value is greater than
				// the value it is currently looping through in the
				// nested loop
				if (randomArray[j] > randomArray[a + j])
				{
					// creates a variable to hold the value of the
					// number that will be switched to.
					int temp = randomArray[j + a];

					// sets the value of the variable in the nested
					// loop to the value of the variable in the outer loop
					randomArray[a + j] = randomArray[j];

					// sets the value of the variable in the outer loop
					// to the value of the temporary variable
					randomArray[j] = temp;
				}
			}
		}
	}
	
	/**
	 * Writes the specified target to the data.txt file.
	 * 
	 * @param target The integer to store in the file.
	 */
	private void writeTarget(int target)
	{
		write("data.txt", target);
	}
	
	/**
	 * Gets the target from the data.txt file.
	 * 
	 * @return The integer received from the file.
	 */
	private int getTarget()
	{
		return Integer.valueOf((String)read("data.txt"));
	}
	
	/**
	 * Set the target field to the target in the file.
	 */
	public void setTarget()
	{
		target = getTarget();
	}
	
	/**
	 * Returns whether the target is in the array.
	 * 
	 * @return True of false whether the target is there.
	 */
	public boolean targetInArray()
	{
		return binarySearch(target);
	}
	
	/**
	 * Searches through the array for the specified target. Uses
	 * a binary search.
	 * 
	 * @param target The target to search for.
	 * @return Whether the target is in the array.
	 */
	public boolean binarySearch(int target)
	{
		int leftI   = 0;
		int rightI  = randomArray.length - 1;
		int middleI = 0;
		
		while (rightI > leftI + 1)
		{
			middleI = (leftI + rightI) / 2;
			
			if (randomArray[middleI] > target)
			{
				rightI = middleI;
			}
			else if (randomArray[middleI] < target)
			{
				leftI = middleI;
			}
			else
			{
				return true;
			}
		}
		
		return false;
	}
	
//	private void readFromFile()
//	{
//		randomArray = (int[])read("arrayFile.dat");
//
//		System.out.println("Read: ");
////		for (int i = 0; i < randomArray.length; i ++)
////		{
////			System.out.println(randomArray[i]);
////		}
//	}
	
//	private void writeToFile()
//	{
//		randomArray = new int[100000];
//		
//		for (int i = 0; i < randomArray.length; i ++)
//		{
//			randomArray[i] = (int)(Math.random() * 10);
//		}
//		
//		write("arrayFile.dat", randomArray);
//		
////		System.out.println("Wrote: ");
////		for (int i = 0; i < randomArray.length; i ++)
////		{
////			System.out.println(randomArray[i]);
////		}
//	}
	
	/**
	 * Gets the first object from the file at the specified
	 * location.
	 * 
	 * @param location The location of the file.
	 * @return The first object from the file.
	 */
	private static Object read(String location)
	{
		Object obj = null;
		
		try
		{
			ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(
					new FileInputStream(location)));
		
			obj = in.readObject();
			
			in.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			
		}
		
		return obj;
	}
	
	/**
	 * Writes the object to the specified file at the specified
	 * location.
	 * 
	 * @param location The location of the file to write to.
	 * @param obj The object to write to the file.
	 */
	private static void write(String location, Object obj)
	{
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(
					new BufferedOutputStream(
					new FileOutputStream(location)));
			
			out.writeObject(obj);
			
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}