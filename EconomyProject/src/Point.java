/**
 * File:          Point.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 28August2012
 * Description:   Class that holds the information of the
 * quantity and cost of an item. It can also check if
 * two Points are equal to each other.
 */
public class Point implements Comparable
{
	private long   quantity;

	private double cost;

	public  static final double EPSILON = 0.001;

	/**
	 * Prevents anybody from initializing an empty Point object.
	 */
	private Point()
	{
		
	}

	/**
	 * Constructs a point at the specified quantity,
	 * with the specified cost.
	 *
	 * @param quantity The quantity of items.
	 * @param cost The cost of all of the items.
	 */
	public Point(long quantity, double cost)
	{
		if (quantity < 0)
		{
			throw new IllegalArgumentException(
				"Quantity must be > 0.");
		}

		this.quantity = quantity;
		this.cost     = cost;
	}


	/**
	 * Method that checks whether the object passed is
	 * a Point, if it is, then check if this object has
	 * the same variable values as the Object passed. If
	 * not, then just return the Object.equals(Object o)
	 * method.
	 *
	 * @param o The object to be tested if equal.
	 * @return Whether this object and o have the same
	 * 		quantity and cost.
	 */
	public boolean equals(Object o)
	{
		if (o == null)
		{
			return false;
		}
		else if (o == this)
		{
			return true;
		}

		if (o instanceof Point)
		{
			return equals((Point)o);
		}

		return super.equals(o);
	}

	/**
	 * Method that checks whether this object has the same
	 * variable values as the Point passed.
	 *
	 * @param p The Point to check if equal.
	 * @return Whether the two Points values are equal.
	 */
	public boolean equals(Point p)
	{
		if ((p.quantity == quantity) && doubleEquals(p.cost, cost))
		{
			return true;
		}

		return false;
	}

	/**
	 * Method that makes a neater way of printing out
	 * the information of the Object.
	 *
	 * @return The text output of the Point Object.
	 */
	public String toString()
	{
		return "(" + quantity + ", " + String.format("%.2f", cost) + ")";
	}

	/**
	 * Method that checks if the double values are close
	 * enough to be called equal. They must be within the
	 * EPSILON, which is 0.001.
	 *
	 * @param d1 The first double to be checked.
	 * @param d2 The second double to be compared to.
	 * @return Whether the doubles are close enough.
	 */
	private boolean doubleEquals(double d1, double d2)
	{
		return !(Math.abs(d1 - d2) > EPSILON);
	}
	
	/**
	 * @return the quantity
	 */
	public long getQuantity()
	{
		return quantity;
	}

	/**
	 * @return the cost
	 */
	public double getCost()
	{
		return cost;
	}

	/**
	 * Method implemented from the Comparable interface. Sorts
	 * the points.
	 */
	public int compareTo(Object o)
	{
		if (o instanceof Point)
		{
			Point p = (Point)o;
			
			if (quantity > p.quantity)
			{
				return 1;
			}
			else if (quantity == p.quantity)
			{
				return 0;
			}
			else
			{
				return -1;
			}
		}
		else
		{
			throw new IllegalArgumentException("A Point must be passed.");
		}
	}
}
