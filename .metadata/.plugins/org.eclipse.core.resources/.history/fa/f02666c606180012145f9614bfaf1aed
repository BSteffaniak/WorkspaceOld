package net.foxycorndog.jdobase;

import java.io.File;

public class Base
{
	private static final boolean JAR             = setJar();
	
	public  static final String  PATH;//            = System.getProperty("java.class.path");
	public  static final String  OS_NAME         = System.getProperty("os.name").toLowerCase();
	public  static final String  APPLICATION_DIR = getApplicationDir();
	public  static final String  BASE_DIR        = getBaseDir();
	
	public static boolean includeNatives = true;
	
	public static boolean debug = false;
	
	public  static final int ARRAYS = 0, ELEMENTS = 1;
	
	private static       int drawMode = ELEMENTS;
	
	private static   boolean usingVBO = false;
	
	/**
	 * Used to initialize the path variable. Sets the path variable
	 * to the working directory of the project.
	 */
	static
	{
		String pth = System.getProperty("java.class.path");
		pth = pth.replace('\\', '/');
		
		if (pth.indexOf(";") >= 0)
		{
			int curInd = pth.indexOf("jDoogl");
			
			int endIndex = pth.indexOf(';', curInd);
			
			while (pth.charAt(curInd) != ';')
			{
				curInd --;
			}
			
			pth = pth.substring(curInd + 1, endIndex);
			
			pth += "/";
		}
		else if (pth.indexOf(":") >= 0 && OS_NAME.contains("lin"))
		{
			pth = System.getProperty("java.class.path");
			
			pth = pth.substring(pth.indexOf(":") + 1, pth.indexOf(":", pth.indexOf(":") + 1));
			pth += "/";
		}
		else
		{
			File f = new File("");
			
			pth = f.getAbsolutePath() + "/";// + System.getProperty("java.class.path");
		}
		
		PATH = pth;
		
		if (includeNatives)
		{
			setNatives();
		}
	}
	
	/**
	 * Sets the location to the "natives" folder that contains all
	 * of the needed natives for the operating system.
	 */
	private static void setNatives()
	{
		System.out.println(PATH + "natives");
		
		System.setProperty("org.lwjgl.librarypath", PATH + "natives");
	}
	
	/**
	 * Sets the JAR final boolean variable to whether the jDoogl
	 * library was loaded from a jar or not.
	 * 
	 * @return Whether jDoogl was loaded from a jar or not.
	 */
	private static final boolean setJar()
	{
		return insideJar(Base.class);
	}
	
	/**
	 * Returns whether the specified Class is inside a jar. This method
	 * is especially useful for when you need to do anything with the
	 * working directory.
	 * 
	 * @param clazz The class from your project you are testing.
	 * @return Whether the Class is inside a jar or not.
	 */
	public static boolean insideJar(Class<?> clazz)
	{
		String isJar = clazz.getResource(clazz.getSimpleName() + ".class").toString();
		
		return isJar.startsWith("jar") || isJar.startsWith("rsrc");
	}
	
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