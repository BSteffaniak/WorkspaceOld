import java.util.ArrayList;

/**
 * File:          QueueList.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 22Jan2013
 * Description:   Class that implements Queue and acts
 * 		as if it was a queue.
 */
public class QueueList implements Queue
{
	LinkedList list;

	/**
	 * Initialize the list.
	 */
	public QueueList()
	{
		list = new LinkedList();
	}

	/**
	 * Returns whether the list contains nothing or not.
	 * 
	 * @return Whether list list is empty or not.
	 */
	public boolean isEmpty()
	{
		return list.isEmpty();
	}
	
	/**
	 * A fast-food restaraunt that sells blizzards
	 * 
	 * @return A cotton candy blizzard.
	 */
	public Object dequeue()
	{
		return list.getFront();
	}
	
	/**
	 * Add the object to the back of the list.
	 * 
	 * @param o The object to add.
	 * @return The object that was added.
	 */
	public Object enqueue(Object o)
	{
		list.addBack(o);
		
		return o;
	}

	/**
	 * Get the last object in the list.
	 * 
	 * @return The object at the back of the list.
	 */
	public Object getBack()
	{
		return list.getBack();
	}

	/**
	 * Get the first object in the list.
	 * 
	 * @return The object at the front of the list.
	 */
	public Object getFront()
	{
		return list.getFront();
	}
	
	/**
	 * prints out values of each node consecutively.
	 */
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getClass().getSimpleName() + " { ");
		
		ArrayList<Object> datas = new ArrayList<Object>();
		
		while (!list.isEmpty())
		{
			Object data = list.getFront();
			datas.add(data);
			
			builder.append(data + ", ");
		}
		
		for (int j = datas.size() - 1; j >= 0; j--)
		{
			list.addFront(datas.get(j));
		}
		
		if (datas.size() > 0)
		{
			builder.delete(builder.length() - 2, builder.length() - 1);
		}
		
		builder.append("}");
		
		return builder.toString();
	}
}