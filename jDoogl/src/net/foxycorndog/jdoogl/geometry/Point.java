package net.foxycorndog.jdoogl.geometry;

public class Point implements Cloneable
{
	private float x, y, z;
	
	public Point(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point minus(Point p)
	{
		return minus(p.x, p.y, p.z);
	}
	
	public Point minus(float x, float y, float z)
	{
		return new Point(this.x - x, this.y - y, this.z - z);
	}
	
	public float getX()
	{
		return x;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public float getZ()
	{
		return z;
	}
	
	public void setZ(float z)
	{
		this.z = z;
	}
	
	public String toString()
	{
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
	public Point clone()
	{
		Point clone = null;
		
		try
		{
			clone = (Point)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		
		return clone;
	}
}