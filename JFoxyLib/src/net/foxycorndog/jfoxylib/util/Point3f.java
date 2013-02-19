package net.foxycorndog.jfoxylib.util;

public class Point3f implements Cloneable
{
	private float x, y, z;
	
	public Point3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3f minus(Point3f p)
	{
		return minus(p.x, p.y, p.z);
	}
	
	public Point3f minus(float x, float y, float z)
	{
		return new Point3f(this.x - x, this.y - y, this.z - z);
	}
	
	public Point3f plus(Point3f p)
	{
		return minus(p.x, p.y, p.z);
	}
	
	public Point3f plus(float x, float y, float z)
	{
		return new Point3f(this.x + x, this.y + y, this.z + z);
	}
	
	public void move(float dx, float dy, float dz)
	{
		x += dx;
		y += dy;
		z += dz;
	}
	
	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
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
	
	public Point3f clone()
	{
		Point3f clone = null;
		
		try
		{
			clone = (Point3f)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		
		return clone;
	}
}