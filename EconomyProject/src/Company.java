import java.util.ArrayList;
import java.util.Collections;

/**
 * File:          Company.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 6Sep2012
 * Description:   Class that holds the information of the
 * curve. And can negotiate.
 */
public class Company
{
	private int   currentIndex;
	
	private Curve curve;
	
 	/**
	 * Constructor for the slope and stuff.
	 */
	public Company(double m, int x, double b, int num)
	{
		curve = new Curve(m, x, b, num);
	}
	
	/**
	 * Create a Curve of points with the specified array.
	 * 
	 * @param points The Point array of Points to create the Curve for.
	 */
	public Company(Point points[])
	{
		curve = new Curve(points);
	}
	
	/**
	 * Returns the initial bid for this curve.
	 */
	public Point initialBid()
	{
		currentIndex = 0;
		
		Point point = curve.getPoint(currentIndex ++);
		
		return point;
	}
	
	/**
	 * Method used to see if this curve will accept the bid.
	 */
	public Point negotiate(Point p)
	{
		Point currentPoint = curve.getPoint(currentIndex ++);
		
		Point nextPoint    = null;
		
		if (currentIndex < curve.size())
		{
			nextPoint = curve.getPoint(currentIndex);
		}
		
		double distX   = 0, distY = 0;
		
		Rectangle tol1 = null, tol2 = null;
		
		if (nextPoint != null)
		{
			distX = nextPoint.getQuantity() -
					currentPoint.getQuantity();
			distY = nextPoint.getQuantity() -
					currentPoint.getQuantity();
		
			tol2  = new Rectangle(nextPoint
					.getQuantity() - distX / 2, nextPoint
					.getCost() - distY / 2, distX, distY);
		}
			
		tol1 = new Rectangle(currentPoint.getQuantity() - distX / 2,
				currentPoint.getCost() - distY / 2, distX, distY);
		
		if (tol2 != null)
		{
			if (Market.equilibrium(p, tol1, tol2))
			{
				return p;
			}
		}
		else
		{
			if (Market.equilibrium(p, tol1))
			{
				return p;
			}
		}
		
		return currentPoint;
	}
	
	/**
	 * The to string. returns the curves points.
	 */
	public String toString()
	{
		return curve.toString();
	}
}
