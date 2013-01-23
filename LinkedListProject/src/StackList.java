import java.util.ArrayList;

/**
 * File:          StackList.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 22Jan2013
 * Description:   Class that implements Stack and acts
 * 		as if it was a stack.
 */
public class StackList implements Stack
{
	LinkedList list;
	
	/**
	 * Initialize the list.
	 */
	public StackList()
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
	 * Put an object on the top of the stack.
	 * 
	 * @param o The object to put.
	 * @return The object that was put.
	 */
	public Object push(Object o)
	{
		list.addFront(o);
		
		return o;
	}

	/**
	 * Remove the object at the top of the stack.
	 * 
	 * @return The object that was removed.
	 */
	public Object pop()
	{
		return list.getFront();
	}

	/**
	 * Gets the front object from the list without
	 * removing it from the list.
	 * 
	 * @return The first object in the list.
	 */
	public Object peek()
	{
		Object data = list.getFront();
		list.addFront(data);
		
		return data;
	}

	/**
	 * @return position of element on stack, top
	 * 		element = 1 ...return -1 if not found
	 */
	public int search(Object o)
	{
		ArrayList<Object> datas = new ArrayList<Object>();
		
		int index = -1;
		
		for (int i = 0; !list.isEmpty(); i++)
		{
			Object data = list.getFront();
			
			datas.add(data);
			
			if (data.equals(o))
			{
				index = i;
				
				break;
			}
		}
		
		for (int j = datas.size() - 1; j >= 0; j--)
		{
			list.addFront(datas.get(j));
		}
		
		return index;
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
		
		builder.append(" }");
		
		return builder.toString();
	}
}