package net.foxycorndog.glshaderide.compiler;

public class Compiler
{
	public static final String GLSL_VERTEX_ENDINGS[], GLSL_FRAGMENT_ENDINGS[], JAVA_ENDINGS[];
	
	static
	{
		GLSL_VERTEX_ENDINGS = new String[] { ".vs", ".vert" };
		GLSL_FRAGMENT_ENDINGS = new String[] { ".fs", ".frag", ".shade", ".shad", ".sha" };
		
		JAVA_ENDINGS = new String[] { ".java" };
	}
	
	public static String compile(String fileLocation, String code)
	{
		if (fileLocation != null)
		{
			String result = null;
			
			String fileName = fileLocation.substring(fileLocation.lastIndexOf('/') + 1, fileLocation.lastIndexOf('.'));;
			
			if (endsWith(fileLocation, JAVA_ENDINGS))
			{
				result = JavaCompiler.compile(fileName, code);
			}
			else if (endsWith(fileLocation, GLSL_VERTEX_ENDINGS) || endsWith(fileLocation, GLSL_FRAGMENT_ENDINGS))
			{
				result = GLSLCompiler.loadVertexShader(fileName, code);
			}
	//		else if (endsWith(fileLocation, GLSL_FRAGMENT_ENDINGS))
	//		{
	//			result = GLSLCompiler.loadFragmentShader(fileName, code);
	//		}
			
			return result;
		}
		
		return "You must save the file before compiling.";
	}
	
	public static boolean endsWith(String str, String endings[])
	{
		for (String ending : endings)
		{
			if (str.toLowerCase().endsWith(ending))
			{
				return true;
			}
		}
		
		return false;
	}
}