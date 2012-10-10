package net.foxycorndog.jdoogl.fonts;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import net.foxycorndog.jdoogl.Color;

public class TTFont
{
	HashMap<Character, Integer> widths;
	HashMap<Character, Integer> heights;
	
	
	public TTFont(String location, int size, boolean stream)
	{
		
	}
	
	public void drawString(float x, float y, String text, Color color, int startIndex, int endIndex)
	{
		
	}
	
	public int getWidth(String text)
	{
		char chars[] = text.toCharArray();
		
		int width = 0;
		
		for (char ch : chars)
		{
			width += widths.get(ch) == null ? 0 : widths.get(ch);
		}
		
		return width;
	}
	
	public int getHeight(String text)
	{
		char chars[] = text.toCharArray();
		
		int height = 0;
		
		for (char ch : chars)
		{
			height = heights.get(ch) == null ? 0 : heights.get(ch) > height ? heights.get(ch) : height;
		}
		
		return height;
	}
}