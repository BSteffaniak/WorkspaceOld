import net.foxycorndog.jdoutil.Vector;

public class Main
{
	public static void main(String args[])
	{
		new Main();
	}
	
	public Main()
	{
		float p1x = -1;
		float p1y = 0;
		float p1z = 0;

		float p2x = 0;
		float p2y = 0;
		float p2z = -2;
		
		float p3x = 1;
		float p3y = 0;
		float p3z = 0;
		
		float l1x = 1;
		float l1y = 5;
		float l1z = -10;

		float l2x = 1;
		float l2y = -5;
		float l2z = -10;
		
		// Line points
		Vector a = new Vector(l1x, l1y, l1z);
		Vector b = new Vector(l2x, l2y, l2z);
		
		// Triangle points
		Vector c = new Vector(p1x, p1y, p1z);
		Vector d = new Vector(p2x, p2y, p2z);
		Vector e = new Vector(p3x, p3y, p3z);
		
		// Get the cross product (normal) of the face of the triangle
		Vector crossProd = Vector.crossProduct(c, d, e);
		
		// Get the t variable using the cross product, a point, and b point.
		float t = solveForT(crossProd, a, b);
		
		// The vector for the line
		Vector lineVector = a.clone();
		lineVector.minus(b);
		
		// the point of intersection between the line and the plane
		float intersection[] = solveForIntersection(a, lineVector, t);
		
		System.out.println(intersection[0] + ", " + intersection[1] + ", " + intersection[2]);
		
		if (inside(new Vector(intersection[0], intersection[1], intersection[2]), c, d, e))
		{
			System.out.println("The line intersects the triangle.");
		}
		else
		{
			System.out.println("The line does not intersect the triangle.");
		}
	}
	
	private float sign(Vector p1, Vector p2, Vector p3)
	{
		return (p1.getX() - p3.getX()) * (p2.getZ() - p3.getZ()) * (p2.getX() - p3.getX()) * (p1.getZ() - p3.getZ());
	}
	
	private boolean inside(Vector intersection, Vector point1, Vector point2, Vector point3)
	{
//		float ix = intersection[0];
//		float iy = intersection[1];
//		float iz = intersection[2];
		
		boolean b1 = sign(intersection, point1, point2) < 0;
		boolean b2 = sign(intersection, point2, point3) < 0;
		boolean b3 = sign(intersection, point3, point1) < 0;
		
		return b1 == b2 && b2 == b3;
	}
	
	private float[] solveForIntersection(Vector point, Vector vector, float t)
	{
		float x = point.getX() + (vector.getX() * t);
		float y = point.getY() + (vector.getY() * t);
		float z = point.getZ() + (vector.getZ() * t);
		
		return new float[] { x, y, z };
	}
	
	private float solveForT(Vector normal, Vector point1, Vector point2)
	{
		float results[] = Vector.dotProductCoefficients(normal, point1, point2);
		
		float a = results[0];
		float b = results[1];
		float c = results[2];
		
		float x1 = point2.getX();
		float y1 = point2.getY();
		float z1 = point2.getZ();
		
		point1 = point1.clone();
		
		point1.minus(point2);
		
		float t = (b * y1) / (b * point1.getY());
		
		return t;
	}
}