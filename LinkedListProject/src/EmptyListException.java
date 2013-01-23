/**
 * File:          EmptyListException.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 22Jan2013
 * Description:   Class that extends RuntimeException and is
 * 		thrown whenever a list is empty and is trying to be accessed.
 */
public class EmptyListException extends RuntimeException
{
	public EmptyListException()
	{
		super("The List you are trying to access is empty.");
	}
}