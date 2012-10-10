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
	private int   negotiateIndex;
	
	private Curve curve;
	
 	/**
	 * Constructor for the slope and stuff.
	 */
	public Company(double m, double b, int x, int num)
	{
		curve = new Curve(m, b, x, num);
	}
	
	/**
	 * Returns the initial bid for this curve.
	 */
	public Point initialBid()
	{
		return curve.getPoint(0);
	}
	
	/**
	 * Method used to see if this curve will accept the bid.
	 */
	public Point negotiate(Point p)
	{
		negotiateIndex ++;
		
		double distX = 0, distY = 0;
		
		Rectangle tol1 = null, tol2 = null;
		
		if (negotiateIndex < curve.size())
		{
			distX   = curve.getPoint(negotiateIndex).getQuantity() -
					curve.getPoint(negotiateIndex - 1).getQuantity();
			distY   = curve.getPoint(negotiateIndex).getQuantity() -
					curve.getPoint(negotiateIndex - 1).getQuantity();
		
			tol2 = new Rectangle(curve.getPoint(negotiateIndex)
					.getQuantity() - distX / 2, curve.getPoint(negotiateIndex)
					.getCost() - distY / 2, distX, distY);
		}
			
		tol1 = new Rectangle(curve.getPoint(negotiateIndex -1)
				.getQuantity() - distX / 2, curve.getPoint(negotiateIndex
				- 1).getCost() - distY / 2, distX, distY);
		
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
		
		if (negotiateIndex - 1 < curve.size())
		{
			return curve.getPoint(negotiateIndex - 1);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * The to string. returns the curves points.
	 */
	public String toString()
	{
		return curve.toString();
	}
}