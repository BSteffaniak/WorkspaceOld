package net.foxycorndog.arrowide.language.cpp;

import java.io.IOException;

import net.foxycorndog.arrowide.command.Command;
import net.foxycorndog.arrowide.console.ConsoleStream;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class CppLanguage
{
	public  static final Color
			COMMENT_COLOR = new Color(Display.getCurrent(), 40, 140, 0),
			KEYWORD_COLOR = new Color(Display.getCurrent(), 150, 0, 0),
			INCLUDE_COLOR = new Color(Display.getCurrent(), 180, 180, 0);
	
	public static void init()
	{
		
	}
	
	public static void run(final String fileLocation, final ConsoleStream stream)
	{
		int lastInd = fileLocation.lastIndexOf('.');
		lastInd = lastInd < 0 ? fileLocation.length() : lastInd;
		
		String exe  = "\"" + fileLocation.substring(0, lastInd) + ".exe\"";
		
		Command command = new Command(exe, stream);
		
		try
		{
			command.execute();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}