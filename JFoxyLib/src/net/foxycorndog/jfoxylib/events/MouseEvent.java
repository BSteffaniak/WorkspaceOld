package net.foxycorndog.jfoxylib.events;

public class MouseEvent extends Event
{
	private int		x, y;
	private int		button;
	
	public MouseEvent(int x, int y, int button)
	{
		this.x      = x;
		this.y      = y;
		
		this.button = button;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getButton()
	{
		return button;
	}
}