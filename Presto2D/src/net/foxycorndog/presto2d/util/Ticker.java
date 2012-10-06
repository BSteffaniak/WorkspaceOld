package net.foxycorndog.presto2d.util;

/**
* @author BradenSteffaniak
* 
* This class is used to simplify the process of creating a thread.
* Instead of having to define a new Thread, create a loop, and
* tell it to delay/sleep, all you have to do is create an instance
* of this class and write the code in the TickerTask run method.
*/
public class Ticker
{
	private int delay;
	
	/**
	* Blank constructor method.
	*/
	public Ticker()
	{
		
	} // Ticker()
	
	/**
	* The start method, used to receive the delay data and what to do
	* within the thread loop, from the user.
	* 
	* <pre>
	* start(1000, new TickerTask()
	* {
	*     int t = 0;
	*     String word;
	* 
	*     public void run()
	*     {
	*         if (t % 2 == 0)
	*         {
	*             word = "tick";
	*         }
	*         else
	*         {
	*             word = "tock";
	*         }
	*         
	*         System.out.println(word);
	*         
	*         t ++;
	*     }
	* });
	* </pre>
	* 
	* @param delay How long to delay before running again (millisec).
	* @param task The task to be performed within the loop.
	*/
	public void start(int delay, final TickerTask task)
	{
		this.delay = delay;
		
		new Thread()
		{
			public void run()
			{
				while (true)
				{
					task.run();
					
					if (getDelay() > 0)
					{
						try
						{
							Thread.sleep(getDelay());
						} // try
						catch (InterruptedException ex)
						{
							
						} // catch()
					} // if()
				} // while()
			} // run()
		}.start(); // Thread()/start()
	} // start()
	
	public void setDelay(int delay)
	{
		this.delay = delay;
	}
	
	public int getDelay()
	{
		return delay;
	}
	
} // Ticker