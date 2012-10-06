package net.neonseal.idk.animatedobject.actors.community;

import java.util.ArrayList;

public class PhraseList
{
	private ArrayList<String>  phrases;
	private ArrayList<Mood>    moods;
	
	public PhraseList()
	{
		phrases = new ArrayList<String>();
		moods   = new ArrayList<Mood>();
	}
	
	public void addPhrase(String phrase, Mood mood)
	{
		phrases.add(phrase);
		moods.add(mood);
	}
	
	public String getRandomPhrase()
	{
		int rand = (int)(Math.random() * phrases.size());
		
		return phrases.get(rand);
	}
}