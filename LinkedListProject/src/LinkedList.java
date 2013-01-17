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
		return null;
	}
	
	/**
	 * Returns the last node in the list and then removes it.
	 * 
	 * @return The last node in the list.
	 */
	public Object getBack()
	{
		return null;
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
			ListNode temp = node;
			ListNode next = node.getNext();
			
			if (next == null)
			{
				return node;
			}
			else
			{
				node = next;
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
			node = node.getNext();
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
		
		builder.append("LinkedList { ");
		
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