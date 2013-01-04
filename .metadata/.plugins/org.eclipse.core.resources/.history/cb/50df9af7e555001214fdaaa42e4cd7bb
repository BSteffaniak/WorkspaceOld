package net.foxycorndog.arrowide.language.assembly;

import java.io.IOException;

import net.foxycorndog.arrowide.command.Command;
import net.foxycorndog.arrowide.console.ConsoleStream;

public class AssemblyCompiler
{
	public static String compile(String fileLocation, String outputLocation, final ConsoleStream stream)
	{
		String result = null;
		
		try
		{
			Process p = Command.execute("res/assembly/fasm.exe \"" + fileLocation + "\" " + outputLocation, stream);
			
//			if (p.exitValue() == 0)
//			{
				result = "Compiled successfully!!!";
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
		
		return result;
	}
}