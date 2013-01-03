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
	
	public static void run(final String classLocation, final ConsoleStream stream)
	{
		final PrintStream out = System.out;
		final PrintStream err = System.err;
		
//		System.setOut(stream);
//		System.setErr(stream);
		
		try
		{
			URL url = new URL("file://" + FileUtils.getParentFolder(classLocation) + "/");
			
			final ClassLoader cLoader   = new URLClassLoader(new URL[] { url });
			
			final String      className = classLocation.substring(classLocation.lastIndexOf('/') + 1, classLocation.lastIndexOf('.'));
			
			
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
															
								Process process = exec(clazz, classLocation, stream);
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
			
			
//			
//			while (classRunning)
//			{
//				if (!ArrowIDE.display.readAndDispatch())
//				{
//					ArrowIDE.display.sleep();
//				}
//			}
		}
//		catch (NoSuchMethodException e)
//		{
//			e.printStackTrace();
//		}
//		catch (ClassNotFoundException e)
//		{
//			e.printStackTrace();
//		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
//		System.setOut(out);
//		System.setErr(err);
	}
	
	private static Process exec(Class clazz, String classLocation, ConsoleStream stream) throws IOException, InterruptedException
	{
		String javaHome  = System.getProperty("java.home");
		String javaBin   = javaHome + "/bin/java";
		String classpath = FileUtils.getParentFolder(classLocation);//System.getProperty("java.class.path");
		String className = clazz.getCanonicalName();
		
		Process process = Command.execute("\"" + javaBin + "\" -cp " + "\"" + classpath + "\" " + className, stream);
		
		return process;
	}
}