package net.foxycorndog.jdoutil;

public class Vector
{
	private float x, y, z;
	
	public Vector(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float dotProduct(Vector vector)
	{
		double length1 = Math.sqrt(x * x + y * y + z * z);
		double length2 = Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
		
		double dotProduct = (x * vector.x + y * vector.y + z * vector.z);
		
		dotProduct /= (length1 * length2);
		
		return (float)dotProduct;
	}
	
	public float dotProduct(float x, float y, float z)
	{
		double length1 = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
		double length2 = Math.sqrt(x * x + y * y + z * z);
		
		double dotProduct = (this.x * x + this.y * y + this.z * z);
		
		dotProduct /= (length1 * length2);
		
		return (float)dotProduct;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getZ()
	{
		return z;
	}
}