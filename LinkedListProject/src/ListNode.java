/**
 * File:          ListNode.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 22Jan2013
 * Description:   Class that keeps track of a piece of data
 * 		and the next ListNode in the sequence.
 */
public class ListNode
{
	private Object		data;

	private ListNode	next;
	
	public ListNode()
	{
		data = null;
		next = null;
	}
	
	public ListNode(Object data)
	{
		this.data = data;
	}
	
	public void setNext(ListNode next)
	{
		this.next = next;
	}
	
	public ListNode getNext()
	{
		return next;
	}
	
	public Object getData()
	{
		return data;
	}
	
	public void setData(Object data)
	{
		this.data = data;
	}
}