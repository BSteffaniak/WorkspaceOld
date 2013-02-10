package net.foxycorndog.jfoxylib;

import net.foxycorndog.jfoxylib.components.Frame;

import org.eclipse.swt.widgets.Display;

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