import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * File:          SortThing.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 18Nov2012
 * Description:   Class that demonstrates the insertion and
 * selection sorting methods. Fried Chicken.
 */
public class SortThing
{
	private Comparable rArr[];
	private List<Integer> list;
	
	/**
	 * The main method used for testing.
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		SortThing s = new SortThing();
		
		s.list = s.genRandomList(100);
		
		try
		{
			s.print(s.list, null, "Before insertion sort: ");
			
			s.insertionSort(s.list);
			
			s.print(s.list, null, "\nAfter insertion: ");
			
			s.rArr = s.genRandomArray(100);
			
			s.print(null, s.rArr, "\nBefore selection sort: ");

			s.selectionSort(s.rArr);

			s.print(null, s.rArr, "\nAfter selection sort: ");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * THIS IS A CONSTRUCTOR!!!!!!!!!!!!!!!!!!
	 */
	public SortThing()
	{
		
	}
	
	/**
	 * Generates random integers for an array
	 * 
	 * @param length
	 * @return
	 */
	private Comparable[] genRandomArray(int length)
	{
		Comparable arr[] = new Comparable[length];
		
		for (int i = 0; i < length; i ++)
		{
			arr[i] = (Integer)(int)(Math.random() * 100);
		}
		
		return arr;
	}
	
	/**
	 * Generates random integers for a list.
	 * 
	 * @param length
	 * @return
	 */
	private List<Integer> genRandomList(int length)
	{
		List<Integer> arr = new ArrayList<Integer>();
		
		for (int i = 0; i < length; i ++)
		{
			arr.add(i, (Integer)(int)(Math.random() * 100));
		}
		
		return arr;
	}
	
	/**
	 * Method that prints the array to the console and a text file.
	 * 
	 * @param a
	 * @param b
	 * @param msg
	 * @throws Exception
	 */
	public void print(List a, Comparable b[], String msg) throws Exception
	{
		String s = "";
		
		s += msg + "\n\n";
		
		if (a != null)
		{
			for (int i = 0; i < a.size(); i ++)
			{
				s += a.get(i) + ", ";
				
				if (i >= 10 && i % 10 == 0)
				{
					s += "\n";
				}
			}
		}
		else if (b != null)
		{
			for (int i = 0; i < b.length; i ++)
			{
				s += b[i] + ", ";
				
				if (i >= 10 && i % 10 == 0)
				{
					s += "\n";
				}
			}
		}
		
		System.out.println(s);
		
		write("log.txt", s);
	}
	
	/**
	 * Method that sorts using the insertion sort method.
	 * 
	 * @param arr
	 */
	public void insertionSort(List<Integer> arr)
	{
		for (int currentSorted = 1; currentSorted <
				arr.size(); currentSorted ++)
		{
			Integer nextElement = arr.get(currentSorted);
			
			int compareI        = 0;
			
			for (compareI = currentSorted - 1; compareI >= 0 &&
					arr.get(compareI) > nextElement; compareI --)
			{
				Integer temp = arr.get(compareI + 1);
				arr.set(compareI + 1, arr.get(compareI));
				arr.set(compareI, temp);
			}
		}
	}
	
	/**
	 * Sorts an array of Comaparables from least to greatest.
	 * 
	 * @param arr
	 */
	public void selectionSort(Comparable arr[])
	{
		for (int endOfArray = arr.length; endOfArray > 0; endOfArray --)
		{
			int        maxI = 0;
			Comparable max  = arr[maxI];
			
			for (int i = 0; i < endOfArray; i ++)
			{
				if (arr[i].compareTo(max) > 0)
				{
					maxI = i;
					max  = arr[maxI];
				}
			}
			
			Comparable temp     = arr[maxI];
			arr[maxI]           = arr[endOfArray - 1];
			arr[endOfArray - 1] = temp;
		}
	}
	
	public void quickSort(Comparable arr[])
	{
		quickSort(arr, 0, arr.length - 1);
	}
	
	public void quickSort(Comparable arr[], int frontIndex, int backIndex)
	{
		int middleIndex = 0;
		
		if (frontIndex < backIndex)
		{
			middleIndex = partition(arr, frontIndex, backIndex);
			
			quickSort(arr, frontIndex, middleIndex - 1);
			quickSort(arr, middleIndex + 1, backIndex);
		}
	}
	
	private int partition(Comparable arr[], int frontIndex, int backIndex)
	{
		return 0;
	}
	
	/**
	 * Writes the object to the specified file at the specified
	 * location.
	 * 
	 * @param location The location of the file to write to.
	 * @param obj The object to write to the file.
	 */
	private static void write(String location, Object obj)
			throws IOException
	{
		if (obj instanceof String)
		{
			File file = new File(location);
			
			if (!file.exists())
			{
				file.createNewFile();
			}
			
			String str        = "";
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String line = "";
			
			while ((line = br.readLine()) != null)
			{
				str += line + "\n";
			}
			
			PrintWriter pw    = new PrintWriter(new BufferedWriter(
					new FileWriter(file)));
			
			str += (String)obj;
			
			pw.append(str);
			
			pw.close();
		}
		else
		{
			ObjectOutputStream out = new ObjectOutputStream(
					new BufferedOutputStream(
					new FileOutputStream(location)));

			out.writeObject(obj);

			out.close();
		}
	}
}
