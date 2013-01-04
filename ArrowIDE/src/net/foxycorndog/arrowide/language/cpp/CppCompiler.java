package net.foxycorndog.arrowide.language.cpp;

import java.io.IOException;
import java.util.ArrayList;

import net.foxycorndog.arrowide.command.Command;
import net.foxycorndog.arrowide.command.CommandListener;
import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.CompilerListener;

import static net.foxycorndog.arrowide.ArrowIDE.CONFIG_DATA;

public class CppCompiler
{
	public static void compile(String fileLocation, String outputLocation, final ConsoleStream stream, final ArrayList<CompilerListener> compilerListeners)
	{
		if (!CONFIG_DATA.containsKey("g++.location"))
		{
			FileBrowseDialog gppSearch = new FileBrowseDialog;
			
			String gppLoc = gppSearch.open();
			
			if (gppLoc != null)
			{
				
			}
			else
			{
				stream.println("You must specify a valid gcc compiler to compile this program.");
				
				return;
			}
		}
		
		try
		{
//			g++ hello.C -o hello
			String text = "\"" + CONFIG_DATA.get("g++.location") + "/g++.exe\" \"" + fileLocation + "\" -o \"" + outputLocation + (outputLocation.equals("") ? "" : "/") + FileUtils.getFileNameWithoutExtension(fileLocation) + "\"";
			
			System.out.println(text);
			
			Command command = new Command(text, stream);
			
			command.addCommandListener(new CommandListener()
			{
				public void resultReceived(int result)
				{
					for (int i = compilerListeners.size() - 1; i >= 0; i--)
					{
						compilerListeners.get(i).compiled(result);
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
