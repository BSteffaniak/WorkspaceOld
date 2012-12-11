package net.foxycorndog.jdoogl.shader;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.GL_FALSE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.lwjgl.BufferUtils;


public class ShaderUtils
{
	private static int uniformCacheSize, uniformCacheId;
	private static int currentProgram, lastProgramCreated;
	
	private static ArrayList<HashMap<String, Integer>> uniformCache;
	
	static
	{
		uniformCacheSize = 50;
		
		uniformCache     = new ArrayList<HashMap<String, Integer>>();
	}
	
	public static int genShaderProgramId()
	{
		int id = glCreateProgram();
		
		try
		{
			uniformCache.add(id, new HashMap<String, Integer>());
		}
		catch (IndexOutOfBoundsException e)
		{
			for (int i = lastProgramCreated; i < id; i ++)
			{
				uniformCache.add(i, null);
			}
			
			uniformCache.add(id, new HashMap<String, Integer>());
		}
		
		lastProgramCreated = id;
		
		return id;
	}
	
	public static void genShaderProgram(int programId)
	{
		glLinkProgram(programId);
	}
	
	public static void useShaderProgram(int programId)
	{
		glUseProgram(programId);
		
		currentProgram = programId == 0 ? currentProgram : programId;
	}
	
	public static boolean validateProgram(int programId)
	{
		glValidateProgram(programId);
		
		return glGetProgram(programId, GL_VALIDATE_STATUS) != GL_FALSE;
	}
	
	public static void deleteProgram(int programId)
	{
		glDeleteProgram(programId);
	}
	
	public static void deleteShader(int shaderId)
	{
		glDeleteShader(shaderId);
	}
	
	public static void attachShader(int shaderId, int shaderProgramId)
	{
		glAttachShader(shaderProgramId, shaderId);
	}
	
	public static int loadVertexShader(String location)
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
			
			error = formatError(error, getName(location));
			
