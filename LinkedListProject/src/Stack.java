/**
 * File:          Stack.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 22Jan2013
 * Description:   Class that creates the method signatures
 * 		for a stack interface.
 */
public interface Stack
{
	/**
	 * Returns whether the list contains nothing or not.
	 * 
	 * @return Whether list list is empty or not.
	 */
	public boolean isEmpty();
	
	/**
	 * Put an object on the top of the stack.
	 * 
	 * @param o The object to put.
	 * @return The object that was put.
	 */
	public Object push(Object o);
	
	/**
	 * Remove the object at the top of the stack.
	 * 
	 * @return The object that was removed.
	 */
	public Object pop();
	
	/**
	 * Gets the front object from the list without
	 * removing it from the list.
	 * 
	 * @return The first object in the list.
	 */
	public Object peek();
	
	/**
	 * @return position of element on stack, top
	 * 		element = 1 ...return -1 if not found
	 */
	public int search(Object o);
}