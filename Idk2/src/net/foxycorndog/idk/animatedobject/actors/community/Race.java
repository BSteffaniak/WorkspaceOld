package net.foxycorndog.idk.animatedobject.actors.community;

import net.foxycorndog.idk.Idk;
import net.foxycorndog.idk.animatedobject.actors.Actor;
import net.foxycorndog.idk.maps.Map;

public class Race extends Actor
{
	private PhraseList phrases;
	
	private int        mood;
	private int        phrasePoint;
	
	public Race(byte row, short width, short height, short xo, short yo,
			float moveSpeed, float phaseSpeed, short attack, short defense,
			short maxHealth, boolean passive, boolean initialize, Map map)
	{
		super(row, width, height, xo, yo, moveSpeed, phaseSpeed, attack, defense,
				maxHealth, passive, initialize, map);
		
		phrases = new PhraseList();
	}
	
	public void addPhrase(String phrase, Mood mood)
	{
		phrases.addPhrase(phrase, mood);
	}
	
	public void talk()
	{
		int point = Idk.getPoint(phrasePoint);
		
		if (point >= 200)
		{
			System.out.println(phrases.getRandomPhrase());
			
			phrasePoint = Idk.counter;
		}
	}
}