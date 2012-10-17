/**
 * File:          Producer.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 6Sep2012
 * Description:   Class that is the producer.
 */
public class Producer extends Company
{
	/**
	 * Constructor from the Company class.
	 */
	public Producer(double m, int x, double b, int num)
	{
		super(m, x, b, num);
	}
	
	public Producer(Point points[])
	{
		super(points);
	}
}