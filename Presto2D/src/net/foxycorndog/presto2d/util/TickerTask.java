package net.foxycorndog.presto2d.util;

/**
* @author BradenSteffaniak
* 
* This class is used as an extension to the Ticker class. It holds
* the run method that has all of the functionality of the Ticker.
* What goes into the run method is defined by the user when they
* create the TickerTask object instance.
*/
public abstract class TickerTask
{
	
	/**
	* Blank constructor method.
	*/
	public TickerTask()
	{
		
	} // TickerTask()
	
	/**
	* The method that does everything that the user defines.
	*/
	public abstract void run(); // run()
	
} // TickerTask