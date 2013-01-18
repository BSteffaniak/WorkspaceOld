import java.util.ArrayList;

public class StackList implements Stack
{
	LinkedList list;
	
	public StackList()
	{
		list = new LinkedList();
	}
	
	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	public Object push(Object o)
	{
		list.addFront(o);
		
		return o;
	}

	public Object pop()
	{
		return list.getFront();
	}

	public Object peek()
	{
		Object data = list.getFront();
		list.addFront(data);
		
		return data;
	}

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