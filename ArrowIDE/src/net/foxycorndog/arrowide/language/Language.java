package net.foxycorndog.arrowide.language;

import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.java.Java;

public class Language
{
	public static final int JAVA = 1, GLSL = 2;
	
	public static int getLanguage(String name)
	{
		return FileUtils.getLanguage(name);
	}
	
	public static void run(int language, String fileLocation, ConsoleStream stream)
	{
		if (language == JAVA)
		{
			Java.run(fileLocation, stream);
		}
		else if (language == GLSL)
		{
			
		}
	}
}