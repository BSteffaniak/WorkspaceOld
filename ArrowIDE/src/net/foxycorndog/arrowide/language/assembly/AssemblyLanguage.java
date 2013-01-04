package net.foxycorndog.arrowide.language.assembly;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.command.Command;
import net.foxycorndog.arrowide.console.ConsoleStream;

public class AssemblyLanguage
{
	public static final Color
			COMMENT_COLOR = new Color(Display.getCurrent(), 40, 140, 0),
			KEYWORD_COLOR = new Color(Display.getCurrent(), 150, 0, 0);
	
	public static void init()
	{
		
	}
	
	public static void run(String fileLocation, ConsoleStream stream)
	{
		int lastInd = fileLocation.lastIndexOf('.');
		lastInd = lastInd < 0 ? fileLocation.length() : lastInd;
		
		String exe  = fileLocation.substring(0, lastInd) + ".exe";
		
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