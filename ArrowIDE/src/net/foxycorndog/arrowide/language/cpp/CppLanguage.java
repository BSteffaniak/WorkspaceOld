package net.foxycorndog.arrowide.language.cpp;

import static net.foxycorndog.arrowide.ArrowIDE.CONFIG_DATA;

import java.io.IOException;
import java.util.ArrayList;

import net.foxycorndog.arrowide.command.Command;
import net.foxycorndog.arrowide.command.CommandListener;
import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.CompilerListener;

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
		
		Command command = new Command(exe, stream, null);
		
		try
		{
			command.execute();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void compile(String fileLocation, String outputLocation, final ConsoleStream stream, final ArrayList<CompilerListener> compilerListeners)
	{
		try
		{
//			g++ hello.C -o hello
			String text = "\"" + CONFIG_DATA.get("g++.location") + "/g++\" \"" + fileLocation + "\" -o \"" + outputLocation + (outputLocation.equals("") ? "" : "/") + FileUtils.getFileNameWithoutExtension(fileLocation) + "\"";
			
			System.out.println(text);
			
			Command command = new Command(text, stream, null);
			
			String outputFile = outputLocation + FileUtils.getFileName(fileLocation);
			
			final String outputFiles[] = new String[] { outputFile };
			
			command.addCommandListener(new CommandListener()
			{
				public void resultReceived(int result)
				{
					for (int i = compilerListeners.size() - 1; i >= 0; i--)
					{
						compilerListeners.get(i).compiled(outputFiles, result);
					}
				}
			});
			
			command.execute();
			
//			if (p.exitValue() == 0)
//			{
//				result = "Compiled successfully!!!";
//			}
//			else
//			{
//				result = "";
//			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}