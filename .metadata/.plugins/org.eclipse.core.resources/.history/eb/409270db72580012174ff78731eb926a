package net.foxycorndog.arrowide.language.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.command.Command;
import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.file.FileUtils;

public class JavaLanguage
{
	public  static final Color
			COMMENT_COLOR = new Color(Display.getCurrent(), 40, 140, 0),
			KEYWORD_COLOR = new Color(Display.getCurrent(), 150, 0, 0);
	
	public static void init()
	{
		
	}
	
	public static void run(final String fileLocation, final ConsoleStream stream)
	{
		final String classLocation = FileUtils.getParentFolder(FileUtils.getParentFolder(fileLocation)) + "/bin/" + FileUtils.getFileNameWithoutExtension(fileLocation) + ".class";
		
		try
		{
			URL url = new URL("file://" + FileUtils.getParentFolder(classLocation) + "/");
			
			final ClassLoader cLoader   = new URLClassLoader(new URL[] { url });
			
			final String      className = FileUtils.getFileNameWithoutExtension(classLocation);
			
			
//			Class        clazz     = cLoader.loadClass(className);//classLocation.substring(0, classLocation.lastIndexOf('.')));
			
//			final Method m         = clazz.getMethod("main", String[].class);
			
//			classRunning = true;
			
			Display.getDefault().asyncExec(new Runnable()
			{
				public void run()
				{
					new Thread()
					{
						public void run()
						{
							try
							{
								Class clazz   = Class.forName(className, true, cLoader);
															
								exec(clazz, classLocation, stream);
							}
							catch (ClassNotFoundException e)
							{
								e.printStackTrace();
							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}.start();
				}
			});
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private static void exec(Class clazz, String classLocation, ConsoleStream stream) throws IOException, InterruptedException
	{
		String javaHome  = System.getProperty("java.home");
		String javaBin   = javaHome + "/bin/java";
		String classpath = FileUtils.getParentFolder(classLocation);//System.getProperty("java.class.path");
		String className = clazz.getCanonicalName();
		
		Command command = new Command("\"" + javaBin + "\" -cp " + "\"" + classpath + "\" " + className, stream);
		
		command.execute();
	}
}