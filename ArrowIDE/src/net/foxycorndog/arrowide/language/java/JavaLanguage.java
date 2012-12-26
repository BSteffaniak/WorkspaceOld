package net.foxycorndog.arrowide.language.java;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.console.ConsoleStream;

public class JavaLanguage
{
	public static final Color COMMENT_COLOR = new Color(Display.getCurrent(), 40, 140, 0);
	
	public static void run(String classLocation, ConsoleStream stream)
	{
		final PrintStream out = System.out;
		
		try
		{
			System.setOut(stream);
			
			URL url = new URL("file://" + classLocation);
			
			ClassLoader cLoader = new URLClassLoader(new URL[] { url });
			
			Class clazz = Class.forName("Test", false, cLoader);//cLoader.loadClass(classLocation.substring(0, classLocation.lastIndexOf('.')));
			
			final Method m = clazz.getMethod("main", String[].class);
			
//			new Thread(new Runnable()
//			{
//				public void run()
//				{
					ArrowIDE.display.syncExec(new Runnable()
					{
						public void run()
						{
							try
							{
								m.invoke(null, new String[] { null });
							}
							catch (IllegalAccessException e)
							{
								e.printStackTrace();
							}
							catch (InvocationTargetException e)
							{
								e.printStackTrace();
							}
						}
					});
//				}
//			});
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			stream.println(e.getMessage());
		}
		
		System.setOut(out);
	}
}