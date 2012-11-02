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

public class ArrayThing
{
	private int randomArray[];
	
	private int target;
	
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

		// Point 3
		{
			thang.printArray();
			
			nanos = System.nanoTime();
		
			thang.targetInArray();
			
			System.out.println(System.nanoTime() - nanos);
		}
	}
	
	public void printArray()
	{
		for (int i = 0; i < randomArray.length; i ++)
		{
			System.out.println(randomArray[i]);
		}
	}
	
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
	
	public ArrayThing()
	{
//		System.out.println(System.currentTimeMillis());
//		System.out.println(System.nanoTime());
		randomArray = new int[10];
		
		for (int i = 0; i < randomArray.length; i ++)
		{
			randomArray[i] = (int)(Math.random() * 10);
		}
//		writeToFile();
//		readFromFile();
	}
	
	private void writeTarget(int target)
	{
		write("data.txt", 34);
	}
	
	private int getTarget()
	{
		return Integer.valueOf((String)read("data.txt"));
	}
	
	public boolean targetInArray()
	{
		target = getTarget();
		
		return binarySearch(6) != -1;
	}
	
	public int binarySearch(int target)
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
				System.out.println(target + " at " + middleI);
				return middleI;
			}
		}
		
		System.out.println("no target");
		
		return -1;
	}
	
	private void readFromFile()
	{
		randomArray = (int[])read("arrayFile.dat");

		System.out.println("Read: ");
//		for (int i = 0; i < randomArray.length; i ++)
//		{
//			System.out.println(randomArray[i]);
//		}
	}
	
	private void writeToFile()
	{
		randomArray = new int[100000];
		
		for (int i = 0; i < randomArray.length; i ++)
		{
			randomArray[i] = (int)(Math.random() * 10);
		}
		
		write("arrayFile.dat", randomArray);
		
//		System.out.println("Wrote: ");
//		for (int i = 0; i < randomArray.length; i ++)
//		{
//			System.out.println(randomArray[i]);
//		}
	}
	
	private static Object read(String location)
	{
		Object obj = null;
		
		try
		{
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(location)));
		
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
	
	private static void write(String location, Object obj)
	{
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(location)));
			
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