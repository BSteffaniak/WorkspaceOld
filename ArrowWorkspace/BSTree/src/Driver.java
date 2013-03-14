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
		
		t.add(10);
		t.add(8);
		t.add(7);
		t.add(9);
		t.add(12);
		t.add(11);
		t.add(13);
		t.add(14);
		t.add(15);
		
		System.out.println(t.mystery());
	}
}