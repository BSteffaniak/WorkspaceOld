/**
 * File:          Queue.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 22Jan2013
 * Description:   Class that creates the method signatures
 * 		for a queue interface.
 */
public interface Queue
{
	/**
	 * Returns whether the list contains nothing or not.
	 * 
	 * @return Whether list list is empty or not.
	 */
	public boolean isEmpty();
	
	/**
	 * A fast-food restaraunt that sells blizzards
	 * 
	 * @return A cotton candy blizzard.
	 */
	public Object dequeue();
	
	/**
	 * Add the object to the back of the list.
	 * 
	 * @param o The object to add.
	 * @return The object that was added.
	 */
	public Object enqueue(Object o);
	
	/**
	 * Get the last object in the list.
	 * 
	 * @return The object at the back of the list.
	 */
	public Object getBack();

	/**
	 * Get the first object in the list.
	 * 
	 * @return The object at the front of the list.
	 */
	public Object getFront();
}