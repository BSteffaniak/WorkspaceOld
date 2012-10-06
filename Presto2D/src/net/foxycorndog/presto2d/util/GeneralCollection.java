package net.foxycorndog.presto2d.util;

import java.util.ArrayList;

public class GeneralCollection<E>
{
//	private int amountElements;
	
	private ArrayList<ArrayList<E>> posXposY;
	private ArrayList<ArrayList<E>> posXnegY;
	private ArrayList<ArrayList<E>> negXnegY;
	private ArrayList<ArrayList<E>> negXposY;
	
	public GeneralCollection()
	{
		init();
	}
	
	public void init()
	{
		posXposY = new ArrayList<ArrayList<E>>();
		posXnegY = new ArrayList<ArrayList<E>>();
		negXnegY = new ArrayList<ArrayList<E>>();
		negXposY = new ArrayList<ArrayList<E>>();
		
//		amountElements = 0;
	}
	
	public void add(int x, int y, E object)
	{
		if (x >= 0 && y >= 0)
		{
			while (true)
			{
				try
				{
					posXposY.get(x).set(y, object);
					
					break;
				}
				catch (IndexOutOfBoundsException ex)
				{
					while (true)
					{
						try
						{
							posXposY.get(x).add(null);
							
							break;
						}
						catch (IndexOutOfBoundsException ex2)
						{
							posXposY.add(new ArrayList<E>());
						}
					}
				}
			}
		}
		else if (x >= 0 && y < 0)
		{
			while (true)
			{
				try
				{
					posXnegY.get(x).set(-y, object);
					
					break;
				}
				catch (IndexOutOfBoundsException ex)
				{
					while (true)
					{
						try
						{
							posXnegY.get(x).add(null);
							
							break;
						}
						catch (IndexOutOfBoundsException ex2)
						{
							posXnegY.add(new ArrayList<E>());
						}
					}
				}
			}
		}
		else if (x < 0 && y < 0)
		{
			while (true)
			{
				try
				{
					negXnegY.get(-x).set(-y, object);
					
					break;
				}
				catch (IndexOutOfBoundsException ex)
				{
					while (true)
					{
						try
						{
							negXnegY.get(-x).add(null);
							
							break;
						}
						catch (IndexOutOfBoundsException ex2)
						{
							negXnegY.add(new ArrayList<E>());
						}
					}
				}
			}
		}
		else if (x < 0 && y >= 0)
		{
			while (true)
			{
				try
				{
					negXposY.get(-x).set(y, object);
					
					break;
				}
				catch (IndexOutOfBoundsException ex)
				{
					while (true)
					{
						try
						{
							negXposY.get(-x).add(null);
							
							break;
						}
						catch (IndexOutOfBoundsException ex2)
						{
							negXposY.add(new ArrayList<E>());
						}
					}
				}
			}
		}
	}
	
	public ArrayList<E> getElements()
	{
		ArrayList<E> elements = new ArrayList<E>();
		
		for (int x = 0; x < posXposY.size(); x ++)
		{
			for (int y = 0; y < posXposY.get(x).size(); y ++)
			{
				if (posXposY.get(x).get(y) != null)
				{
					elements.add(posXposY.get(x).get(y));
				}
			}
		}
		
		for (int x = 0; x < posXnegY.size(); x ++)
		{
			for (int y = 0; y < posXnegY.get(x).size(); y ++)
			{
				if (posXnegY.get(x).get(y) != null)
				{
					elements.add(posXnegY.get(x).get(y));
				}
			}
		}
		
		for (int x = 0; x < negXnegY.size(); x ++)
		{
			for (int y = 0; y < negXnegY.get(x).size(); y ++)
			{
				if (negXnegY.get(x).get(y) != null)
				{
					elements.add(negXnegY.get(x).get(y));
				}
			}
		}
		
		for (int x = 0; x < negXposY.size(); x ++)
		{
			for (int y = 0; y < negXposY.get(x).size(); y ++)
			{
				if (negXposY.get(x).get(y) != null)
				{
					elements.add(negXposY.get(x).get(y));
				}
			}
		}
		
		return elements;
	}
	
	public E get(int x, int y)
	{
		if (x >= 0 && y >= 0)
		{
			return posXposY.get(x).get(y);
		}
		else if (x >= 0 && y < 0)
		{
			return posXnegY.get(x).get(-y);
		}
		else if (x < 0 && y < 0)
		{
			return negXnegY.get(-x).get(-y);
		}
		else if (x < 0 && y >= 0)
		{
			return negXposY.get(-x).get(y);
		}
		
		return null;
	}
	 
	public boolean is(int x, int y)
	{
		try
		{
			if (x >= 0 && y >= 0)
			{
				posXposY.get(x).get(y);
			}
			else if (x >= 0 && y < 0)
			{
				posXnegY.get(x).get(-y);
			}
			else if (x < 0 && y < 0)
			{
				negXnegY.get(-x).get(-y);
			}
			else if (x < 0 && y >= 0)
			{
				negXposY.get(-x).get(y);
			}
			
			return true;
		}
		catch (IndexOutOfBoundsException ex)
		{
			return false;
		}
	}
	
	public boolean contains(E object)
	{
		for (int i = 0; i < posXposY.size(); i ++)
		{
			if (object.equals(posXposY.get(i)))
			{
				return true;
			}
		}
		for (int i = 0; i < posXnegY.size(); i ++)
		{
			if (object.equals(posXnegY.get(i)))
			{
				return true;
			}
		}
		for (int i = 0; i < negXnegY.size(); i ++)
		{
			if (object.equals(negXnegY.get(i)))
			{
				return true;
			}
		}
		for (int i = 0; i < negXposY.size(); i ++)
		{
			if (object.equals(negXposY.get(i)))
			{
				return true;
			}
		}
		
		return false;
	}
}