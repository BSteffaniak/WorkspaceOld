public class LinkedList
{
	private ListNode start;
	
	/**
	 * Returns whether the list contains nothing or not.
	 * 
	 * @return Whether list list is empty or not.
	 */
	public boolean isEmpty()
	{
		return start == null;
	}
	
	/**
	 * Returns the first node in the list and then removes it.
	 * 
	 * @return The first node in the list.
	 */
	public Object getFront()
	{
		if (isEmpty())
		{
			throw new EmptyListException();
		}
		
		Object data = start.getData();
		
		start = start.getNext();
		
		return data;
	}
	
	/**
	 * Returns the last node in the list and then removes it.
	 * 
	 * @return The last node in the list.
	 */
	public Object getBack()
	{
		if (isEmpty())
		{
			throw new EmptyListException();
		}
		
		Object data = null;
		
		ListNode secondLast = getSecondLastNode();
		
		if (secondLast != null)
		{
			ListNode lastNode = secondLast.getNext();
			
			if (lastNode == null)
			{
				data  = start.getData();
				start = null;
			}
			else
			{
				data = lastNode.getData();
				secondLast.setNext(null);
			}
		}
		
		return data;
	}
	
	/**
	 * Gets the second to last node in the list.
	 * 
	 * @return the second to last node if there is one.
	 */
	private ListNode getSecondLastNode()
	{
		if (start == null)
		{
			return null;
		}
		
		ListNode node = start;
		
		while (node != null)
		{
			ListNode next  = node.getNext();
			
			if (next != null)
			{
				ListNode next2 = next.getNext();
				
				if (next2 == null)
				{
					return node;
				}
				else
				{
					node = next;
				}
			}
			else
			{
				return node;
			}
		}
		
		return node;
	}
	
	/**
	 * Gets the last node in the list.
	 * 
	 * @return The last node in the list if there is one.
	 */
	private ListNode getLastNode()
	{
		ListNode node = getSecondLastNode();
		
		if (node != null)
		{
			ListNode temp = node.getNext();
			
			if (temp != null)
			{
				node = temp;
			}
		}
		else
		{
			return start;
		}
		
		return node;
	}
	
	/**
	 * Adds a ListNode containing the object to the front
	 * of the list.
	 * 
	 * @param o The object to add to the list.
	 */
	public void addFront(Object o)
	{
		ListNode newNode = new ListNode(o);
		
		newNode.setNext(start);
		
		start = newNode;
	}
	
	/**
	 * Adds a ListNode containing the object to the back
	 * of the list.
	 * 
	 * @param o The object to add to the list.
	 */
	public void addBack(Object o)
	{
		ListNode node = new ListNode(o);
		
		if (isEmpty())
		{
			start = node;
		}
		else
		{
			getLastNode().setNext(node);
		}
	}
	
	/**
	 * prints out values of each node consecutively.
	 */
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getClass().getSimpleName() + " { ");
		
		ListNode node = start;
		
		while (node != null)
		{
			builder.append(node.getData() + ", ");
			
			node = node.getNext();
		}
		
		builder.append(" }");
		
		return builder.toString();
	}
}