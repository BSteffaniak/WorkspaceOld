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
	
	public static Vector crossProduct(Vector v1, Vector v2)
	{
		Vector cross = new Vector(0, 0, 0);
		
		cross.x = v1.y * v2.z - v1.z * v2.y;
		cross.y = v1.z * v2.x - v1.x * v2.z;
		cross.z = v1.x * v2.y - v1.y * v2.x;
		
		return cross;
	}
	
	public void minus(Vector vector)
	{
		minus(vector.getX(), vector.getY(), vector.getZ());
	}
	
	public void minus(float x, float y, float z)
	{
		this.x -= x;
		this.y -= y;
		this.z -= z;
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