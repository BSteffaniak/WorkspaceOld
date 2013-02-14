/**
 * File:          Driver.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 11Feb2013
 * Description:   Class that demonstrates the Tree class.
 */
public class Driver
{
	/**
	 * Main method that runs the test for the Tree.
	 */
	public static void main(String args[])
	{
		Tree t = new Tree();
		
		t.add("Bill");
		t.add("Fred");
		t.add("Joe");
		t.add("Henrietta");
		t.add("Mr. M");
		t.add("Noman");
		t.add("Alfred");
		t.add("Al");
		t.add("Fred");
		
		System.out.println("Done: " + t);
		System.out.println(t.remove("Joe"));
		
		System.out.println("Done: " + t);
	}
}