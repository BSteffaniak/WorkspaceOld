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