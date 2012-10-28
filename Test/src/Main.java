import net.foxycorndog.jdoogl.geometry.Point;
import net.foxycorndog.jdoogl.geometry.Vector;

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
		
		float l1x = 0;
		float l1y = 5;
		float l1z = 0;

		float l2x = 0;
		float l2y = -5;
		float l2z = 0;
		
		// Line points
		Point a = new Point(l1x, l1y, l1z);
		Point b = new Point(l2x, l2y, l2z);
		
		// Triangle points
		Point c = new Point(p1x, p1y, p1z);
		Point d = new Point(p2x, p2y, p2z);
		Point e = new Point(p3x, p3y, p3z);
		
		// Get the cross product (normal) of the face of the triangle
		Vector crossProd = Vector.crossProduct(c, d, e);
		System.out.println("CrossProduct: " + crossProd);
		
		// Get the t variable using the cross product, a point, and b point.
		float t = solveForT(crossProd, a, b);
		
		// The vector for the line
		Vector lineVector = Vector.toVector(a.minus(b));
		
		// the point of intersection between the line and the plane
		Point intersection = solveForIntersection(a, lineVector, t);
		
		System.out.println(intersection);
		
		if (inside(intersection, c, d, e))
		{
			System.out.println("The line intersects the triangle.");
		}
		else
		{
			System.out.println("The line does not intersect the triangle.");
		}
	}
	
	private boolean inside(Point intersection, Point point1, Point point2, Point point3)
	{
		float ix = intersection.getX();
		float iy = intersection.getY();
		float iz = -intersection.getZ();
		
		float side1Slope = (-point2.getZ() - -point1.getZ()) / (point2.getX() - point1.getX());
		float side2Slope = (-point3.getZ() - -point2.getZ()) / (point3.getX() - point2.getX());
		float side3Slope = (-point3.getZ() - -point1.getZ()) / (point3.getX() - point1.getX());
		
		float side1b = (-point1.getZ() - (side1Slope * point1.getX()));
		float side2b = (-point2.getZ() - (side2Slope * point2.getX()));
		float side3b = (-point1.getZ() - (side3Slope * point1.getX()));
		
		return (iz < side1Slope * ix + side1b) && (iz < side2Slope * ix + side2b) && (iz >= side3Slope * ix + side3b);
	}
	
	private Point solveForIntersection(Point point, Vector vector, float t)
	{
		float x = point.getX() + (vector.getX() * t);
		float y = point.getY() + (vector.getY() * t);
		float z = point.getZ() + (vector.getZ() * t);
		
		return new Point(x, y, z);
	}
	
	private float solveForT(Vector normal, Point point1, Point point2)
	{
		float results[] = Vector.dotProductCoefficients(normal, point1, point2);
		
		float a = results[0];
		float b = results[1];
		float c = results[2];
		
		float x1 = point2.getX();
		float y1 = point2.getY();
		float z1 = point2.getZ();
		
		point1 = point1.minus(point2);
		
		System.out.println(point1);
		
		float t = ((a * x1) + (b * y1) + (c * z1)) / ((a * point1.getX()) + (b * point1.getY()) + (c * point1.getZ()));
		
		System.out.println(t);
		
		return t;
	}
}