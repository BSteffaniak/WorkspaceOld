package net.foxycorndog.arrowide.dialog;

public class DialogEvent
{
	private long   when;
	
	private Object source;
	
	public DialogEvent()
	{
		when = System.currentTimeMillis();
	}
	
	public Object getSource()
	{
		return source;
	}
	
	public void setSource(Object source)
	{
		this.source = source;
	}
	
	public long getWhen()
	{
		return when;
	}
}