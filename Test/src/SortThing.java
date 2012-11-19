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
	
	/**
	 * 
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		SortThing s = new SortThing();
		
		s.rArr = s.genRandomArray(100);
		
		try
		{
			s.print(null, s.rArr, "Before: ");
			
			System.out.println("\n");
			
			s.insertionSort(s.rArr);
			s.print(null, s.rArr, "After: ");
		}
		catch (Exception e)
		{
			
		}
	}
	
	/**
	 * THIS IS A CONSTRUCTOR!!!!!!!!!!!!!!!!!!
	 */
	public SortThing()
	{
		
	}
	
	/**
	 * 
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
	}
	
	/**
	 * 
	 * 
	 * @param arr
	 */
	public void insertionSort(Comparable arr[])
	{
		for (int currentSorted = 1; currentSorted < arr.length; currentSorted ++)
		{
			Comparable nextElement = arr[currentSorted];
			
			int compareI           = 0;
			
			for (compareI = currentSorted - 1; compareI >= 0 && arr[compareI].compareTo(nextElement) > 0; compareI --)
			{
				Comparable temp   = arr[compareI + 1];
				arr[compareI + 1] = arr[compareI];
				arr[compareI]     = temp;
			}
		}
	}
	
	/**
	 * 
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
			
			Comparable temp = arr[maxI];
			arr[maxI]       = arr[endOfArray - 1];
			arr[endOfArray - 1] = temp;
		}
	}
}