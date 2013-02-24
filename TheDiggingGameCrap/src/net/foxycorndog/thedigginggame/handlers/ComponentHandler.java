package net.foxycorndog.thedigginggame.handlers;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import net.foxycorndog.thedigginggame.Display;
import net.foxycorndog.thedigginggame.TheDiggingGame;

public class ComponentHandler implements ComponentListener
{
	private Display display;
	
	public ComponentHandler(Display tdg)
	{
		this.display = tdg;
	}
	
	@Override
	public void componentHidden(ComponentEvent e)
	{
		
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
		
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		display.setDisplaySize(display.getWidth(), display.getHeight());
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
		
	}
	
}