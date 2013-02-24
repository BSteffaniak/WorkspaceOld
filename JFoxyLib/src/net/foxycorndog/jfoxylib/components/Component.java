package net.foxycorndog.jfoxylib.components;

public class Component
{
	private int		x, y;
	private int		width, height;
	
	private Panel	parent;
	
	public Component(Panel parent)
	{
		this.parent = parent;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}