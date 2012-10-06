package net.foxycorndog.jdoogl.listeners;

public interface MouseListener
{
	public abstract void onMouseMoved();
	
	public abstract void onMouseDragged(int bottonId);
	
	public abstract void onMouseClicked(int buttonId);
	
	public abstract void onMouseReleased(int buttonId);
	
	public abstract void onMousePressed(int buttonId);
}