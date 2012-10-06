package net.foxycorndog.scrabble;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Words
{
	HashSet<String> words = new HashSet<String>();
	
	public Words()
	{
		BufferedReader br = null;
		
		try
		{
			br = new BufferedReader(new FileReader("res/words.txt"));
		}
		catch (FileNotFoundException ex)
		{
			System.err.println("Error1");
		}
		
		String word;
		
		try
		{
			while ((word = br.readLine()) != null)
			{
				words.add(word.toLowerCase());
			}
		}
		catch (IOException ex)
		{
			System.err.println("Error2");
		}
		
		
	}
	
	public boolean contains(String str)
	{
		return words.contains(str);
	}
}