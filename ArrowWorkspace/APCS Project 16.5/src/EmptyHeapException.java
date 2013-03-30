/**
 * File Name:	Driver.java
 * Programming:	APCS
 * Author:		Braden Steffaniak
 * Date:		18Mar2013
 * Description:	Class that tests out the functionality the three
 * 	challenge functions.
 */
public class EmptyHeapException extends RuntimeException
{
	/**
	 * Create an EmptyHeapException with the specified message.
	 * 
	 * @param message The message describing the error in more detail.
	 */
	public EmptyHeapException(String message)
	{
		super(message);
	}
}