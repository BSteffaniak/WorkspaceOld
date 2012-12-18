package net.foxycorndog.glshaderide.language;

import java.util.HashMap;
import java.util.HashSet;

import net.foxycorndog.glshaderide.GLShaderIDE;
import net.foxycorndog.glshaderide.language.glsl.GLSLKeyword;
import net.foxycorndog.glshaderide.language.java.JavaKeyword;

import org.eclipse.swt.graphics.Color;

import static net.foxycorndog.glshaderide.language.Language.*;

public class Keyword
{
	private String word;
	
	private Color  color;
	
	private static HashMap<Integer, HashMap<String, Keyword>> keywords;
	
	static
	{
		keywords = new HashMap<Integer, HashMap<String, Keyword>>();
		keywords.put(0, new HashMap<String, Keyword>());
		keywords.put(JAVA, new HashMap<String, Keyword>());
		keywords.put(GLSL, new HashMap<String, Keyword>());
		
		JavaKeyword.init();
		GLSLKeyword.init();
	}
	
	public Keyword(int language, String word)
	{
		this.word = word;
		
		keywords.get(language).put(word, this);
	}
	
	public String getWord()
	{
		return word;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public static boolean isKeyword(int language, String word)
	{
		return keywords.get(language).containsKey(word);
	}
	
	public static Keyword getKeyword(int language, String word)
	{
		return keywords.get(language).get(word);
	}
}