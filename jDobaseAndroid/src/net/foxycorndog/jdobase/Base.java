package net.foxycorndog.jdobase;

import java.io.File;

public class Base
{
	public  static final String  OS_NAME         = System.getProperty("os.name").toLowerCase();
	public  static final String  APPLICATION_DIR = getApplicationDir();
	public  static final String  BASE_DIR        = getBaseDir();
	
	public static boolean includeNatives = true;
	
	public static boolean debug = false;
	
	public  static final int ARRAYS = 0, ELEMENTS = 1;
	
	private static       int drawMode = ELEMENTS;
	
	private static   boolean usingVBO = false;
	
	/**
	 * Returns the directory to where you should store your applications
	 * data.
	 * @return
	 */
	private static final String getApplicationDir()
	{
		if (OS_NAME.contains("win"))
		{
			String dir = null;
			
			dir = System.getenv("APPDATA") + "/";
			
			dir = dir.replace("\\", "/");
			
			return dir;
		}
		else if (OS_NAME.contains("mac"))
		{
			return System.getProperty("user.home") + "/Library/Application Support/";
		}
		else if (OS_NAME.contains("lin"))
		{
			return System.getProperty("user.home") + "/";
		}
		
		return "error";
	}
	//1129 1236
	
	/**
	 * Used to set the BASE_DIR String variable to the base directory
	 * of the operating system. For instance, if you are running on
	 * Windows operating system, it would be "C://", however if you
	 * run it from Mac OSX, it would be "/".
	 * 
	 * @return The base directory of the operating system.
	 */
	private static final String getBaseDir()
	{
		if (OS_NAME.contains("win"))
		{
			String dir = "C://";
			
			return dir;
		}
		else if (OS_NAME.contains("mac"))
		{
			return "/";
		}
		
		return "error";
	}
	
	public static int getDrawMode()
	{
		return drawMode;
	}
	
	public static void setDrawMode(int drawMode)
	{
		Base.drawMode = drawMode;
	}
	
	public static boolean usingVBO()
	{
		return usingVBO;
	}
	
	public static void setUsingVBO(boolean usingVBO)
	{
		Base.usingVBO = usingVBO;
	}
}