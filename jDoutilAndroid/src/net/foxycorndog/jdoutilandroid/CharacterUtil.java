package net.foxycorndog.jdoutilandroid;

public class CharacterUtil
{
	public static char toUpperCase(char ch)
	{
		return (char)((int)ch & 65503);
	}
	
	public static char toLowerCase(char ch)
	{
		return (char)((int)ch | 32);
	}
}