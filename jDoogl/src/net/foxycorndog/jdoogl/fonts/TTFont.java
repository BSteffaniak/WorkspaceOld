package net.foxycorndog.jdoogl.fonts;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.UnicodeFont.DisplayList;
import org.newdawn.slick.font.Glyph;
import org.newdawn.slick.font.GlyphPage;
import org.newdawn.slick.font.effects.ColorEffect;

public class TTFont
{
	UnicodeFont font;
	
	HashMap<Character, Integer> widths;
	HashMap<Character, Integer> heights;
	
	
	public TTFont(String location, int size, boolean stream)
	{
		widths  = new HashMap<Character, Integer>();
		heights = new HashMap<Character, Integer>();
		
		try
		{
//			Font font1 = new Font("TIMES NEW ROMAN", Font.PLAIN, 12);
			
			if (stream)
			{
				try
				{
					Font base = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getClassLoader().getResourceAsStream(location));
					
					Font real = base.deriveFont(Font.PLAIN, 16);
					
					font = new UnicodeFont(real);
				}
				catch (FontFormatException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				font = new UnicodeFont(location, size, false, false);
			}
			
			//8, 16, 24, 32
//			font = new UnicodeFont(location, size, false, false);
//			font = new UnicodeFont(font1);
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect(java.awt.Color.white));
			font.loadGlyphs();
			
//			List l = font.getGlyphPages();
//			
//			for (int i = 0; i < l.size(); i ++)
//			{
//				GlyphPage p = ((GlyphPage)l.get(i));
//				
//				List l2 = p.getGlyphs();
//				
//				for (int j = 0; j < l2.size(); j ++)
//				{
//					Glyph g = ((Glyph)l2.get(j));
//					
//					//System.out.println(g.getCodePoint());
//					
//					widths.put (Unicode.getCodePointChar(g.getCodePoint()), g.getWidth());
//					heights.put(Unicode.getCodePointChar(g.getCodePoint()), g.getHeight());
//				}
//			}
//			
//			if (widths.get(Unicode.getCodePointChar(32)) == null)
//			{
//				widths.put(' ', font.getWidth(" "));
//			}
//			
//			widths.put('F', font.getWidth("F"));
//			widths.put('P', font.getWidth("P"));
//			widths.put('S', font.getWidth("S"));
//			widths.put(':', font.getWidth(":"));
//			widths.put('6', font.getWidth("6"));
//			widths.put('0', font.getWidth("0"));
//			widths.put('1', font.getWidth("1"));
//			
//			System.out.println( "\\u" + Integer.toHexString('/' | 0x10000).substring(1) );
//			
//			System.out.println("32:" + widths.get(Unicode.getCodePointChar(32)) + "!");
			
			for (int i = 32; i < 127; i ++)
			{
				char ch = Unicode.getCodePointChar(i);
				
				widths.put (ch, font.getWidth (ch + " "));
				heights.put(ch, font.getHeight(ch + ""));
			}
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
		
//		System.out.println((font.getGlyphPageWidth()) + ", " + font.getWidth("FPS:asdfasd fasdf asdfasdfsdfASDFASDFASDFADf asdf asdf41!1!1 60") + ", " + getWidth("FPS:asdfasd fasdf asdfasdfsdfASDFASDFASDFADf asdf asdf41!1!1 60"));
	}
	
	public void drawString(float x, float y, String text, Color color, int startIndex, int endIndex)
	{
		font.drawString(x, y, text, color, startIndex, endIndex);
	}
	
	public DisplayList drawDisplayList(float x, float y, String text, Color color, int startIndex, int endIndex)
	{
		return font.drawDisplayList(x, y, text, color, startIndex, endIndex);
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
	
	public int getLegitWidth(String text)
	{
		return font.getWidth(text);
	}
	
	public int getLegitHeight(String text)
	{
		return font.getHeight(text);
	}
}