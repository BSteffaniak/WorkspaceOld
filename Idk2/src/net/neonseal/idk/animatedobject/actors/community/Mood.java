package net.neonseal.idk.animatedobject.actors.community;

public enum Mood
{
	HAPPY(70, 100), ANGRY(0, 40), SAD(0, 0), SUPRISED(0, 0),
			SCARED(0, 0), DEPRESSED(0, 0), BORED(0, 0), ANNOYED(40, 70);
	
	private int low;
	private int high;
	
	private Mood(int low, int high)
	{
		this.low  = low;
		this.high = high;
	}
	
	public Mood getMood(int low, int high)
	{
		for (Mood mood : Mood.values())
		{
			if (low >= mood.low && high < mood.high)
			{
				return mood;
			}
		}
		
		return null;
	}
	
	public boolean inRange(int value)
	{
		if (value > low && value < high)
		{
			return true;
		}
		
		return false;
	}
}