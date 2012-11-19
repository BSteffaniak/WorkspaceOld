package net.foxycorndog.jdoutil;

import java.util.ArrayList;

public class DynamicArray<E>
{
	ArrayList list;
	
	public DynamicArray()
	{
		this(0);
	}
	
	public DynamicArray(int size)
	{
		list = new ArrayList<E>(size);
	}
	
	public void add()
	{
		synchronized(this)
		{
			
		}
	}
}