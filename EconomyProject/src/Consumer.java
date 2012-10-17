/**
 * File:          Consumer.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 6Sep2012
 * Description:   Class that is the consumer.
 */
public class Consumer extends Company
{
	/**
	 * Constructor from the Company class.
	 */
	public Consumer(double m, int x, double b, int num)
	{
		super(m, x, b, num);
	}
	
	public Consumer(Point points[])
	{
		super(points);
	}
}