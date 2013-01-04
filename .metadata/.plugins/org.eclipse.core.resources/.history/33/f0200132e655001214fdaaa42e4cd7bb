package net.foxycorndog.arrowide.language;

import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.assembly.AssemblyCompiler;
import net.foxycorndog.arrowide.language.glsl.GLSLCompiler;
import net.foxycorndog.arrowide.language.java.JavaCompiler;

public class LanguageCompiler
{
	public static String compile(String fileLocation, String code, String outputLocation, ConsoleStream stream)
	{
		if (fileLocation != null)
		{
			String result = null;
			
			String fileName = FileUtils.getFileName(fileLocation);
			
			int language = FileUtils.getLanguage(fileLocation);
			
			if (language == Language.JAVA)
			{
				result = JavaCompiler.compile(fileName, code, outputLocation);
			}
			else if (language == Language.GLSL)
			{
				result = GLSLCompiler.loadVertexShader(fileName, code);
			}
			else if (language == Language.ASSEMBLY)
			{
				result = AssemblyCompiler.compile(fileLocation, outputLocation, stream);
			}
			
			return result;
		}
		
		return "You must save the file before compiling.";
	}
}