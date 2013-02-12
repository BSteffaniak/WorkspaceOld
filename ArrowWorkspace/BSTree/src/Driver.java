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
		
		t.insert("Bill");
		t.insert("Fred");
		t.insert(j);
		t.insert("Henrietta");
		t.insert("Mr. M");
		t.insert("Noman");
		t.insert("Alfred");
		t.insert("Al");
		t.insert("Fred");
		
		System.out.println("Done: " + t);
		
		System.out.println(t.remove(j));
		
		System.out.println("Done: " + t);
	}
}