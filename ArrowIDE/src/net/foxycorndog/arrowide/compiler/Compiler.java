package net.foxycorndog.arrowide.compiler;

import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.Language;

public class Compiler
{
	
	
	public static String compile(String fileLocation, String code)
	{
		if (fileLocation != null)
		{
			String result = null;
			
			String fileName = FileUtils.getFileName(fileLocation);
			
			int language = FileUtils.getLanguage(fileLocation);
			
			if (language == Language.JAVA)
			{
				result = JavaCompiler.compile(fileName, code);
			}
			else if (language == Language.GLSL)
			{
				result = GLSLCompiler.loadVertexShader(fileName, code);
			}
			
			return result;
		}
		
		return "You must save the file before compiling.";
	}
}