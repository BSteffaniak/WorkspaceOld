import java.util.ArrayList;

/**
 * File Name:	Heap.java
 * Programming:	APCS
 * Author:		Braden Steffaniak
 * Date:		18Mar2013
 * Description:	Class that organizes data according to
 * 	the priority from least(top) to greatest(bottom).
 */
public class Heap
{
	private ArrayList<Comparable> data;
	
	/**
	 * Creates a basic Heap with no data.
	 */
	public Heap()
	{
		data = new ArrayList<Comparable>();
		
		data.add(null);
	}
	
	/**
	 * 
	 */
	public boolean add(Comparable data)
	{
		return false;
	}
	
	/**
	 * 
	 */
	public Comparable removeMin()
	{
		return null;
	}
	
	/**
	 * Get the object that represents the minimum value
	 * Comparable in the Heap.
	 * 
	 * @return The minimum value Comparable in the Heap.
	 */
	public Comparable peekMin()
	{
		if (isEmpty())
		{
			
		}
		
		return data.get(1);
	}
	
	/**
	 * Returns false if the Heap has data in it, true if
	 * it does.
	 * 
	 * @return Whether or not the Heap has no data in it.
	 */
	public boolean isEmpty()
	{
		return data.size() <= 1;
	}
	
	/**
	 * Swaps the data at the given indices in the data ArrayList.
	 */
	private void swapData(int index1, int index2)
	{
		Comparable temp = data.get(index1);
		
		data.set(index1, data.get(index2));
		data.set(index2, temp);
	}
}