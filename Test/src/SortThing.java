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
 * Last Modified: 10Dec2012
 * Description:   Class that demonstrates the insertion and
 * selection sorting methods and the quick and merge sort.
 */
public class SortThing implements SortingInterface
{
	private int              qArr[];
	
	private Comparable       rArr[];
	private List<Integer>    list;
	private List<Comparable> cList;
	
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
			// Quick sort
			s.qArr = s.genRandomArrayi(100);
			
			s.print(null, null, s.qArr, "Before quick sort sort: ");

			s.quickSort(s.qArr);

			s.print(null, null, s.qArr, "\nAfter quick sort sort: ");
			
			// Temporary indices sort
			s.cList = s.genRandomListc(100);
//			s.cList = s.putListc(new int[]
//				{
//					5, 7, 9, 2, 8, 2, 7, 3, 
//				});
			
			s.print(s.cList, null, null, "\n\nBefore temporary " +
					"indices sort sort: ");

			s.mergeSort(s.cList);

			s.print(s.cList, null, null, "\nAfter temporary indices " +
					"sort sort: ");
		}
		catch (IOException e)
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
	 * Generates random integers for an array
	 * 
	 * @param length
	 * @return
	 */
	private int[] genRandomArrayi(int length)
	{
		int arr[] = new int[length];
		
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
	
	private List<Integer> putListi(int array[])
	{
		List<Integer> arr = new ArrayList<Integer>();
		
		for (int i = 0; i < array.length; i ++)
		{
			arr.add(array[i]);
		}
		
		return arr;
	}
	
	/**
	 * Generates random integers for a list.
	 * 
	 * @param length
	 * @return
	 */
	private List<Comparable> genRandomListc(int length)
	{
		List<Comparable> arr = new ArrayList<Comparable>();
		
		for (int i = 0; i < length; i ++)
		{
			arr.add(i, (Integer)(int)(Math.random() * 100));
		}
		
		return arr;
	}
	
	private List<Comparable> putListc(int array[])
	{
		List<Comparable> arr = new ArrayList<Comparable>();
		
		for (int i = 0; i < array.length; i ++)
		{
			arr.add(array[i]);
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
	public void print(List a, Comparable b[], int c[], String msg)
			throws IOException
	{
		String s = "";
		
		s += msg + "\n\n";
		
		if (a != null)
		{
			for (int i = 0; i < a.size(); i ++)
			{
				s += a.get(i);
				
				if (i < a.size() - 1)
				{
					s += ", ";
				}
				
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
				s += b[i];
				
				if (i < b.length - 1)
				{
					s += ", ";
				}
				
				if (i >= 10 && i % 10 == 0)
				{
					s += "\n";
				}
			}
		}
		else if (c != null)
		{
			for (int i = 0; i < c.length; i ++)
			{
				s += c[i];
				
				if (i < c.length - 1)
				{
					s += ", ";
				}
				
				if (i >= 10 && i % 10 == 0)
				{
					s += "\n";
				}
			}
		}
		
		System.out.println(s);
		
//		write("log.txt", s);
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

	/**
	 * The quick sort method that you pass the array and
	 * it does the rest for you.
	 * 
	 * @param arr The array to quicksort
	 */
	public void quickSort(int arr[])
	{
		quickSort(arr, 0, arr.length - 1);
	}
	
	/**
	 * The quick sort method that you pass the array,
	 * front index, and back index of the array.
	 * 
	 * @param arr The array to quicksort.
	 * @param frontIndex the first index of the array.
	 * @param backIndex The last index of the array.
	 */
	public void quickSort(int arr[], int frontIndex, int backIndex)
	{
	      if  (frontIndex < backIndex)
	      {
	    	  int middleI = partition(arr, frontIndex, backIndex);
	    	  
	    	  quickSort(arr, frontIndex, middleI - 1);
	    	  quickSort(arr, middleI, backIndex);
	      }
	}
	
	/**
	 * Method that sorts a portion of the array.
	 * 
	 * @param arr The array to partition.
	 * @param left The left index.
	 * @param right The right index.
	 * @return The middle index.
	 */
	private int partition(int arr[],int left,int right){int leftIndex=left;int rightIndex=right;int pivotElement=arr[leftIndex];do{while(arr[leftIndex]<pivotElement){leftIndex++;}while(arr[rightIndex]>pivotElement){rightIndex--;}if(leftIndex<=rightIndex){swap(arr,leftIndex,rightIndex);leftIndex++;rightIndex--;}}while(leftIndex<=rightIndex);return leftIndex;}
	
	/**
	 * call the other mergesort method.
	 */
	public void mergeSort(List<Comparable> arr)
	{
		mergeSort(arr, 0, arr.size() - 1);
	}
	
	/**
	 * The more complicated recursive method that mergesorts.
	 */
	public void mergeSort(List<Comparable> arr, int frontIndex,
			int backIndex)
	{
		// If the number of elements between front and back
		// index is less than or equal to 1 then quit.
		if (backIndex - frontIndex < 1)
		{
			return;
		}
		else
		{
			int middleIndex = (frontIndex + backIndex) / 2;
			
			mergeSort(arr, frontIndex, middleIndex);
			mergeSort(arr, middleIndex + 1, backIndex);
			merge(arr, frontIndex, backIndex);
		}
	}
	
	/**
	 * Puts the elements in order.
	 */
	public void merge(List<Comparable> arr, int frontIndex, int backIndex)
	{
		List<Comparable> arr2 = new ArrayList<Comparable>();
		
		for (int i = 0; i < arr.size(); i ++)
		{
			arr2.add(arr.get(i));
		}
		
		int aCounter    = frontIndex;
		int middleIndex = (frontIndex + backIndex) / 2;
		int leftIndex   = frontIndex;
		int rightIndex  = middleIndex + 1;
		
		while (leftIndex <= middleIndex && rightIndex <= backIndex)
		{
			// If element on the left is bigger than one on the right.
			if (arr2.get(leftIndex).compareTo(arr2.get(rightIndex)) > 0)
			{
				// Set the element to the one on the right.
				arr.set(aCounter ++, arr2.get(rightIndex ++));
			}
			else
			{
				arr.set(aCounter ++, arr2.get(leftIndex ++));
			}
		}
		
		// If all left elements were used.
		if (leftIndex > middleIndex + 1)
		{
			for (int i = rightIndex + middleIndex + 1; i < backIndex;
					i ++)
			{
				arr.set(aCounter ++, arr2.get(i));
			}
		}
		else
		{
			for (int i = leftIndex; i <= middleIndex; i ++)
			{
				arr.set(aCounter ++, arr2.get(i));
			}
		}
	}
	
	private void swap(int arr[], int i1, int i2)
	{
		int temp = arr[i1];
		arr[i1]  = arr[i2];
		arr[i2]  = temp;
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
			
			br.close();
			
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
