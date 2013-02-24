package net.foxycorndog.jfoxylib.listeners;

import net.foxycorndog.jfoxylib.events.MouseEvent;

public interface MouseListener
{
	public void mousePressed(MouseEvent event);
	public void mouseReleased(MouseEvent event);
	public void mouseDown(MouseEvent event);
	public void mouseUp(MouseEvent event);
	public void mouseMoved(MouseEvent event);
}