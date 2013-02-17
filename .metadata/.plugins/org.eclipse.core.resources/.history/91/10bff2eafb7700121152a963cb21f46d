package net.foxycorndog.jfoxylib.events;

import net.foxycorndog.jfoxylib.components.Button;

public class ButtonEvent
{
	private long	when;
	
	private Button	source;
	
	public ButtonEvent(Button source)
	{
		this.source = source;
		
		when = System.currentTimeMillis();
	}
	
	public long when()
	{
		return when;
	}
	
	public Button getSource()
	{
		return source;
	}
}