package net.foxycorndog.arrowide.language.assembly;

import java.io.IOException;
import java.util.ArrayList;

import net.foxycorndog.arrowide.command.Command;
import net.foxycorndog.arrowide.command.CommandListener;
import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.CompilerListener;

import static net.foxycorndog.arrowide.ArrowIDE.PROPERTIES;

public class AssemblyCompiler
{
	public static void compile(String fileLocation, String outputLocation, final ConsoleStream stream, final ArrayList<CompilerListener> compilerListeners)
	{
//		if (PROPERTIES.get("os.name").equals("macosx"))
//		{
//			throw new UnsupportedOperationException("Compiling assembly on macosx is unsupported.");
//		}
		
		try
		{
			String text = null;
			
			String fileName = FileUtils.getFileNameWithoutExtension(fileLocation);
			
			boolean nasm = true;
			if (nasm)
			{
				text = "\"res/assembly/nasm/" + PROPERTIES.get("os.name") + "/nasm" + PROPERTIES.get("os.executable.extension") + "\" -f bin \"" + fileLocation + "\" -o \"" + outputLocation + fileName + ".com\" -w+orphan-labels";
			}
			else
			{
				text = "\"res/assembly/fasm/" + PROPERTIES.get("os.name") + "/fasm" + PROPERTIES.get("os.executable.extension") + "\" \"" + fileLocation + "\" \"" + outputLocation + fileName + ".exe\" ";
			}
			
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