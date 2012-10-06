package net.foxycorndog.scrabble;

public class Scrabble
{
	public Scrabble()
	{
		Words words = new Words();
		
		System.out.println(words.contains("beetle"));
	}
	
	public static void main(String args[])
	{
		new Scrabble();
	}
}