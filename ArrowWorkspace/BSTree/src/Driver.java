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
		System.out.println(t);
		
		t.remove("");
		
		System.out.println(t);
		
		t = new Tree();
		
		t.add("L");
		t.add("D");
		t.add("A");
		t.add("F");
		t.add("B");
		t.add("R");
		t.add("M");
		t.add("U");
		t.add("T");
		t.add("V");
		
		System.out.println(t);
		System.out.println(t.toStringPreOrder());
		
		System.out.println(t.height());
		System.out.println(t.search("B"));
		
		t.remove("A");
		t.remove("B");
		t.remove("U");
		t.remove("R");
		t.remove("L");
		
		System.out.println(t);
		System.out.println(t.toStringPreOrder());
	}
}