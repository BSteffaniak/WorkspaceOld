public interface Queue
{
	public boolean isEmpty();
	
	public Object dequeue();
	
	public Object enqueue(Object o);
	
	public Object getBack();
	
	public Object getFront();
}