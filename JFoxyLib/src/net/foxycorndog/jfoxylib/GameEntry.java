package net.foxycorndog.jfoxylib;

import net.foxycorndog.jfoxylib.components.Frame;

import org.eclipse.swt.widgets.Display;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Feb 15, 2013 at 11:44:47 PM
 * @since	v0.1
 * @version	v0.1
 */
public abstract class GameEntry
{
	private boolean	running;
	
	private Frame	mainFrame;
	
	private Display	display;
	
	public GameEntry()
	{
		display = new Display();
	}
	
	public void start()
	{
		running = true;
		
		while (running)
		{
			if (mainFrame != null && !mainFrame.isOpen())
			{
				running = false;
				break;
			}
			
			if (!display.readAndDispatch())
			{
				loop();
				
				display.sleep();
			}
		}
	}
	
	public void stop()
	{
		running = false;
	}
	
	public void setMainFrame(Frame frame)
	{
		this.mainFrame = frame;
	}
	
	public abstract void loop();
}