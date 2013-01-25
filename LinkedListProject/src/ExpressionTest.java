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
		
		e.push(6);
		System.out.println(e);
		e.push(4);
		System.out.println(e);
		e.add();
		System.out.println(e);
		e.push(3);
		System.out.println(e);
		e.multiply();
		System.out.println(e);
		e.push(16);
		System.out.println(e);
		e.push(4);
		System.out.println(e);
		e.divide();
		System.out.println(e);
		e.subtract();
		System.out.println(e);
		
		System.out.println("Result: " + e.getResult() + "\n");
		
		e.clear();
		
		e.push(12);
		System.out.println(e);
		e.push(25);
		System.out.println(e);
		e.push(5);
		System.out.println(e);
		e.push(1);
		System.out.println(e);
		e.divide();
		System.out.println(e);
		e.divide();
		System.out.println(e);
		e.multiply();
		System.out.println(e);
		e.push(8);
		System.out.println(e);
		e.push(7);
		System.out.println(e);
		e.add();
		System.out.println(e);
		e.subtract();
		System.out.println(e);
		
		System.out.println("Result: " + e.getResult() + "\n");
		
		e.clear();
		
		e.push(70);
		System.out.println(e);
		e.push(14);
		System.out.println(e);
		e.push(4);
		System.out.println(e);
		e.push(5);
		System.out.println(e);
		e.push(15);
		System.out.println(e);
		e.push(3);
		System.out.println(e);
		e.divide();
		System.out.println(e);
		e.multiply();
		System.out.println(e);
		e.subtract();
		System.out.println(e);
		e.subtract();
		System.out.println(e);
		e.divide();
		System.out.println(e);
		e.push(6);
		System.out.println(e);
		e.add();
		System.out.println(e);
		
		System.out.println("Result: " + e.getResult() + "\n");
		
		e.clear();
		
		e.push(3);
		System.out.println(e);
		e.push(5);
		System.out.println(e);
		e.push(6);
		System.out.println(e);
		e.multiply();
		System.out.println(e);
		e.add();
		System.out.println(e);
		e.push(13);
		System.out.println(e);
		e.subtract();
		System.out.println(e);
		e.push(18);
		System.out.println(e);
		e.push(2);
		System.out.println(e);
		e.divide();
		System.out.println(e);
		e.add();
		System.out.println(e);
		
		System.out.println("Result: " + e.getResult() + "\n");
	}
}