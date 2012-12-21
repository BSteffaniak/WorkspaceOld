package net.foxycorndog.arrowide.file;

import java.io.File;

import net.foxycorndog.arrowide.language.Keyword;
import net.foxycorndog.arrowide.language.Language;

public class FileUtils
{
	private static final String GLSL_VERTEX_ENDINGS[], GLSL_FRAGMENT_ENDINGS[], JAVA_ENDINGS[];
	
	static
	{
		GLSL_VERTEX_ENDINGS = new String[] { "vs", "vert" };
		GLSL_FRAGMENT_ENDINGS = new String[] { "fs", "frag", "shade", "shad", "sha" };
		
		JAVA_ENDINGS = new String[] { "java" };
	}
	
	public static int getLanguage(String name)
	{
		String ending = name.substring(name.lastIndexOf('.') + 1).toLowerCase();
		
		int language  = 0;
		
		if (contains(ending, GLSL_VERTEX_ENDINGS) || contains(ending, GLSL_FRAGMENT_ENDINGS))
		{
			language = Language.GLSL;
		}
		else if (contains(ending, JAVA_ENDINGS))
		{
			language = Language.JAVA;
		}
		
		return language;
	}
	
	public static boolean contains(String str, String endings[])
	{
		for (String ending : endings)
		{
			if (str.equals(ending))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static void delete(File file)
	{
		if (file.isDirectory())
		{
			File subFiles[] = file.listFiles();
			
			for (int i = 0; i < subFiles.length; i ++)
			{
				delete(subFiles[i]);
			}
		}
		
		file.delete();
	}
	
	public static boolean isFileName(String location)
	{
		for (int i = location.length() - 1; i >= 0 && location.charAt(i) != '/'; i --)
		{
			if (location.charAt(i) == '.')
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static String getParentFolder(String location)
	{
		int index = location.length() - 1;
		
		while ()
		{
			
		}
	}
	
	public static String removeEndingSlashes(String location)
	{
		int lastIndex;
		
		while (location.charAt(location.length() - 1) == '/')
		{
			location = location.substring(0, location.length() - 1);
		}
		
		return location.substring(0, lastIndex);
	}
}