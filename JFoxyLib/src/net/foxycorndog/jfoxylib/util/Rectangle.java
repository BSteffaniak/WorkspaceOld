package net.foxycorndog.jfoxylib.util;

public class Rectangle
{
	private int	x, y;
	private int	width, height;
	
	public Rectangle(int x, int y, int width, int height)
	{
		this.x      = x;
		this.y      = y;
		this.width  = width;
		this.height = height;
	}
	
	public Rectangle(java.awt.Rectangle rectangle)
	{
		this.x      = rectangle.x;
		this.y      = rectangle.y;
		this.width  = rectangle.width;
		this.height = rectangle.height;
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public String toString()
	{
		String str = "";
		
		str += this.getClass().getSimpleName() + " { " + x + ", " + y + ", " + width + ", " + height + " }";
		
		return str;
	}
}