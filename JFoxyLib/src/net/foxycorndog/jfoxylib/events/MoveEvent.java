package net.foxycorndog.jfoxylib.events;

public class MoveEvent
{
	private long when;
	
	public MoveEvent()
	{
		this.when = System.currentTimeMillis();
	}
}