			throw new RuntimeException("Vertex shader at \"" + location + "\" was not compiled correctly:\n\t" + error);
		}
		
		return vertexShader;
	}
	
	public static int loadFragmentShader(String location)
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
			
			error = formatError(error, getName(location));
			
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
	
	private static String formatError(String error, String fileName)
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
					
					if (error.charAt(i) == '\n')
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
	
	public static int loadShaderProgram(String vertexShaderLocations[], String fragmentShaderLocation)
	{
		return loadShaderProgram(vertexShaderLocations, new String[] { fragmentShaderLocation });
	}
	
	public static int loadShaderProgram(String vertexShaderLocation, String fragmentShaderLocations[])
	{
		return loadShaderProgram(new String[] { vertexShaderLocation }, fragmentShaderLocations);
	}
	
	public static int loadShaderProgram(String vertexShaderLocation, String fragmentShaderLocation)
	{
		int shaderProgram  = ShaderUtils.genShaderProgramId();
		int vertexShader   = ShaderUtils.loadVertexShader(vertexShaderLocation);
		int fragmentShader = ShaderUtils.loadFragmentShader(fragmentShaderLocation);
		ShaderUtils.attachShader(vertexShader, shaderProgram);
		ShaderUtils.attachShader(fragmentShader, shaderProgram);
		ShaderUtils.genShaderProgram(shaderProgram);
		
		return shaderProgram;
	}
	
	public static int loadShaderProgram(String vertexShaderLocations[], String fragmentShaderLocations[])
	{
		int shaderProgram  = ShaderUtils.genShaderProgramId();
		
		for (int i = 0; i < vertexShaderLocations.length; i ++)
		{
			int vertexShader   = ShaderUtils.loadVertexShader(vertexShaderLocations[i]);
			ShaderUtils.attachShader(vertexShader, shaderProgram);
		}
		
		for (int i = 0; i < fragmentShaderLocations.length; i ++)
		{
			int fragmentShader = ShaderUtils.loadFragmentShader(fragmentShaderLocations[i]);
			ShaderUtils.attachShader(fragmentShader, shaderProgram);
		}
		
		ShaderUtils.genShaderProgram(shaderProgram);
		
		return shaderProgram;
	}
	
	public static int getUniformLocation(int programId, String uniformName)
	{
		int location = 0;
		
		try
		{
			HashMap<String, Integer> map = uniformCache.get(programId);
			
			if (map != null)
			{
				if (map.containsKey(uniformName))
				{
					location = map.get(uniformName);
				}
				else
				{
					location = glGetUniformLocation(programId, uniformName);
					
					map.put(uniformName, location);
				}
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new IllegalArgumentException("No such programId: " + programId);
		}
		
		return location;
	}
	
	public static void uniform4i(int uniformLocation, int x, int y, int z, int w)
	{
		glUniform4i(uniformLocation, x, y, z, w);
	}
	
	public static void uniform4i(String uniformName, int x, int y, int z, int w)
	{
		glUniform4i(getUniformLocation(currentProgram, uniformName), x, y, z, w);
	}
	
	public static void uniform3i(int uniformLocation, int x, int y, int z)
	{
		glUniform3i(uniformLocation, x, y, z);
	}
	
	public static void uniform3i(String uniformName, int x, int y, int z)
	{
		glUniform3i(getUniformLocation(currentProgram, uniformName), x, y, z);
	}
	
	public static void uniform2i(int uniformLocation, int x, int y)
	{
		glUniform2i(uniformLocation, x, y);
	}
	
	public static void uniform2i(String uniformName, int x, int y)
	{
		glUniform2i(getUniformLocation(currentProgram, uniformName), x, y);
	}
	
	public static void uniform1i(int uniformLocation, int x)
	{
		glUniform1i(uniformLocation, x);
	}
	
	public static void uniform1i(String uniformName, int x)
	{
		glUniform1i(getUniformLocation(currentProgram, uniformName), x);
	}
	
	public static void uniform4f(int uniformLocation, float x, float y, float z, float w)
	{
		glUniform4f(uniformLocation, x, y, z, w);
	}
	
	public static void uniform4f(String uniformName, float x, float y, float z, float w)
	{
		glUniform4f(getUniformLocation(currentProgram, uniformName), x, y, z, w);
	}
	
	public static void uniform3f(int uniformLocation, float x, float y, float z)
	{
		glUniform3f(uniformLocation, x, y, z);
	}
	
	public static void uniform3f(String uniformName, float x, float y, float z)
	{
		glUniform3f(getUniformLocation(currentProgram, uniformName), x, y, z);
	}
	
	public static void uniform2f(int uniformLocation, float x, float y)
	{
		glUniform2f(uniformLocation, x, y);
	}
	
	public static void uniform2f(String uniformName, float x, float y)
	{
		glUniform2f(getUniformLocation(currentProgram, uniformName), x, y);
	}
	
	public static void uniform1f(int uniformLocation, float x)
	{
		glUniform1f(uniformLocation, x);
	}
	
	public static void uniform1f(String uniformName, float x)
	{
		glUniform1f(getUniformLocation(currentProgram, uniformName), x);
	}
	
	public static void uniformMatrix2f(String uniformName, FloatBuffer buffer)
	{
		glUniformMatrix2(getUniformLocation(currentProgram, uniformName), false, buffer);
	}
	
	public static void uniformMatrix2f(String uniformName, boolean transpose, FloatBuffer buffer)
	{
		glUniformMatrix2(getUniformLocation(currentProgram, uniformName), transpose, buffer);
	}
	
	public static void uniformMatrix2f(String uniformName, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix2(getUniformLocation(currentProgram, uniformName), false, buffer);
	}
	
	public static void uniformMatrix2f(String uniformName, boolean transpose, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix2(getUniformLocation(currentProgram, uniformName), transpose, buffer);
	}
	
	public static void uniformMatrix3f(String uniformName, FloatBuffer buffer)
	{
		glUniformMatrix3(getUniformLocation(currentProgram, uniformName), false, buffer);
	}
	
	public static void uniformMatrix3f(String uniformName, boolean transpose, FloatBuffer buffer)
	{
		glUniformMatrix3(getUniformLocation(currentProgram, uniformName), transpose, buffer);
	}
	
	public static void uniformMatrix3f(String uniformName, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix3(getUniformLocation(currentProgram, uniformName), false, buffer);
	}
	
	public static void uniformMatrix3f(String uniformName, boolean transpose, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix3(getUniformLocation(currentProgram, uniformName), transpose, buffer);
	}
	
	public static void uniformMatrix4f(String uniformName, FloatBuffer buffer)
	{
		glUniformMatrix4(getUniformLocation(currentProgram, uniformName), false, buffer);
	}
	
	public static void uniformMatrix4f(String uniformName, boolean transpose, FloatBuffer buffer)
	{
		glUniformMatrix4(getUniformLocation(currentProgram, uniformName), transpose, buffer);
	}
	
	public static void uniformMatrix4f(String uniformName, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix4(getUniformLocation(currentProgram, uniformName), false, buffer);
	}
	
	public static void uniformMatrix4f(String uniformName, boolean transpose, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix4(getUniformLocation(currentProgram, uniformName), transpose, buffer);
	}
	
	public static int getAttribLocation(int programId, String attribName)
	{
		return glGetAttribLocation(programId, attribName);
	}
	
	public static void vertexAttrib4s(int attribLocation, short x, short y, short z, short w)
	{
		glVertexAttrib4s(attribLocation, x, y, z, w);
	}
	
	public static void vertexAttrib3s(int attribLocation, short x, short y, short z)
	{
		glVertexAttrib3s(attribLocation, x, y, z);
	}
	
	public static void vertexAttrib2s(int attribLocation, short x, short y)
	{
		glVertexAttrib2s(attribLocation, x, y);
	}
	
	public static void vertexAttrib1s(int attribLocation, short x)
	{
		glVertexAttrib1s(attribLocation, x);
	}
	
	public static void vertexAttrib4f(int attribLocation, float x, float y, float z, float w)
	{
		glVertexAttrib4f(attribLocation, x, y, z, w);
	}
	
	public static void vertexAttrib3f(int attribLocation, float x, float y, float z)
	{
		glVertexAttrib3f(attribLocation, x, y, z);
	}
	
	public static void vertexAttrib2f(int attribLocation, float x, float y)
	{
		glVertexAttrib2f(attribLocation, x, y);
	}
	
	public static void vertexAttrib1f(int attribLocation, float x)
	{
		glVertexAttrib1f(attribLocation, x);
	}
	
	public static void vertexAttrib4d(int attribLocation, double x, double y, double z, double w)
	{
		glVertexAttrib4d(attribLocation, x, y, z, w);
	}
	
	public static void vertexAttrib3d(int attribLocation, double x, double y, double z)
	{
		glVertexAttrib3d(attribLocation, x, y, z);
	}
	
	public static void vertexAttrib2d(int attribLocation, double x, double y)
	{
		glVertexAttrib2d(attribLocation, x, y);
	}
	
	public static void vertexAttrib1d(int attribLocation, double x)
	{
		glVertexAttrib1d(attribLocation, x);
	}
	
	public static void setUniformCacheSize(int size)
	{
		uniformCacheSize = size;
	}
}