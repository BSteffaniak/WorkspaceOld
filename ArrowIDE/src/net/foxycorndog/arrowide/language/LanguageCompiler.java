package net.foxycorndog.arrowide.language;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.dialog.FileBrowseDialog;
import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.assembly.AssemblyLanguage;
import net.foxycorndog.arrowide.language.cpp.CppLanguage;
import net.foxycorndog.arrowide.language.glsl.GLSLLanguage;
import net.foxycorndog.arrowide.language.java.JavaLanguage;

import static net.foxycorndog.arrowide.ArrowIDE.CONFIG_DATA;

public class LanguageCompiler
{
	private static ArrayList<CompilerListener> listeners;
	
	static
	{
		listeners = new ArrayList<CompilerListener>();
	}
	
	public static void compile(String fileLocation, String code, String outputLocation, ConsoleStream stream)
	{
		if (fileLocation != null)
		{
			int language = FileUtils.getLanguage(fileLocation);
			
			if (language == Language.CPP)
			{
				if (!CONFIG_DATA.containsKey("g++.location") || !new File(CONFIG_DATA.get("g++.location")).exists())
				{
					FileBrowseDialog gppSearch = new FileBrowseDialog("Specify your c++ compiler. (EX: C:\\MinGW\\bin)", "Location:", FileBrowseDialog.EITHER);
					
					String gppLoc = gppSearch.open();
					
					if (gppLoc != null)
					{
						ArrowIDE.setConfigDataValue("g++.location", gppLoc);
					}
					else
					{
						stream.println("You must specify a valid c++ compiler to compile this program.");
						
						return;
					}
				}
			}
			
			if (language == Language.JAVA)
			{
				JavaLanguage.compile(fileLocation, code, outputLocation, stream, listeners);
			}
			else if (language == Language.GLSL)
			{
				GLSLLanguage.loadVertexShader(fileLocation, code, stream, listeners);
			}
			else if (language == Language.ASSEMBLY)
			{
				AssemblyLanguage.compile(fileLocation, outputLocation, stream, listeners);
			}
			else if (language == Language.CPP)
			{
				CppLanguage.compile(fileLocation, FileUtils.getParentFolder(fileLocation), stream, listeners);
			}
		}
	}
	
	public static void addCompilerListener(CompilerListener listener)
	{
		listeners.add(listener);
	}
}