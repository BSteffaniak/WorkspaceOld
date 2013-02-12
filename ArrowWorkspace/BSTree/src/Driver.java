/**
 * File:          Driver.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 11Feb2013
 * Description:   Class that demonstrates the Tree class.
 */
public class Driver
{
	public static void main(String args[])
	{
		Tree t = new Tree();
		
		String j = "Joe";
		
		t.add("Bill");
		t.add("Fred");
		t.add(j);
		t.add("Henrietta");
		t.add("Mr. M");
		t.add("Noman");
		t.add("Alfred");
		t.add("Al");
		t.add("Fred");
		
//		System.out.println("Done: " + t);
//		
//		System.out.println(t.remove(j));
		
		System.out.println("Done: " + t);
	}
}