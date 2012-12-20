package net.foxycorndog.glshaderide.compiler;

import net.foxycorndog.glshaderide.file.FileUtils;
import net.foxycorndog.glshaderide.language.Language;

public class Compiler
{
	
	
	public static String compile(String fileLocation, String code)
	{
		if (fileLocation != null)
		{
			String result = null;
			
			String fileName = fileLocation.substring(fileLocation.lastIndexOf('/') + 1, fileLocation.lastIndexOf('.'));;
			
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