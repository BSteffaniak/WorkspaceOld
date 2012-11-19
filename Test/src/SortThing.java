public class SortThing
{
	private Comparable rArr[];
	
	public static void main(String args[])
	{
		new SortThing();
	}
	
	public SortThing()
	{
		rArr = genRandomArray(18);
		
		printArray(rArr);
		
		System.out.println("\nAfter:");
		insertionSort(rArr);
		printArray(rArr);
	}
	
	private Comparable[] genRandomArray(int length)
	{
		Comparable arr[] = new Comparable[length];
		
		for (int i = 0; i < length; i ++)
		{
			arr[i] = (int)(Math.random() * 100);
		}
		
		return arr;
	}
	
	private void printArray(Comparable arr[])
	{
		for (Comparable i : arr)
		{
			System.out.println(i);
		}
	}
	
	private void insertionSort(Comparable arr[])
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
	
	private void selectionSort(Comparable arr[])
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