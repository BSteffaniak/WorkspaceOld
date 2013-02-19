package net.foxycorndog.jfoxylib;

import java.util.ArrayList;

import net.foxycorndog.jfoxylib.components.Frame;
import net.foxycorndog.jfoxylib.listeners.GameListener;

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
	
	private static ArrayList<GameListener>	listeners;
	
	static
	{
		listeners = new ArrayList<GameListener>();
	}
	
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
				
				notifyGameListeners();
				
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
	
	public static void notifyGameListeners()
	{
		for (int i = listeners.size() - 1; i >= 0; i--)
		{
			listeners.get(i).looped();
		}
	}
	
	public static void addGameListener(GameListener listener)
	{
		listeners.add(listener);
	}
	
	public static void removeGameListener(GameListener listener)
	{
		listeners.remove(listener);
	}
	
	public abstract void loop();
}