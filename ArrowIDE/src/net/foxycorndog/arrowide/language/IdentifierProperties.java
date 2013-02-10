package net.foxycorndog.arrowide.language;

import java.util.Arrays;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class IdentifierProperties
{
	private final int		AMOUNT;
	
	public  final Color		COLOR;
	
	public  final char		PREVIOUS_CHARS[][], NEXT_CHARS[][];
	
	public  final String	PREFIXES[][], PREVIOUS_WORDS[][], NEXT_WORDS[][];
	
	public IdentifierProperties(char previousChars, char nextChars, Color color)
	{
		this(new char[] { previousChars }, new char[] { nextChars }, new String[] {}, new String[] {}, color);
	}
	
	public IdentifierProperties(char previousChars[], char nextChars, Color color)
	{
		this(previousChars, new char[] { nextChars }, new String[] {}, new String[] {}, color);
	}
	
	public IdentifierProperties(char previousChars, char nextChars[], Color color)
	{
		this(new char[] { previousChars }, nextChars, new String[] {}, new String[] {}, color);
	}
	
	public IdentifierProperties(char previousChars[], char nextChars[], Color color)
	{
		this(previousChars, nextChars, new String[] {}, new String[] {}, color);
	}
	
	public IdentifierProperties(char previousChars, char nextChars, String prefix, Color color)
	{
		this(new char[] { previousChars }, new char[] { nextChars }, new String[] { prefix }, new String[] {}, color);
	}
	
	public IdentifierProperties(char previousChars[], char nextChars, String prefix, Color color)
	{
		this(previousChars, new char[] { nextChars }, new String[] { prefix }, new String[] {}, color);
	}
	
	public IdentifierProperties(char previousChars, char nextChars[], String prefix, Color color)
	{
		this(new char[] { previousChars }, nextChars, new String[] { prefix }, new String[] {}, color);
	}
	
	public IdentifierProperties(char previousChars, char nextChars, String prefixes[], Color color)
	{
		this(new char[] { previousChars }, new char[] { nextChars }, prefixes, new String[] {}, color);
	}
	
	public IdentifierProperties(char previousChars[], char nextChars, String prefixes[], Color color)
	{
		this(previousChars, new char[] { nextChars }, prefixes, new String[] {}, color);
	}
	
	public IdentifierProperties(char previousChars, char nextChars[], String prefixes[], Color color)
	{
		this(new char[] { previousChars }, nextChars, prefixes, new String[] {}, color);
	}
	
	public IdentifierProperties(char previousChars, char nextChars, String prefix, String previousWords, Color color)
	{
		this(new char[] { previousChars }, new char[] { nextChars }, new String[] { prefix }, new String[] { previousWords }, color);
	}
	
	public IdentifierProperties(char previousChars[], char nextChars, String prefix, String previousWords, Color color)
	{
		this(previousChars, new char[] { nextChars }, new String[] { prefix }, new String[] { previousWords }, color);
	}
	
	public IdentifierProperties(char previousChars, char nextChars[], String prefix, String previousWords, Color color)
	{
		this(new char[] { previousChars }, nextChars, new String[] { prefix }, new String[] { previousWords }, color);
	}
	
	public IdentifierProperties(char previousChars, char nextChars, String prefixes[], String previousWords[], Color color)
	{
		this(new char[] { previousChars }, new char[] { nextChars }, prefixes, previousWords, color);
	}
	
	public IdentifierProperties(char previousChars[], char nextChars, String prefixes[], String previousWords[], Color color)
	{
		this(previousChars, new char[] { nextChars }, prefixes, previousWords, color);
	}
	
	public IdentifierProperties(char previousChars, char nextChars[], String prefixes[], String previousWords[], Color color)
	{
		this(new char[] { previousChars }, nextChars, prefixes, previousWords, color);
	}
	
	public IdentifierProperties(char previousChars, char nextChars, String prefix, String previousWords[], Color color)
	{
		this(new char[] { previousChars }, new char[] { nextChars }, new String[] { prefix }, previousWords, color);
	}
	
	public IdentifierProperties(char previousChars[], char nextChars, String prefix, String previousWords[], Color color)
	{
		this(previousChars, new char[] { nextChars }, new String[] { prefix }, previousWords, color);
	}
	
	public IdentifierProperties(char previousChars, char nextChars[], String prefix, String previousWords[], Color color)
	{
		this(new char[] { previousChars }, nextChars, new String[] { prefix }, previousWords, color);
	}
	
	public IdentifierProperties(char previousChars, char nextChars, String prefixes[], String previousWords, Color color)
	{
		this(new char[] { previousChars }, new char[] { nextChars }, prefixes, new String[] { previousWords }, color);
	}
	
	public IdentifierProperties(char previousChars[], char nextChars, String prefixes[], String previousWords, Color color)
	{
		this(previousChars, new char[] { nextChars }, prefixes, new String[] { previousWords }, color);
	}
	
	public IdentifierProperties(char previousChars, char nextChars[], String prefixes[], String previousWords, Color color)
	{
		this(new char[] { previousChars }, nextChars, prefixes, new String[] { previousWords }, color);
	}
	
	public IdentifierProperties(char previousChars[], char nextChars[], String prefixes[], String previousWords[], Color color)
	{
		this(new char[][] { previousChars }, new char[][] { nextChars }, new String[][] { prefixes }, new String[][] { previousWords }, new String[][] {}, color);
	}
	
	public IdentifierProperties(char previousChars[][], char nextChars[][], String prefixes[][], String previousWords[][], String nextWords[][], Color color)
	{
		this.COLOR          = color;
		
		this.PREVIOUS_CHARS = previousChars;
		this.NEXT_CHARS     = nextChars;
		
		this.PREFIXES       = prefixes;
		this.PREVIOUS_WORDS = previousWords;
		this.NEXT_WORDS     = nextWords;
		
		this.AMOUNT         = Math.max(Math.max(Math.max(Math.max(previousWords.length, prefixes.length), nextChars.length), previousChars.length), NEXT_WORDS.length);
	}
	
	public boolean isQualified(char previousChar, char nextChar, String currentWord, String previousWord, String nextWord)
	{
		if (AMOUNT <= 0)
		{
			return true;
		}
		
		for (int i = 0; i < AMOUNT; i++)
		{
			boolean found = PREVIOUS_CHARS.length <= i || PREVIOUS_CHARS[i].length <= 0;
			
			for (int j = 0; !found && j < PREVIOUS_CHARS[i].length; j++)
			{
				if (PREVIOUS_CHARS[i][j] == previousChar)
				{
					found = true;
				}
			}
			
			if (!found)
			{
				continue;
			}
			
			found = NEXT_CHARS.length <= i || NEXT_CHARS[i].length <= 0;
			
			for (int j = 0; !found && j < NEXT_CHARS[i].length; j++)
			{
				if (NEXT_CHARS[i][j] == nextChar)
				{
					found = true;
				}
			}
			
			if (!found)
			{
				continue;
			}
			
			found = PREVIOUS_WORDS.length <= i || PREVIOUS_WORDS[i].length <= 0;
			
			for (int j = 0; !found && j < PREVIOUS_WORDS[i].length; j++)
			{
				if (PREVIOUS_WORDS[i][j].equals(previousWord))
				{
					found = true;
				}
			}
			
			if (!found)
			{
				continue;
			}
			
			found = NEXT_WORDS.length <= i || NEXT_WORDS[i].length <= 0;
			
			for (int j = 0; !found && j < NEXT_WORDS[i].length; j++)
			{
				if (NEXT_WORDS[i][j].equals(nextWord))
				{
					found = true;
				}
			}
			
			if (!found)
			{
				continue;
			}
			
			found = PREFIXES.length <= i || PREFIXES[i].length <= 0;
			
			for (int j = 0; !found && j < PREFIXES[i].length; j++)
			{
				if (currentWord.startsWith(PREFIXES[i][j]))
				{
					found = true;
				}
			}
			
			if (!found)
			{
				continue;
			}
			
			return true;
		}
		
		return false;
	}
}