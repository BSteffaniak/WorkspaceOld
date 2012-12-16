package net.foxycorndog.glshaderide.compiler;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_MODULATE;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV_MODE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexEnvi;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgram;
import static org.lwjgl.opengl.GL20.glGetShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform2i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform3i;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform4i;
import static org.lwjgl.opengl.GL20.glUniformMatrix2;
import static org.lwjgl.opengl.GL20.glUniformMatrix3;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glVertexAttrib1d;
import static org.lwjgl.opengl.GL20.glVertexAttrib1f;
import static org.lwjgl.opengl.GL20.glVertexAttrib1s;
import static org.lwjgl.opengl.GL20.glVertexAttrib2d;
import static org.lwjgl.opengl.GL20.glVertexAttrib2f;
import static org.lwjgl.opengl.GL20.glVertexAttrib2s;
import static org.lwjgl.opengl.GL20.glVertexAttrib3d;
import static org.lwjgl.opengl.GL20.glVertexAttrib3f;
import static org.lwjgl.opengl.GL20.glVertexAttrib3s;
import static org.lwjgl.opengl.GL20.glVertexAttrib4d;
import static org.lwjgl.opengl.GL20.glVertexAttrib4f;
import static org.lwjgl.opengl.GL20.glVertexAttrib4s;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class Compiler
{
	public static String loadVertexShader(String name, String shaderCode)
	{
		int vertexShader   = glCreateShader(GL_VERTEX_SHADER);
		
		glShaderSource(vertexShader, shaderCode);
		glCompileShader(vertexShader);
		
		String error = "";
		
		if (glGetShader(vertexShader, GL_COMPILE_STATUS) == GL_FALSE)
		{
			error = glGetShaderInfoLog(vertexShader, glGetShader(vertexShader, GL_INFO_LOG_LENGTH));
			
			error = "Vertex shader at \"" + name + "\" was not compiled correctly:\n" + formatError(error, name, false);
		}
		else
		{
			error = "Compiled successfully.";
		}
		
		return error;
	}
	
	public static int loadFragmentShader(String name, String shaderCode)
	{
		int fragmentShader   = glCreateShader(GL_FRAGMENT_SHADER);
		
		glShaderSource(fragmentShader, shaderCode);
		glCompileShader(fragmentShader);
		
		String error = "";
		
		if (glGetShader(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE)
		{
			error = glGetShaderInfoLog(fragmentShader, glGetShader(fragmentShader, GL_INFO_LOG_LENGTH));
			
			error = "Fragment shader at \"" + name + "\" was not compiled correctly:\n" + formatError(error, name, false);
		}
		else
		{
			error = "Compiled successfully.";
		}
		
		return fragmentShader;
	}
	
	public static int loadVertexShaderFromFile(String location)
	{
		int vertexShader   = glCreateShader(GL_VERTEX_SHADER);
		
		StringBuilder shaderSource   = new StringBuilder();
		
		try
		{
			BufferedReader shaderReader = new BufferedReader(new FileReader(location));
			
			String line = null;
			
			while ((line = shaderReader.readLine()) != null)
			{
				shaderSource.append(line).append('\n');
			}
			
			shaderReader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		glShaderSource(vertexShader, shaderSource);
		glCompileShader(vertexShader);
		
		if (glGetShader(vertexShader, GL_COMPILE_STATUS) == GL_FALSE)
		{
			String error = glGetShaderInfoLog(vertexShader, glGetShader(vertexShader, GL_INFO_LOG_LENGTH));
			
			error = formatError(error, getName(location), true);
			
			throw new RuntimeException("Vertex shader at \"" + location + "\" was not compiled correctly:\n\t" + error);
		}
		
		return vertexShader;
	}
	
	public static int loadFragmentShaderFromFile(String location)
	{
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		
		StringBuilder shaderSource   = new StringBuilder();
		
		try
		{
			BufferedReader shaderReader = new BufferedReader(new FileReader(location));
			
			String line = null;
			
			while ((line = shaderReader.readLine()) != null)
			{
				shaderSource.append(line).append('\n');
			}
			
			shaderReader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		glShaderSource(fragmentShader, shaderSource);
		glCompileShader(fragmentShader);
		
		if (glGetShader(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE)
		{
			String error = glGetShaderInfoLog(fragmentShader, glGetShader(fragmentShader, GL_INFO_LOG_LENGTH));
			
			error = formatError(error, getName(location), true);
			
			throw new RuntimeException("Fragment shader at \"" + location + "\" was not compiled correctly:\n\t" + error);
		}
		
		return fragmentShader;
	}
	
	private static String getName(String location)
	{
		int lastI = location.lastIndexOf('/');
		lastI     = Math.max(location.indexOf('\\'), lastI);
		
		return location.substring(lastI + 1, location.length());
	}
	
	private static String formatError(String error, String fileName, boolean tabs)
	{
		String formattedError = "";
		
		for (int i = 0; i < error.length() - 1; i ++)
		{
			try
			{
				if (error.charAt(i) == '0' && error.charAt(i + 1) == '(')
				{
					formattedError += fileName + ":";
				}
				else if (error.charAt(i) == ')' || error.charAt(i) == '(')
				{
					
				}
				else
				{
					formattedError += error.charAt(i);
					
					if (tabs && error.charAt(i) == '\n')
					{
						formattedError += '\t';
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				
			}
		}
		
		return formattedError;
	}
}