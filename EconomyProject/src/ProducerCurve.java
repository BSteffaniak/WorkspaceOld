import java.util.Collections;

/**
 * File:          ProducerCurve.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 6Sep2012
 * Description:   Class that holds the information of all
 * the Points on a producers curve. Allows the ability to
 * add, remove, and check if a Point is on the curve.
 */
public class ProducerCurve extends Curve
{
	/**
	 * Constructor inherited from the Curve class.
	 */
	public ProducerCurve(double m, double b, int x, int num)
	{
		super(m, b, x, num);
	}
}
