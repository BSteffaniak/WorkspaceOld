package net.foxycorndog.jdoogl.geometry;

public class Vector extends Point implements Cloneable
{
	public Vector(float x, float y, float z)
	{
		super(x, y, z);
	}
	
	public float dotProduct(Vector vector)
	{
		float x = getX();
		float y = getY();
		float z = getZ();
		
		float vx = vector.getX();
		float vy = vector.getY();
		float vz = vector.getZ();
		
		double length1 = Math.sqrt(x * x + y * y + z * z);
		double length2 = Math.sqrt(vx * vx + vy * vy + vz * vz);
		
		double dotProduct = (x * vx + y * vy + z * vz);
		
		dotProduct /= (length1 * length2);
		
		return (float)dotProduct;
	}
	
	public float dotProduct(float x, float y, float z)
	{
		float thisX = getX();
		float thisY = getY();
		float thisZ = getZ();
		
		double length1 = Math.sqrt(thisX * thisX + thisY * thisY + thisZ * thisZ);
		double length2 = Math.sqrt(x * x + y * y + z * z);
		
		double dotProduct = (thisX * x + thisY * y + thisZ * z);
		
		dotProduct /= (length1 * length2);
		
		return (float)dotProduct;
	}
	
	public static Vector crossProduct(Point v1, Point v2)
	{
		Vector cross = new Vector(0, 0, 0);
		
		cross.setX(v1.getY() * v2.getZ() - v1.getZ() * v2.getY());
		cross.setY(v1.getZ() * v2.getX() - v1.getX() * v2.getZ());
		cross.setZ(v1.getX() * v2.getY() - v1.getY() * v2.getX());
		
		return cross;
	}
	
	public static Vector crossProduct(Point point1, Point point2, Point point3)
	{
		point2 = point2.minus(point1);
		point3 = point3.minus(point1);
		
		return crossProduct(point2, point3);
	}
	
	public static float dotProduct(Vector normal, Vector v1, Vector v2)
	{
		v1 = v1.clone();
		
		v1.minus(v2);
		
		return normal.dotProduct(v1);
	}
	
	public static float[] dotProductCoefficients(Vector normal, Point point1, Point point2)
	{
		double a = normal.getX();// * v1.x;
		double b = normal.getY();// * v1.y;
		double c = normal.getZ();// * v1.z;
		
		Vector vector = toVector(point1.minus(point2));
		
		double d = normal.dotProduct(vector) * normal.magnitude() * vector.magnitude();//(normal.x * -v2.x) + (normal.y * -v2.y) + (normal.z * -v2.z);
		
		return new float[] { (float)a, (float)b, (float)c, (float)d };
	}
	
	public float magnitude()
	{
		return (float)Math.sqrt(getX() * getX() + getY() * getY() + getZ() * getZ());
	}
	
	public void normalize()
	{
		float magnitude = magnitude();
		
		setX(getX() / magnitude);
		setY(getY() / magnitude);
		setZ(getZ() / magnitude);
	}
	
	public Vector minus(Vector vector)
	{
		return minus(vector.getX(), vector.getY(), vector.getZ());
	}
	
	public Vector minus(float x, float y, float z)
	{
		return (Vector)super.minus(x, y, z);
	}
	
	public static Vector toVector(Point p)
	{
		return new Vector(p.getX(), p.getY(), p.getZ());
	}
	
	public Vector clone()
	{
		Vector clone = null;
		
		clone = (Vector)super.clone();
		
		return clone;
	}
}