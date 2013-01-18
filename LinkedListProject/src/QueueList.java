import java.util.ArrayList;

public class QueueList implements Queue
{
	LinkedList list;
	
	public QueueList()
	{
		list = new LinkedList();
	}
	
	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	public Object dequeue()
	{
		return list.getFront();
	}

	public Object enqueue(Object o)
	{
		list.addBack(o);
		
		return o;
	}

	public Object getBack()
	{
		return list.getBack();
	}

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
		
		builder.append(" }");
		
		return builder.toString();
	}
}