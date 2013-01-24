/**
 * File:          ExpressionTest.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 23Jan2013
 * Description:   Class that tests the Expression class.
 */
public class ExpressionTest
{
	/**
	 * Method that tests the Expression class.
	 */
	public static void main(String args[])
	{
		Expression e = new Expression();
		
		e.pushy(6).pushy(4).addy().multiplie().pushy(16).pushy(4).dividey().subtracty();
		
		System.out.println("hi!!!!! " + e.getResult());
	}
}